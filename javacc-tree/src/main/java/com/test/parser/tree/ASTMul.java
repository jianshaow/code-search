package com.test.parser.tree;

public class ASTMul extends SimpleNode implements Operator {
	public ASTMul(int id) {
		super(id);
	}

	public ASTMul(Calculator p, int id) {
		super(p, id);
	}

	public int calculate(int left, int right) {
		return left * right;
	}
}
