package com.test.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

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
		final String query = args[1];

		if (!indexDir.exists() || !indexDir.isDirectory()) {
			throw new IOException(indexDir
					+ " does not exist or is not a directory");
		}

		search(indexDir, query);
	}

	private static void search(File indexDir, String q) throws IOException,
			ParseException {
		IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
		final IndexSearcher searcher = new IndexSearcher(reader);

		final Query query = new QueryParser(Version.LUCENE_42, "contents",
				new JavaAnalyzer()).parse(q);
		System.out.println(query);
		final long start = System.currentTimeMillis();
		final TopDocs topDocs = searcher.search(query, 100);
		long end = System.currentTimeMillis();

		System.out.println("Found " + topDocs.totalHits + " document(s) (in "
				+ (end - start) + " milliseconds) that matched query '" + q
				+ "':");

		final ScoreDoc[] docs = topDocs.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			final Document doc = searcher.doc(docs[i].doc);
			System.out.println("url=" + doc.get("url"));
			System.out.println("jarFile=" + doc.get("jarFile"));
			System.out.println("jarEntry=" + doc.get("jarEntry"));
		}
	}
}
