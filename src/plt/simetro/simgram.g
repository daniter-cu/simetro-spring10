
grammar simgram;

options {
  language = Java;
  output = AST;
}

tokens {
ShowGUI;
BLOCKSTMT;
PROGRAM;
LINE;
STATIONS;
FREQUENCY;
CAPACITY;
POPITEM;
SPEED;
IF;
ELSE;
STATION;
COORDINATES;
POPULATION;
IDLIST;
FORMALPARAM;
POPULATION;
TIME;
STAT;
RETURN;
}

@header {
  package plt.simetro;
}

@lexer::header {
  package plt.simetro;
}




program: (line|station|population|stat|simulate|statement|load|showgui)*	 EOF!																		
		;

//showGUI
showgui :
        'ShowGUI();' -> ShowGUI
        ;



load:
		'load'^ ID '.map'! ';'!
		;

simulate:
		'Simulate'^ '('! INTEGER ')'! blockstmt
		;

//lines
line: 
    'Line' ID  '{'
    'Stations' '(' idlist ')' ';' 
    'Frequency' '(' (f=ID | f=INTEGER) ')' ';'
    'Capacity' '(' (c=ID | c=INTEGER) ')' ';'
    'Speed' '(' (s=ID | s=INTEGER) ')' ';' 
    '}' 
    -> ^(LINE ID ^(STATIONS idlist) ^(FREQUENCY $f) ^(CAPACITY $c) ^(SPEED $s) )
    ;

//Station
station:
	'Station' ID '{'
	'Coordinates' '(' i1=INTEGER ',' i2=INTEGER ')' ';'
	'Population' '(' ID ')' ';'
	'}'
	-> ^(STATION ID ^(COORDINATES $i1 $i2) ^(POPULATION ID))
	;


//Population
	
population:
	'Population' i=ID '{'
	('('j+=ID ','k+=INTEGER')')+
	'}' -> ^(POPULATION $i ^(POPITEM $j $k)*)
	;

idlist : i=ID (',' il+=ID)* -> ^(IDLIST $i $il*) ;

//defining strings
string :
        'String'^ ID '=' STRING ';'! 
        | 'String'^ ID ';'!
        ;


       
//primitive-type-declarator
primitive_type_declarator:
		PRIMITIVE_TYPE^ ID 
		;

  
formal_param: 
		PRIMITIVE_TYPE^ ID  
		|'Station'^ ID 
		|'Line'^ ID  
		|'Population'^ ID  
		|'Time'^ ID  
		|'String'^ ID
		; 
		
formal_params:
		 a+=formal_param ( ',' a+=formal_param)*	-> ^(FORMALPARAM $a*)	
		;
  
params:
    (ID | NUM |('+'|'-'|'*'|'/'|'^')?INTEGER) ( ',' (ID | NUM |('+'|'-'|'*'|'/'|'^')?INTEGER))*     
  ;
//Procedures
procedures:
		FUNCTIONS^ '('! params ')'! ';'!
		;
		

		
mod_procedures:
		MOD_FUNCTIONS^ '('! params ')'! ';'!
		;
		
//user-defined stat
stat:
		'Stat' ID '(' f=formal_params ')' '{'
		s=statements
		'return' r=ID ';'
		'}' -> ^(STAT ID $f $s ^(RETURN $r))
		;

//timie
time :
      'Time' ID '[' i+=INTEGER (',' i+=INTEGER)? ']' ';' -> ^(TIME ID $i+)
      ; 		
		
derived_type : 'Station' | 'Line' | 'Time' | 'Population' ;		
 		
//print          
print_function:
			'print'^ (STRING |procedures | ID) ';'                
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
          '{' s+=statements '}' -> ^(BLOCKSTMT statements) 
          ;
       
statements :
		statement* -> statement+
		;
	
expression_statement:
		assignsExpr
		| primitive_type_declarator ';'
		;
		

foreach :
    'foreach'^ derived_type ID blockstmt 
    ;

forloop :
    'for'^ ID '['! INTEGER ','! INTEGER ']'! blockstmt
    ;

ifstmt :
        'if' '(' a=arithExpr')' b=blockstmt ('else' c=blockstmt)? -> ^(IF $a $b ^(ELSE $c)?)
 		;

//assignment expression


assignsExpr: 
        (ID^ | primitive_type_declarator^) '=' arithExpr ';'! 
        ;

//this is a simple arithmetic grammar

arithExpr : 
					logicExpr
					;

logicExpr :	
					relExpr (('and'^ | 'or'^) relExpr)*
					;


relExpr :	
				addExpr (( '!='^ | '=='^ | '<'^ | '>'^ | '<='^ | '>='^) addExpr)*
				;
		
											
addExpr : 
					multExpr (('+'^ | '-'^) multExpr)*
					;

multExpr :	
					powerExpr (('*'^ | '/'^ | '%'^) powerExpr)*
					;
						
powerExpr :
					unaryExpr ('^'^ unaryExpr)*
					;
																											

unaryExpr:
		('+'^ | '-'^)? primaryExpr                    
		;


primaryExpr:
		ID
		|NUM
		|INTEGER
		;

STRING : '"'
        {StringBuilder b = new StringBuilder();}
        ( '\\' '"'  {b.appendCodePoint('"'); }
        |  c = ~('"' | '\r' | '\n')   {b.appendCodePoint(c);  } 
        )*
        '"'  
        {setText(b.toString()); }
        ;
        
//STRING : '"' .* '"'; //fix strings!
FUNCTIONS: 'getRate'|'getFrequeny'|'getCapacity'|'getSpeed'|'getNumLines'|'getNumStation'|'getNumPassengers'|'getNumInStation'|'getNumOnLine'|'getNumTrains'|'getNumFromTo'|'getNumWaiting'|'getNumMissed'|'checkBottleneck'|'getAvgWaitTime';
MOD_FUNCTIONS: 'changeRate'|'changeFrequency'|'changeSpeed'|'skipStation';
PRIMITIVE_TYPE: 'num';
//DERIVED_TYPE: 'Station' | 'Line' | 'Time' | 'Population';
ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' |'_'| 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT : '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;
NUM : INTEGER ('.'? INTEGER)? ;