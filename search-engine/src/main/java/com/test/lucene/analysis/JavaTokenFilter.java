package com.test.lucene.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class JavaTokenFilter extends TokenFilter {

	public JavaTokenFilter(TokenStream input) {
		super(input);
	}

	@Override
	public Token next(Token result) throws IOException {
		Token next = input.next();
		return next;
	}
}
