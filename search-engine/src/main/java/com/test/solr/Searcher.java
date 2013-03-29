package com.test.solr;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

import com.test.lucene.analysis.JavaAnalyzer;

public class Searcher {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("Usage: java " + Searcher.class.getName()
					+ " <index dir> <query>");
		}

		final File indexDir = new File(args[0]);
		final String q = args[1];

		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new IOException(indexDir
					+ " does not exist or is not a directory");
		}

		search(indexDir, q);
	}

	private static void search(File indexDir, String q) throws IOException,
			ParseException {
		final FSDirectory fsDir = FSDirectory.getDirectory(indexDir);
		final IndexSearcher searcher = new IndexSearcher(fsDir);

		final Query query = new QueryParser("contents", new JavaAnalyzer())
				.parse(q);
		System.out.println(query);
		final long start = System.currentTimeMillis();
		final Hits hits = searcher.search(query);
		long end = System.currentTimeMillis();

		System.out.println("Found " + hits.length() + " document(s) (in "
				+ (end - start) + " milliseconds) that matched query '" + q
				+ "':");

		for (int i = 0; i < hits.length(); i++) {
			final Document doc = hits.doc(i);
			System.out.println("url=" + doc.get("url"));
			System.out.println("jarFile=" + doc.get("jarFile"));
			System.out.println("jarEntry=" + doc.get("jarEntry"));
		}
	}
}
