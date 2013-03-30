package com.test.lucene.analysis;

import japa.parser.JavaCharStream;
import japa.parser.JavaParserConstants;
import japa.parser.JavaParserTokenManager;
import japa.parser.Token;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class JavaTokenizer extends Tokenizer {

	private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private JavaCharStream stream;
	private JavaParserTokenManager scanner;

	public JavaTokenizer(Reader input) {
		super(input);
		this.stream = new JavaCharStream(input);
		this.scanner = new JavaParserTokenManager(this.stream);
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
				final int end = this.stream.getEnd();
				offsetAtt.setOffset(correctOffset(end - termAtt.length()),
						correctOffset(end));
				typeAtt.setType(JavaParserConstants.tokenImage[token.kind]);
				return true;
			}
			posIncr++;
		}
	}

	@Override
	public final void end() {
		final int end = this.stream.getEnd();
		int finalOffset = correctOffset(end);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	@Override
	public void reset() throws IOException {
		this.stream.ReInit(input);
		this.scanner.ReInit(stream);
	}
}
