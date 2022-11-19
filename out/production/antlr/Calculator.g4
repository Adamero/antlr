grammar Calculator;


expression: plusExpression ((INTEGRAL) plusExpression)*;

plusExpression: multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*;

multiplyingExpression: powExpression ((TIMES | DIV)* powExpression)*;

powExpression: INT (POW INT)*;

INT: [0-9]+ ;
DOT: '.';
TIMES: '*' ;
DIV: '/' ;
PLUS: '+' ;
MINUS: '-' ;
POW: '^';
INTEGRAL: 'cal';
WS : [ \t\r\n]+ -> skip ;