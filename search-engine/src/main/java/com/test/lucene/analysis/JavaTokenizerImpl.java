package com.test.lucene.analysis;

import japa.parser.JavaCharStream;
import japa.parser.JavaParserConstants;
import japa.parser.JavaParserTokenManager;

import java.io.Reader;

import org.apache.lucene.analysis.Token;

public class JavaTokenizerImpl {

	private JavaParserTokenManager tokenManager;

	public JavaTokenizerImpl(Reader input) {
		this.tokenManager = new JavaParserTokenManager(
				new JavaCharStream(input));
	}

	public Token nextToken(Token result) {
		while (true) {
			try {
				japa.parser.Token nextToken = this.tokenManager.getNextToken();
				if (nextToken.kind == JavaParserConstants.IDENTIFIER) {
					result.clear();
					result.setTermBuffer(nextToken.image.toCharArray(), 0,
							nextToken.image.length());
					result
							.setType(JavaParserConstants.tokenImage[nextToken.kind]);
					break;
				}
				if ("".equals(nextToken.image)) {
					return null;
				}
			} catch (Error e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
}
