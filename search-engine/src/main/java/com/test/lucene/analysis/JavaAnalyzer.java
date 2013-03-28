package com.test.lucene.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

public class JavaAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new JavaTokenFilter(new JavaTokenizer(reader));
	}
}
