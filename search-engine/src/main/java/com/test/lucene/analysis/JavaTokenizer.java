package com.test.lucene.analysis;

import japa.parser.JavaCharStream;
import japa.parser.JavaParserConstants;
import japa.parser.JavaParserTokenManager;
import japa.parser.Token;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class JavaTokenizer extends Tokenizer {

	private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
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
		int posIncr = 1;

		while (true) {
			Token token = this.scanner.getNextToken();
			if (token.kind == JavaParserConstants.EOF) {
				return false;
			}
			if (token.kind == JavaParserConstants.IDENTIFIER) {
				posIncrAtt.setPositionIncrement(posIncr);
				termAtt.copyBuffer(token.image.toCharArray(), 0,
						token.image.length());
				typeAtt.setType(JavaParserConstants.tokenImage[token.kind]);
				return true;
			}
			posIncr++;
		}
	}
}
