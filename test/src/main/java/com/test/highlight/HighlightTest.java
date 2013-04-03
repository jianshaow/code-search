package com.test.highlight;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.test.lucene.analysis.JavaAnalyzer;

public class HighlightTest {

	/**
	 * @param args
	 * @throws Exception
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException, Exception {
//		searchHighlightByAnalyzer();
		searchHighlightByTermVector();
	}

	public static void searchHighlightByAnalyzer() {
		try {
			final FSDirectory directory = FSDirectory.open(new File(
					"D:/lucene-index/research"));
			IndexSearcher searcher = new IndexSearcher(
					DirectoryReader.open(directory));
			String strField = "contents";
			final Analyzer analyzer = new JavaAnalyzer();
			QueryParser parser = new QueryParser(Version.LUCENE_42, strField,
					analyzer);
			Query query = parser.parse("apache");
			TopDocs hits = searcher.search(query, 100);
			ScoreDoc[] scoreDoc = hits.scoreDocs;

			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
					"<read>", "</read>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
					new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(1000));

			Document doc = searcher.doc(scoreDoc[0].doc);

			final String content = InputStream2String(getInputStream(doc));

			System.out.println(highlighter.getBestFragment(analyzer,
					"contents", content));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void searchHighlightByTermVector() {
		try {
			final FSDirectory directory = FSDirectory.open(new File(
					"D:/lucene-index/research"));
			IndexSearcher searcher = new IndexSearcher(
					DirectoryReader.open(directory));
			String strField = "contents";
			final Analyzer analyzer = new JavaAnalyzer();
			QueryParser parser = new QueryParser(Version.LUCENE_42, strField,
					analyzer);
			Query query = parser.parse("rowMapper");
			TopDocs hits = searcher.search(query, 100);
			ScoreDoc[] scoreDoc = hits.scoreDocs;

			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
					"<read>", "</read>");
			Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
					new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(1000));

			Document doc = searcher.doc(scoreDoc[0].doc);
			final String content = InputStream2String(getInputStream(doc));

			final IndexReader indexReader = searcher.getIndexReader();

			final Terms termVector = indexReader.getTermVector(scoreDoc[0].doc,
					"contents");
			final TokenStream tokenStream = TokenSources
					.getTokenStream(termVector);

			System.out.println(highlighter
					.getBestFragment(tokenStream, content));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static InputStream getInputStream(Document doc) throws IOException,
			MalformedURLException, URISyntaxException {
		final String url = doc.get("url");
		final InputStream fileInputStream = new URL(url).openStream();
		final String jarEntry = doc.get("jarEntry");
		if (jarEntry != null) {
			final JarFile jarFile = new JarFile(new File(new URI(url)));
			final InputStream inputStream = jarFile.getInputStream(jarFile
					.getEntry(jarEntry));
			return inputStream;
		}
		return fileInputStream;
	}

	public static String InputStream2String(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1) {
			outStream.write(data, 0, count);
		}

		return new String(outStream.toByteArray(), "UTF-8");
	}
}
