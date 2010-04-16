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



program: (line|station|population|stat|simulate|statement|load)*																			
		;

//showGUI
showgui :
        'ShowGUI();' 
        {
        //instantiate ShowGui() object 
        }
        ;



load:
		'load' ID'.map' ';'
		;

simulate:
		'Simulate''('NUM?')' blockstmt
		;


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


        
//primitive-type-declarator
primitive_type_declarator:
		PRIMITIVE_TYPE ID
		;

		
//initilizer
primitive_type_initilizer:
		primitive_type_declarator ('='(assignsExpr|procedures))';'
		;
        
//Parameters
param: 
		ID
		|NUM
		|('+'|'-'|'*'|'/'|'^')?INTEGER
		|PRIMITIVE_TYPE ID
		|'Station' ID 
		|'Line' ID 
		|'Population' ID 
		|'Time' ID
		;
		
params:
		 param ( ',' param)*		
		;
        
//Procedures
procedures:
		FUNCTIONS '(' params ')'
		;
		
mod_procedures:
		MOD_FUNCTIONS '(' params ')'';'
		;
//user-defined stat
stat:
		'Stat' ID '('params')' '{'
		statements
		'return' ID ';'
		'}'
		;

//timie
time :
      'Time' ID '[' (NUM | INTEGER) (',' (NUM | INTEGER))? ']' ';'
      ; 		
				
derived_type : 'Station' | 'Line' | 'Time' | 'Population' ;		
 		
//print          
print_function:
			'print' (STRING |procedures) ';'
			;

//statement
statement:
					expression_statement
					|foreach
					|forloop
					|ifstmt
					|print_function
					|time
					|mod_procedures
					;

blockstmt :
          '{' primitive_type_declarator? statements '}'
          ;
          
statements :
		statement*
		;
		
expression_statement:
		assignsExpr? ';'
		;
		

foreach :
    'foreach' derived_type ID '[' (NUM | INTEGER) ',' (NUM | INTEGER) ']' blockstmt
    ;

forloop :
    'for' ID '[' (NUM | INTEGER) ',' (NUM | INTEGER) ']' blockstmt
    ;

ifstmt :
        'if' '(' (assignsExpr)')' (('else'? blockstmt) | statement) 
 		;

//assignment expression


assignsExpr: 
        (ID|primitive_type_declarator) '=' arithExpr
        ;

//this is a simple arithmetic grammar

arithExpr : 
					logicExpr
					;

logicExpr :	
					relExpr (('and' | 'or') relExpr)*
					;


relExpr :	
				addExpr (( '!=' | '==' | '<' | '>' | '<=' | '>=') addExpr)*
				;
		
											
addExpr : 
					multExpr (('+' | '-') multExpr)*
					;

multExpr :	
					powerExpr (('*' | '/' | '%') powerExpr)*
					;
						
powerExpr :
					unaryExpr ('^' unaryExpr)*
					;
																											

unaryExpr:
		unaryExpr_
		|procedures
		;

unaryExpr_ :	
					postfixExpr
					|('++'|'--') unaryExpr
					;

postfixExpr:
	    primaryExpr ('++'|'--')* 
        ;

primaryExpr:
		ID
		|NUM
		|INTEGER
		;


STRING : '"' .* '"';
FUNCTIONS: 'getRate'|'getFrequeny'|'getCapacity'|'getSpeed'|'getNumLines'|'getNumStation'|'getNumPassengers'|'getNumInStation'|'getNumOnLine'|'getNumTrains'|'getNumFromTo'|'getNumWaiting'|'getNumMissed'|'checkBottleneck'|'getAvgWaitTime';
MOD_FUNCTIONS: 'changeRate'|'changeFrequency'|'changeSpeed'|'skipStation';
PRIMITIVE_TYPE: 'int'|'num'|'String';
//DERIVED_TYPE: 'Station' | 'Line' | 'Time' | 'Population';
ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' |'_'| 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT : '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;
NUM : INTEGER ('.'? INTEGER)? ;