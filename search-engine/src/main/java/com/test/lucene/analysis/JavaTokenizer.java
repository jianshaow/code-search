package com.test.lucene.analysis;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

public class JavaTokenizer extends Tokenizer {

	private JavaTokenizerImpl scanner;

	public JavaTokenizer(Reader input) {
		this.input = input;
		this.scanner = new JavaTokenizerImpl(input);
	}

	@Override
	public Token next(Token result) throws IOException {
		return scanner.nextToken(result);
	}
}
