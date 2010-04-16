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
					| primitive_type_declarator
					;

//showGUI
showgui :
        'ShowGUI();' 
        {
        //instantiate ShowGui() object 
        }
        ;


//assignment expression
assignments:
					ID assigns+ ';'
					;

assigns: 
        '=' arithExpr
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
		primitive_type_declarator assignments
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
		 param ( ',' param)*		
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

timetester : time ;		
		
time :
      'Time' ID '[' (NUM | INTEGER) (',' (NUM | INTEGER))? ']' ';'
      ; 		
		
forloop :
    'for' ID '[' (NUM | INTEGER) ',' (NUM | INTEGER) ']' blockstmt
    ;
    
foreach :
    'foreach' derived_type '[' (NUM | INTEGER) ',' (NUM | INTEGER) ']' blockstmt
    ;
 		
derived_type : 'Station' | 'Line' | 'Time' | 'Population' ;		
 		
ifstmt :
        'if' '(' (arithExpr | NUM)')' 
        (blockstmt | statement) 
        ('else' (blockstmt | statement))?
 		;
		
blockstmt :
          '{' statement '}'
          ;
		

FUNCTIONS: 'getRate'|'getFrequeny'|'getCapacity'|'getSpeed'|'getNumLines'|'getNumStation'|'getNumPassengers'|'getNumInStation'|'getNumOnLine'|'getNumTrains'|'getNumFromTo'|'getNumWaiting'|'getNumMissed'|'checkBottleneck'|'getAvgWaitTime';
MOD_FUNCTIONS: 'changeRate'|'changeFrequency'|'changeSpeed'|'skipStation';
PRIMITIVE_TYPE: 'int'|'num'|'String';
//DERIVED_TYPE: 'Station' | 'Line' | 'Time' | 'Population';
ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' | 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT : '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;
NUM : INTEGER ('.'? INTEGER)? ;