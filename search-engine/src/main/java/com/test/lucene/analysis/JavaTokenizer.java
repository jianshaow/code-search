package com.test.lucene.analysis;

import japa.parser.JavaCharStream;
import japa.parser.JavaParserConstants;
import japa.parser.JavaParserTokenManager;
import japa.parser.Token;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class JavaTokenizer extends Tokenizer {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private JavaParserTokenManager scanner;

	public JavaTokenizer(Reader input) {
		super(input);
		this.scanner = new JavaParserTokenManager(new JavaCharStream(input));
	}

	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();

		while (true) {
			Token nextToken = this.scanner.getNextToken();
			if ("".equals(nextToken.image)
					|| nextToken.kind == JavaParserConstants.EOF) {
				return false;
			}
			if (nextToken.kind == JavaParserConstants.IDENTIFIER) {
				termAtt.copyBuffer(nextToken.image.toCharArray(), 0,
						nextToken.image.length());
				typeAtt.setType(JavaParserConstants.tokenImage[nextToken.kind]);
				return true;
			}
		}
	}
}
