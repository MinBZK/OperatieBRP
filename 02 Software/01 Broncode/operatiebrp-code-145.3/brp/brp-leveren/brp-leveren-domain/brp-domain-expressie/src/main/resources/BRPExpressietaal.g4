grammar BRPExpressietaal;
@header {
package nl.bzk.brp.domain.expressie.parser.antlr;
}

brp_expressie :         exp ;
exp :                   closure ;
closure :               booleanExp assignments? ;
assignments :           OP_WAARBIJ assignment (COMMA assignment)* ;
assignment :            variable OP_EQUAL exp ;
booleanExp :            booleanTerm (OP_OR booleanExp)? ;
booleanTerm :           equalityExpression (OP_AND booleanTerm)? ;
equalityExpression :    relationalExpression (equalityOp relationalExpression)? ;
equalityOp :            OP_EQUAL
    |                   OP_NOT_EQUAL
    |                   OP_LIKE
    ;
relationalExpression :  ordinalExpression (relationalOp ordinalExpression)? ;
relationalOp :          OP_LESS
    |                   OP_GREATER
    |                   OP_LESS_EQUAL
    |                   OP_GREATER_EQUAL
    |                   OP_AIN
    |                   OP_AIN_WILDCARD
    |                   OP_EIN
    |                   OP_EIN_WILDCARD
    |                   collectionEOp
    |                   collectionAOp
    ;
collectionEOp :         EOP_EQUAL
    |                   EOP_NOT_EQUAL
    |                   EOP_LESS
    |                   EOP_GREATER
    |                   EOP_LESS_EQUAL
    |                   EOP_GREATER_EQUAL
    |                   EOP_LIKE
    ;
collectionAOp :         AOP_EQUAL
    |                   AOP_NOT_EQUAL
    |                   AOP_LESS
    |                   AOP_GREATER
    |                   AOP_LESS_EQUAL
    |                   AOP_GREATER_EQUAL
    |                   AOP_LIKE
    ;
ordinalExpression :     negatableExpression (ordinalOp ordinalExpression)? ;
ordinalOp :             OP_PLUS | OP_MINUS ;
negatableExpression :   negationOperator? unaryExpression ;
negationOperator :      OP_MINUS | OP_NOT ;
unaryExpression :       expressionList
    |                   function
    |                   existFunction
    |                   element
    |                   literal
    |                   variable
    |                   bracketedExp
    ;
bracketedExp :          LP exp RP;
expressionList :        emptyList
    |                   nonEmptyList ;
emptyList :             LL RL ;
nonEmptyList :          LL exp (COMMA exp)* RL ;
element :               element_path ;
variable :              IDENTIFIER ;
function :              functionName LP ( exp (COMMA exp)* )? RP ;
functionName :          'IS_NULL'
    |                   'DATUM'
    |                   'VANDAAG'
    |                   'DAG'
    |                   'MAAND'
    |                   'JAAR'
    |                   'AANTAL_DAGEN'
    |                   'LAATSTE_DAG'
    |                   'AANTAL'
    |                   'ALS'
    |                   'HISM'
    |                   'HISM_LAATSTE'
    |                   'HISF'
    |                   'GEWIJZIGD'
    |                   'KV'
    |                   'KNV'
    |                   'ACTIE'
    |                   'AH'
    |                   'SELECTIE_DATUM'
    |                   'SELECTIE_LIJST'
    ;
existFunction :         existFunctionName LP exp COMMA variable COMMA exp RP ;
existFunctionName :     'ER_IS'
    |                   'ALLE'
    |                   'FILTER'
    |                   'MAP'
    ;
literal :               stringLiteral
    |                   booleanLiteral
    |                   numericLiteral
    |                   dateLiteral
    |                   dateTimeLiteral
    |                   periodLiteral
    |                   element
    |                   elementCodeLiteral
    |                   nullLiteral
    ;
stringLiteral :         STRING ;
booleanLiteral :        TRUE_CONSTANT | FALSE_CONSTANT ;
numericLiteral :        INTEGER | '-' INTEGER ;
dateTimeLiteral :       year '/' month '/' day '/' hour '/' minute '/' second ;
dateLiteral :           year '/' month '/' day
    ;
year :                  numericLiteral | UNKNOWN_VALUE;
month :                 numericLiteral | UNKNOWN_VALUE
    |                   monthName ;
monthName:              MAAND_JAN
    |                   MAAND_FEB
    |                   MAAND_MRT
    |                   MAAND_APR
    |                   MAAND_MEI
    |                   MAAND_JUN
    |                   MAAND_JUL
    |                   MAAND_AUG
    |                   MAAND_SEP
    |                   MAAND_OKT
    |                   MAAND_NOV
    |                   MAAND_DEC
    ;
day :                   numericLiteral | UNKNOWN_VALUE;
hour :                  numericLiteral ;
minute :                numericLiteral ;
second :                numericLiteral ;
periodLiteral :         '^' relativeYear '/' relativeMonth '/' relativeDay ;
relativeYear :          periodPart | UNKNOWN_VALUE;
relativeMonth :         periodPart | UNKNOWN_VALUE;
relativeDay :           periodPart | UNKNOWN_VALUE;
periodPart :            ('-'|) numericLiteral ;
elementCodeLiteral:   '[' element_path ']' ;
nullLiteral :           NULL_CONSTANT ;
element_path :        IDENTIFIER | IDENTIFIER (DOT IDENTIFIER)+ ;
STRING :                '"' (.|' ')*? '"';
INTEGER :               [0-9]+ ;
WS :                    [ \t\r\n]+ -> skip ;
LP :                    '(' ;
RP :                    ')' ;
LL :                    '{' ;
RL :                    '}' ;
COMMA :                 ',' ;
DOT :                   '.' ;
UNKNOWN_VALUE :         '?' ;
EOP_EQUAL :             'E=' ;
AOP_EQUAL :             'A=' ;
OP_EQUAL :              '=' ;
EOP_NOT_EQUAL :         'E<>' ;
AOP_NOT_EQUAL :         'A<>' ;
OP_NOT_EQUAL :          '<>' ;
EOP_LIKE :              'E=%' ;
AOP_LIKE :              'A=%' ;
OP_LIKE :               '=%' ;
EOP_LESS :              'E<' ;
AOP_LESS :              'A<' ;
OP_LESS :               '<' ;
EOP_GREATER :           'E>' ;
AOP_GREATER :           'A>' ;
OP_GREATER :            '>' ;
EOP_LESS_EQUAL :        'E<=' ;
AOP_LESS_EQUAL :        'A<=' ;
OP_LESS_EQUAL :         '<=' ;
EOP_GREATER_EQUAL :     'E>=' ;
AOP_GREATER_EQUAL :     'A>=' ;
OP_GREATER_EQUAL :      '>=' ;
OP_AIN_WILDCARD :       'AIN%' ;
OP_AIN :                'AIN' ;
OP_EIN_WILDCARD :       'EIN%' ;
OP_EIN :                'EIN' ;
OP_PLUS :               '+' ;
OP_MINUS :              '-' ;
OP_OR :                 'OF' ;
OP_AND :                'EN' ;
OP_NOT :                'NIET' ;
OP_REF :                '$' ;
OP_WAARBIJ :            'WAARBIJ' ;
TRUE_CONSTANT :         'WAAR' | 'TRUE' ;
FALSE_CONSTANT :        'ONWAAR' | 'FALSE' ;
NULL_CONSTANT :         'NULL' ;
MAAND_JAN :             ('JAN' | 'JANUARI') ;
MAAND_FEB :             ('FEB' | 'FEBRUARI') ;
MAAND_MRT :             ('MRT' | 'MAART');
MAAND_APR :             ('APR' | 'APRIL');
MAAND_MEI :             ('MEI' ) ;
MAAND_JUN :             ('JUNI' | 'JUN');
MAAND_JUL :             ('JULI' | 'JUL');
MAAND_AUG :             ('AUGUSTUS' | 'AUG');
MAAND_SEP :             ('SEPTEMBER' | 'SEP');
MAAND_OKT :             ('OKTOBER' | 'OKT');
MAAND_NOV :             ('NOVEMBER' | 'NOV');
MAAND_DEC :             ('DECEMBER' | 'DEC');
IDENTIFIER :            [a-zA-Z][a-zA-Z0-9_]* ;

