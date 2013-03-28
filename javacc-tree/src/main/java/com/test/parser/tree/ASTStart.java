package com.test.parser.tree;

public class ASTStart extends SimpleNode implements Expression {
	public ASTStart(int id) {
		super(id);
	}

	public ASTStart(Calculator p, int id) {
		super(p, id);
	}

	public int getValue() {
		return ((Expression) this.jjtGetChild(0)).getValue();
	}
}
