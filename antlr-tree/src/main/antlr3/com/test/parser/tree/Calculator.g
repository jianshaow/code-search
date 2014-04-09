grammar Calculator;

options {
    language=Java;
    ASTLabelType=CommonTree;
    output=AST;
}

@parser::header {
package com.test.parser.tree;
}

@lexer::header {
package com.test.parser.tree;
}

calc   : expr
       ;
expr   : term ( ( PLS^ | MIN^ ) term )*
       ;
term   : factor ( ( MUL^ | DIV^ ) factor )*
       ;
factor : INT
       | LBR expr RBR
       ;

PLS    : '+' ;
MIN    : '-' ;
MUL    : '*' ;
DIV    : '/' ;
LBR    : '(' ;
RBR    : ')' ;
INT    : ('0'..'9')+ ;

WS     : (' ' | '\t'| '\r' ? '\n')+ { $channel=HIDDEN; }
       ;
