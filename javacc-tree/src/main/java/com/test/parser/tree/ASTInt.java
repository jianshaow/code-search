package com.test.parser.tree;

public class ASTInt extends SimpleNode implements Expression {
	private int value;

	public ASTInt(int id) {
		super(id);
	}

	public ASTInt(Calculator p, int id) {
		super(p, id);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
