grammar Calculator;

expression: multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*;
multiplyingExpression: powExpression ((TIMES | DIV)* powExpression)*;
powExpression: integralExpression ((POW | SQRT)* integralExpression)*;
integralExpression: INT (INTEGRAL INT)*;


INT: [0-9]+ ;
DOT: '.';
TIMES: '*' ;
DIV: '/' ;
PLUS: '+' ;
MINUS: '-' ;
POW: '^';
SQRT: 'sqrt';
INTEGRAL: 'cal';
WS : [ \t\r\n]+ -> skip ;