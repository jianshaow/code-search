package com.test.indexing;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.test.lucene.Indexer;
import com.test.lucene.analysis.JavaAnalyzer;

public class IndexingTest {

	public static void main(String[] args) throws IOException {
		final File indexDir = new File("D:/lucene-index/research");
		final File javaFile = new File(
				"D:/Sources/repositories/messaging/messaging/messaging-business-common-session-lib/src/main/java/se/ericsson/nrg/ws/service/msgcommonsession/MsgCommonSessionDaoImpl.java");

		final Directory dir = FSDirectory.open(indexDir);
		final Analyzer analyzer = new JavaAnalyzer();
		final IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_42, analyzer);
		config.setOpenMode(OpenMode.CREATE);
		final IndexWriter writer = new IndexWriter(dir, config);
		Indexer.indexJavaFile(writer, javaFile);
		writer.close();
	}
}
