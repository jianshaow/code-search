package com.test.indexing;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Testing {

	@Test
	public void test() throws IOException {
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
				"D:/lucene-index/java")));
		final IndexSearcher searcher = new IndexSearcher(reader);
	}
}
