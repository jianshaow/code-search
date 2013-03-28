package com.test.parser.tree;

public class ASTMonomial extends SimpleNode implements Expression {
	public ASTMonomial(int id) {
		super(id);
	}

	public ASTMonomial(Calculator p, int id) {
		super(p, id);
	}

	public int getValue() {
		int value = 0;
		Operator operator = null;
		for (int i = 0; i < this.jjtGetNumChildren(); i++) {
			if (this.jjtGetChild(i) instanceof Expression) {
				final Expression expressionChild = (Expression) this
						.jjtGetChild(i);
				if (i == 0) {
					value = expressionChild.getValue();
				} else {
					if (operator != null) {
						value = operator.calculate(value, expressionChild
								.getValue());
					} else {
						throw new RuntimeException("error");
					}
				}
			}
			if (this.jjtGetChild(i) instanceof Operator) {
				operator = (Operator) this.jjtGetChild(i);
			}
		}
		return value;
	}
}
