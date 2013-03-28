grammar Calculator;

options {
	language = Java;
}

@parser::header {
package com.test.parser;
}

@lexer::header {
package com.test.parser;
}

parse returns [int value = 0]
    : a=expr { value = a; }
    ;

expr returns [int value = 0]
    : a=term { value = a; } ( ( ADD b=term { value += b; } ) | ( SUB b=term { value -= b; } ) )*
    ;

term returns [int value=0]
    : a=factor { value = a; } ( ( MUL b=factor { value *= b; } ) | ( DIV b=factor { value /= b; } ) )*
    ;

factor returns [int value=0]
    : INT { value = $INT.int; } | LBR a=expr { value = a; } RBR
    ;

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;
LBR : '(' ;
RBR : ')' ;
INT : ('0'..'9')+ ;

WS  : (' ' | '\t'| '\r' ? '\n')+ { $channel=HIDDEN; }
    ;
