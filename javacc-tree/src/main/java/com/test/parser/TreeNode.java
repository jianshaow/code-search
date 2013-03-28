package com.test.parser;

public interface TreeNode {

	TreeNode getLeft();

	TreeNode getParent();

	TreeNode getRight();

	int getValue();

	void setLeft(TreeNode left);

	void setParent(TreeNode parent);

	void setRight(TreeNode right);
}
