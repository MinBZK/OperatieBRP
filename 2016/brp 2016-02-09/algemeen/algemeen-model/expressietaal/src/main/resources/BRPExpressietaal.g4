grammar BRPExpressietaal;
import BRPAttributen,BRPGroepen;
@header {
package nl.bzk.brp.expressietaal.parser.antlr;
}

brp_expressie :         exp ;
exp :                   closure ;
closure :               booleanExp assignments? ;
assignments :           OP_WAARBIJ assignment (COMMA assignment)* ;
assignment :            variable OP_EQUAL exp ;
booleanExp :            booleanTerm (OP_OR booleanExp)? ;
booleanTerm :           equalityExpression (OP_AND booleanTerm)? ;
equalityExpression :    relationalExpression (equalityOp relationalExpression)? ;
equalityOp :            OP_EQUAL | OP_NOT_EQUAL | OP_LIKE ;
relationalExpression :  ordinalExpression (relationalOp ordinalExpression)? ;
relationalOp :          OP_LESS | OP_GREATER | OP_LESS_EQUAL | OP_GREATER_EQUAL | OP_IN | OP_IN_WILDCARD ;
ordinalExpression :     negatableExpression (ordinalOp ordinalExpression)? ;
ordinalOp :             OP_PLUS | OP_MINUS ;
negatableExpression :   negationOperator? unaryExpression ;
negationOperator :      OP_MINUS | OP_NOT ;
unaryExpression :       expressionList
    |                   function
    |                   existFunction
    |                   groepReference
    |                   attribute
    |                   attributeReference
    |                   literal
    |                   variable
    |                   bracketedExp
    ;
bracketedExp :          LP exp RP;
expressionList :        emptyList
    |                   nonEmptyList ;
emptyList :             LL RL ;
nonEmptyList :          LL exp (COMMA exp)* RL ;
attribute :             (objectIdentifier DOT)? attribute_path ;
attributeReference :    OP_REF attribute ;
groep :                 (objectIdentifier DOT)? groep_path ;
groepReference :        OP_REF groep ;
objectIdentifier :      IDENTIFIER ;
variable :              IDENTIFIER ;
function :              functionName LP ( exp (COMMA exp)* )? RP ;
functionName :          'IS_NULL'
    |                   'IS_OPGESCHORT'
    |                   'VIEW'
    |                   'DATUM'
    |                   'VANDAAG'
    |                   'DAG'
    |                   'MAAND'
    |                   'JAAR'
    |                   'AANTAL_DAGEN'
    |                   'LAATSTE_DAG'
    |                   'AANTAL'
    |                   'PLATTE_LIJST'
    |                   'ALS'
    |                   'KINDEREN'
    |                   'OUDERS'
    |                   'ERKENNERS'
    |                   'INSTEMMERS'
    |                   'NAAMSKEUZEPARTNERS'
    |                   'NAAMGEVERS'
    |                   'GEREGISTREERD_PARTNERS'
    |                   'HUWELIJKSPARTNERS'
    |                   'PARTNERS'
    |                   'HUWELIJKEN'
    |                   'PARTNERSCHAPPEN'
    |                   'FAMILIERECHTELIJKEBETREKKINGEN'
    |                   'GERELATEERDE_BETROKKENHEDEN'
    |                   'GEWIJZIGD'
    |                   'BETROKKENHEDEN'
    |                   'ONDERZOEKEN'
    ;
existFunction :         existFunctionName LP exp COMMA variable COMMA exp RP ;
existFunctionName :     'ER_IS'
    |                   'ALLE'
    |                   'FILTER'
    |                   'MAP'
    |                   'RMAP'
    ;
literal :               stringLiteral
    |                   booleanLiteral
    |                   numericLiteral
    |                   dateLiteral
    |                   dateTimeLiteral
    |                   periodLiteral
    |                   attributeCodeLiteral
    |                   nullLiteral
    ;
stringLiteral :         STRING ;
booleanLiteral :        TRUE_CONSTANT | FALSE_CONSTANT ;
numericLiteral :        INTEGER | '-' INTEGER ;
dateTimeLiteral :       year '/' month '/' day '/' hour '/' minute '/' second ;
dateLiteral :           year '/' month '/' day
    |                   year '/' month '/' UNKNOWN_VALUE
    |                   year '/' UNKNOWN_VALUE '/' UNKNOWN_VALUE
    |                   UNKNOWN_VALUE '/' UNKNOWN_VALUE '/' UNKNOWN_VALUE
    ;
year :                  numericLiteral ;
month :                 numericLiteral
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
day :                   numericLiteral ;
hour :                  numericLiteral ;
minute :                numericLiteral ;
second :                numericLiteral ;
periodLiteral :         '^' relativeYear ('/' relativeMonth ('/' relativeDay)? )? ;
relativeYear :          periodPart ;
relativeMonth :         periodPart ;
relativeDay :           periodPart ;
periodPart :            ('-'|) numericLiteral ;
attributeCodeLiteral:   '[' attribute_path ']' ;
nullLiteral :           NULL_CONSTANT ;

STRING :                '"' (.|' ')*? '"';
IDENTIFIER :            [a-z][a-zA-Z0-9_]* ;
INTEGER :               [0-9]+ ;
WS :                    [ \t\r\n]+ -> skip ;
LP :                    '(' ;
RP :                    ')' ;
LL :                    '{' ;
RL :                    '}' ;
COMMA :                 ',' ;
DOT :                   '.' ;
UNKNOWN_VALUE :         '?' ;
OP_EQUAL :              '=' ;
OP_NOT_EQUAL :          '<>' ;
OP_LIKE :               '%=' ;
OP_LESS :               '<' ;
OP_GREATER :            '>' ;
OP_LESS_EQUAL :         '<=' ;
OP_GREATER_EQUAL :      '>=' ;
OP_IN :                 'IN' ;
OP_IN_WILDCARD:         'IN%' ;
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

