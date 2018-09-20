grammar BRPExpressietaal;
import BRPAttributen;
exp :                   closure ;
closure :               boolean_exp ('WAARBIJ' assignment (COMMA assignment)* )? ;
assignment :            variable OP_EQUAL exp ;
boolean_exp : 		    boolean_term (op_or boolean_exp)? ;
boolean_term :          equality_expression (op_and boolean_term)? ;
equality_expression :   relational_expression (equality_op relational_expression)? ;
equality_op :           OP_EQUAL | OP_NOT_EQUAL | OP_LIKE ;
relational_expression : ordinal_expression (relational_op ordinal_expression)? ;
relational_op :         OP_LESS | OP_GREATER | OP_LESS_EQUAL | OP_GREATER_EQUAL | op_in ;
ordinal_expression :    negatable_expression (ordinal_op ordinal_expression)? ;
ordinal_op :            OP_PLUS | OP_MINUS ;
negatable_expression :  negation_operator? unary_expression ;
negation_operator :     OP_MINUS | op_not ;
unary_expression :
                       expression_list
    |                   function
    |                   exist_function
    |                   attribute
    |                   attribute_reference
    |                   literal
    |                   variable
    | bracketed_exp
    ;
bracketed_exp :         LP exp RP;
expression_list :       empty_list
    |                   non_empty_list ;
empty_list :            LL RL ;
non_empty_list :        LL exp (COMMA exp)* RL ;
attribute :             (object_identifier DOT)? attribute_path ;
attribute_reference :   OP_REF attribute ;
object_identifier :     IDENTIFIER ;
variable :              IDENTIFIER ;
function :              function_name LP ( exp (COMMA exp)* )? RP ;
function_name :         'IS_NULL'
    |                   'IS_OPGESCHORT'
    |                   'VIEW'
    |                   'DATUM'
    |                   'NU'
    |                   'DAG'
    |                   'MAAND'
    |                   'JAAR'
    |                   'AANTAL'
    |                   'PLATTE_LIJST'
    |                   'ALS'
    |                   'KINDEREN'
    |                   'OUDERS'
    |                   'PARTNERS'
    |                   'HUWELIJKEN'
    |                   'PARTNERSCHAPPEN'
    |                   'VOLGENDE'
    |                   'VORIGE'
    ;
exist_function :        exist_function_name LP exp COMMA variable COMMA exp RP ;
exist_function_name :   'ER_IS'
    |                   'ALLE'
    |                   'FILTER'
    |                   'MAP'
    |                   'RMAP'
    ;
literal :			    string_literal
	|				    boolean_literal
	|				    numeric_literal
	|				    date_literal
	|				    period_literal
	|				    null_literal
	;
string_literal : 	    STRING ;
boolean_literal : 	    true_constant | false_constant ;
true_constant :         'WAAR' | 'TRUE' ;
false_constant :        'ONWAAR' | 'FALSE' ;
numeric_literal : 	    (-)? INTEGER ;
date_literal : 		    '#' year ('/' month ('/' day)? )? '#' ;
year :			 	    numeric_literal
	|				    unknown_value ;
month : 			    numeric_literal
	|				    unknown_value
	|                   month_name ;
month_name:			    maand_jan
	|				    maand_feb
	|				    maand_mrt
	|				    maand_apr
	|				    maand_mei
	|				    maand_jun
	|				    maand_jul
	|				    maand_aug
	|				    maand_sep
	|				    maand_okt
	|				    maand_nov
	|				    maand_dec
	;
maand_jan :             ('JAN' | 'jan' | 'JANUARI' | 'januari') ;
maand_feb :             ('FEB' | 'feb' | 'FEBRUARI' | 'februari') ;
maand_mrt :             ('MRT' | 'mrt' | 'MAART' | 'maart');
maand_apr :             ('APR' | 'apr' | 'APRIL' | 'april');
maand_mei :             ('MEI' | 'mei') ;
maand_jun :             ('JUNI' | 'juni' | 'JUN' | 'jun');
maand_jul :             ('JULI' | 'juli' | 'JUL' | 'jul');
maand_aug :             ('AUGUSTUS' | 'augustus' | 'AUG' | 'aug');
maand_sep :             ('SEPTEMBER' | 'september' | 'SEP' | 'sep');
maand_okt :             ('OKTOBER' | 'oktober' | 'OKT' | 'okt');
maand_nov :             ('NOVEMBER' | 'november' | 'NOV' | 'nov');
maand_dec :             ('DECEMBER' | 'december' | 'DEC' | 'dec');
day :				    numeric_literal
	|				    unknown_value ;
period_literal :	    '^' relative_year ('/' relative_month ('/' relative_day)? )? '^' ;
relative_year :         period_part ;
relative_month :        period_part ;
relative_day :          period_part ;
period_part :		    ('-'|) numeric_literal ;
null_literal :		    'NULL' ;
unknown_value : 	    '?' ;

STRING :                '"' (.|' ')*? '"';
IDENTIFIER : 		    [a-z][a-z0-9_]* ;
INTEGER : 			    [0-9]+ ;
WS : 				    [ \t\r\n]+ -> skip ;
LP :                    '(' ;
RP :                    ')' ;
LL :                    '{' ;
RL :                    '}' ;
COMMA :                 ',' ;
DOT :                   '.' ;
OP_EQUAL :              '=' ;
OP_NOT_EQUAL :          '<>' ;
OP_LIKE :               '%=' ;
OP_LESS :               '<' ;
OP_GREATER :            '>' ;
OP_LESS_EQUAL :         '<=' ;
OP_GREATER_EQUAL :      '>=' ;
op_in :                 'IN' ;
OP_PLUS :               '+' ;
OP_MINUS :              '-' ;
op_or :                 'OF' ;
op_and :                'EN' ;
op_not :                'NIET' ;
OP_REF :                '$' ;
