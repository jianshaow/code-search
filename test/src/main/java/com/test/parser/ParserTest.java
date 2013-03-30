package com.test.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;

public class ParserTest {

	public static void main(String[] args) throws ParseException {
		final CompilationUnit compilationUnit = JavaParser.parse(new File(
				"src/main/java/com/test/Sample.java"));

		System.out.println("package........");
		final PackageDeclaration pakage = compilationUnit.getPakage();
		System.out.println(pakage.getName());
		System.out.println("start: " + pakage.getBeginLine() + ":"
				+ pakage.getBeginColumn());
		System.out.println("end: " + pakage.getEndLine() + ":"
				+ pakage.getEndColumn());
		System.out.println();

		System.out.println("import........");
		for (final ImportDeclaration importDeclaration : compilationUnit
				.getImports()) {
			System.out.println(importDeclaration.getName());
			System.out.println("start: " + importDeclaration.getBeginLine()
					+ ":" + importDeclaration.getBeginColumn());
			System.out.println("end: " + importDeclaration.getEndLine() + ":"
					+ importDeclaration.getEndColumn());
		}
		System.out.println();
		System.out.println("type........");
		for (final TypeDeclaration typeDeclaration : compilationUnit.getTypes()) {
			System.out.println(typeDeclaration.getModifiers());
			System.out.println(typeDeclaration.getName());
			System.out.println("start: " + typeDeclaration.getBeginLine() + ":"
					+ typeDeclaration.getBeginColumn());
			System.out.println("end: " + typeDeclaration.getEndLine() + ":"
					+ typeDeclaration.getEndColumn());
			for (final BodyDeclaration bodyDeclaration : typeDeclaration
					.getMembers()) {
				System.out.println(bodyDeclaration);
			}
		}
		System.out.println();
		System.out.println("comment........");
		for (final Comment comment : compilationUnit.getComments()) {
			System.out.println("start: " + comment.getBeginLine() + ":"
					+ comment.getBeginColumn());
			System.out.println("end: " + comment.getEndLine() + ":"
					+ comment.getEndColumn());
			System.out.println(comment.getContent());
		}
	}
}
