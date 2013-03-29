package com.test.lucene.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;

public class JavaAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		return new TokenStreamComponents(new JavaTokenizer(reader));
	}
}
