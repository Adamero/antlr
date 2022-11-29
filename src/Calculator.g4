grammar Calculator;


expression: multiplyingExpression ((PLUS | MINUS) multiplyingExpression)*;
multiplyingExpression: powerExpression ((TIMES | DIV) powerExpression)*;
powerExpression: integralExpression  ((POW|SQRT|LOG|MAX|MIN) integralExpression)* ;
integralExpression: MINUS (INT | DOUBLE) | (INT | DOUBLE) | LPAREN expression RPAREN;



INT: [0-9]+ ;
DOUBLE: [0-9]+'.'+[0-9]+;
POW: '^';
SQRT: 'sqrt';
LOG: 'log';
TIMES: '*' ;
DIV: '/' ;
PLUS: '+' ;
MINUS: '-' ;
MIN: 'MIN';
MAX: 'MAX';
INTEGRAL: 'cal';
LPAREN: '(';
RPAREN: ')';
WS : [ \t\r\n]+ -> skip ;