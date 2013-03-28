tree grammar CalculatorTreeWalker;

options {
    language=Java;
    ASTLabelType=CommonTree;
    tokenVocab=Calculator;
}

@header {
package com.test.parser.tree;
}

calc: expr;

expr returns [int value]
  : ^(PLS a=expr b=expr) { value = a + b; }
  | ^(MINUS a=expr b=expr) { value = a - b; }
  | ^(MUL a=expr b=expr) { value = a * b; }
  | ^(DIV a=expr b=expr) { value = a / b; }
  | INT { value = $INT.int; }
  | LBR a=expr RBR { value = a; }
  ;