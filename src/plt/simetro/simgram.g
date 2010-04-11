grammar simgram;

options {
  language = Java;
}

@header {
  package plt.simetro;
}

@lexer::header {
  package plt.simetro;
}

//statement
statement:
					assignments
					;

//assignment expression
assignments:
					ID'='arithExpr';'
					;


//this is a simple arithmetic grammar

arithExpr : 
					logicExpr
					;

logicExpr :	
					relExpr (('and' | 'or') relExpr)*
					;
//DO WE WANT LOGICAL EXPRESSIONS; DO WE WANT TO USE AND, OR?	

relExpr :	
				addExpr (('=' | '!=' | '==' | '<' | '>' | '<=' | '>=') addExpr)*
				;
//DO WE WANT != FOR NOT EQUALS?				
											
addExpr : 
					multExpr (('+' | '-') multExpr)*
					;

multExpr :	
					powerExpr (('*' | '/' | '%') powerExpr)*
					;
						
powerExpr :
					unaryExpr ('^' unaryExpr)*
					;
											
unaryExpr :	
					('+' | '-')* (term|procedures)
					;
														
											
term : 	ID
				| '('logicExpr')'
				| INTEGER
				;
																			
program : (line | station | population)* ;

//just a test
strings : string+;

//lines
line: 
    'Line' ID  '{'
    'Stations' '('idlist ')' ';' 
    'Frequency' '(' (ID | INTEGER) ')' ';'
    'Capacity' '('(ID | INTEGER) ')' ';'
    'Speed' '(' (ID | INTEGER) ')' ';' 
    '}' 
    ;

//Station
station:
	'Station' ID '{'
	'Population' '(' ID ')' ';'
	'}'
	;


//Population
	
population:
	'Population' ID '{'
	('('ID ','(ID|INTEGER)')'';')+
	'}'
	;

idlist : ID (',' ID)* ;

//defining strings
string :
        'String' ID '=' STRING ';'
        ;

STRING : '"'
        {StringBuilder b = new StringBuilder();}
        ( '\\' '"'  {b.appendCodePoint('"'); }
        |  c = ~('"' | '\r' | '\n')   {b.appendCodePoint(c);  } 
        )*
        '"'  
        {setText(b.toString()); }
        ;
        
//primitive-type-declarator
primitive_type_declarator:
		PRIMITIVE_TYPE ID ';'
		;

		
//initilizer
primitive_type_initilizer:
		primitive_type_declarator '=' arithExpr
		|assignments
		;
        
//Parameters
param: 
		PRIMITIVE_TYPE ID
		|'Station' ID 
		|'Line' ID 
		|'Population' ID 
		|'Time' ID
		;
		
params:
		(param',')*param		
		;
        
//Procedures
procedures:
		FUNCTIONS '(' params ')'';'	
		;
//user-defined stat
stat:
		'Stat' ID '('params')' '{'
		(statement|procedures)*
		'return' ID ';'
		'}'
		;
		
		
FUNCTIONS: 'getRate'|'getFreequeny'|'getCapacity'|'getSpeed'|'getNumLines'|'getNumStation'|'getNumPassengers'|'getNumInStation'|'getNumOnLine'|'getNumTrains'|'getNumFromTo'|'getNumWaiting'|'getNumMissed'|'checkBottleneck'|'getAvgWaitTime';
MOD_FUNCTIONS: 'changeRate'|'changeFrequency'|'changeSpeed'|'skipStation';
PRIMITIVE_TYPE: 'int'|'num'|'String';
ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' | 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT : '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;