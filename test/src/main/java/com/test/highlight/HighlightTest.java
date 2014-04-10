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
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.test.lucene.analysis.JavaAnalyzer;

public class HighlightTest {

	private static String getFragment(Highlighter highlighter,
			final IndexSearcher searcher, final ScoreDoc scoreDoc)
			throws IOException, Exception, MalformedURLException,
			URISyntaxException, InvalidTokenOffsetsException {
		final Document doc = searcher.doc(scoreDoc.doc);
		final String content = inputStream2String(getInputStream(doc));
		final IndexReader indexReader = searcher.getIndexReader();
		final Terms termVector = indexReader.getTermVector(scoreDoc.doc,
				"contents");
		final TokenStream tokenStream = TokenSources.getTokenStream(termVector);
		final String fragment = highlighter.getBestFragment(tokenStream,
				content);
		return fragment;
	}

	private static String getFragment(final Highlighter highlighter,
			final IndexSearcher searcher, final ScoreDoc scoreDoc,
			final Analyzer analyzer) throws IOException, Exception,
			MalformedURLException, URISyntaxException,
			InvalidTokenOffsetsException {
		final Document doc = searcher.doc(scoreDoc.doc);
		final String content = inputStream2String(getInputStream(doc));
		final String fragment = highlighter.getBestFragment(analyzer,
				"contents", content);
		return fragment;
	}

	private static Highlighter getHighlighter(Query query) {
		final SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<read>", "</read>");
		final Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
				new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(1000));
		return highlighter;
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

	private static String inputStream2String(InputStream in) throws Exception {
		final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		final byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1) {
			outStream.write(data, 0, count);
		}

		return new String(outStream.toByteArray(), "UTF-8");
	}

	/**
	 * @param args
	 * @throws Exception
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException, Exception {
		searchHighlightByAnalyzer();
		System.out.println("=================================================");
		searchHighlightByTermVector();
	}

	public static void searchHighlightByAnalyzer() {
		try {
			final FSDirectory directory = FSDirectory.open(new File(
					"D:/lucene-index/research"));
			final IndexSearcher searcher = new IndexSearcher(
					DirectoryReader.open(directory));
			final Analyzer analyzer = new JavaAnalyzer();
			final QueryParser parser = new QueryParser(Version.LUCENE_47,
					"contents", analyzer);
			final Query query = parser.parse("rowMapper");
			final TopDocs hits = searcher.search(query, 100);
			final ScoreDoc[] scoreDocs = hits.scoreDocs;

			final ScoreDoc scoreDoc = scoreDocs[0];

			final Highlighter highlighter = getHighlighter(query);

			final String fragment = getFragment(highlighter, searcher,
					scoreDoc, analyzer);
			System.out.println(fragment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void searchHighlightByTermVector() {
		try {
			final FSDirectory directory = FSDirectory.open(new File(
					"D:/lucene-index/research"));
			final IndexSearcher searcher = new IndexSearcher(
					DirectoryReader.open(directory));
			final Analyzer analyzer = new JavaAnalyzer();
			final QueryParser parser = new QueryParser(Version.LUCENE_47,
					"contents", analyzer);
			final Query query = parser.parse("rowMapper");
			final TopDocs hits = searcher.search(query, 100);
			final ScoreDoc[] scoreDocs = hits.scoreDocs;
			final ScoreDoc scoreDoc = scoreDocs[0];

			final Highlighter highlighter = getHighlighter(query);

			final String fragment = getFragment(highlighter, searcher, scoreDoc);
			System.out.println(fragment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
