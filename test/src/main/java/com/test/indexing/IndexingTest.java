package com.test.indexing;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.test.lucene.Indexer;
import com.test.lucene.analysis.JavaAnalyzer;

public class IndexingTest {

	public static void main(String[] args) throws IOException {
		final File indexDir = new File("D:/lucene-index/research");
		final File javaFile = new File("src/main/java/com/test/Sample.java");

		final Directory dir = FSDirectory.open(indexDir);
		 final Analyzer analyzer = new JavaAnalyzer();
//		final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		final IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_42, analyzer);
		final IndexWriter writer = new IndexWriter(dir, config);
		Indexer.indexJavaFile(writer, javaFile);
		writer.close();
	}
}
