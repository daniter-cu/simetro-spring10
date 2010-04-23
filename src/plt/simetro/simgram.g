
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


/*
program: (line|station|population|stat|simulate|statement|load|showgui|string)*	 EOF!																		
		;
*/
program: 
        (declarations |statements)*
        EOF!
        ;
        
declarations:
        types
        |load
        ;
        
types:
    (line|station|population|stat|time|string|showgui)
    ;

//==DERIVED TYPES==

line: 
    'Line' ID  '{'
    'Stations' '(' idlist ')' ';' 
    'Frequency' '(' (f=ID | f=INTEGER) ')' ';'
    'Capacity' '(' (c=ID | c=INTEGER) ')' ';'
    'Speed' '(' (s=ID | s=INTEGER) ')' ';' 
    '}' 
    -> ^(LINE ID ^(STATIONS idlist) ^(FREQUENCY $f) ^(CAPACITY $c) ^(SPEED $s) )
    ;

station:
	'Station' ID '{'
	(('Coordinates' '(' i1=INTEGER ',' i2=INTEGER ')' ';'))?
	(('Population' '(' ID ')' ';'))*
	'}'
	-> ^(STATION ID ^(COORDINATES $i1 $i2)? ^(POPULATION ID)?)
	;

population:
	'Population' i=ID '{'
	('('j+=ID ','k+=primaryExpr')')+
	'}' -> ^(POPULATION $i ^(POPITEM $j $k)*)
	;

idlist : i=ID (',' il+=ID)* -> ^(IDLIST $i $il*) ;

//==OBJECT VARIABLES== 

stat:
        'Stat' ID '(' f=formal_params ')' '{'
        (s+=statements)*
        'return' r=ID ';'
        '}' -> ^(STAT ID $f $s ^(RETURN $r))
        ;

//time
time:
        'Time' ID '[' i+=INTEGER (',' i+=INTEGER)? ']' ';' -> ^(TIME ID $i+)
        ;

//defining strings
string:
        'String'^ ID '=' STRING ';'! 
        | 'String'^ ID ';'!
        ;

//primitive-type-declarator
primitive_type_declarator:
        PRIMITIVE_TYPE^ ID		
		;


//==STATEMENTS==

statements:
        expression_statement ';'! 
        |foreach
        |forloop
        |ifstmt
        |procedures ';'! 
        |simulate

        //|types
        //|print_function
        //|mod_procedures
        ;
	
expression_statement:
        assignsExpr
		;

blockstmt:
          '{'(s+=statements)* '}' -> ^(BLOCKSTMT statements) 
          ;

derived_type: 'Station' | 'Line' | 'Time' | 'Population' ;      
       
foreach:
        'foreach'^ derived_type ID blockstmt
        ;

forloop:
        'for'^ ID '['! INTEGER ','! INTEGER ']'! blockstmt
        ;

ifstmt:
        'if' '(' a=arithExpr')' b=blockstmt ('else' c=blockstmt)? -> ^(IF $a $b ^(ELSE $c)?)
        ;


//==EXPRESSIONS==

assignsExpr: 
        (ID^ | primitive_type_declarator^) '=' arithExpr
        ;

arithExpr:
        logicExpr
		;

logicExpr:	
        relExpr (('and'^ | 'or'^) relExpr)*
		;

relExpr:
        addExpr (( '!='^ | '=='^ | '<'^ | '>'^ | '<='^ | '>='^) addExpr)*
		;
		
addExpr:
        multExpr (('+'^ | '-'^) multExpr)*
        ;

multExpr: 
        powerExpr (('*'^ | '/'^ | '%'^) powerExpr)*
        ;
						
powerExpr:
        unaryExpr ('^'^ unaryExpr)*
        ;
																											
unaryExpr:
        ('+'^ | '-'^)? primaryExpr
        ;

primaryExpr:
		ID
		|NUM
		|INTEGER
		|procedures
		;

//==PROCEDURES

procedures:
        FUNCTIONS^ '('! (params|formal_params) ')'!
        |mod_procedures
        |print_function
        //|showgui

        ;
        
mod_procedures:
        MOD_FUNCTIONS^ '('! params ')'!
        ;   
      
print_function:
        'print'^ (STRING |procedures | ID)
        ;
               
showgui:
        'ShowGUI();' -> ShowGUI
        ;

load:
        'load'^ ID '.map'!  ';'!
        ;

simulate:
        'Simulate'^ '('! INTEGER ')'! blockstmt
        ;

//==PARAMETERS
fragment formal_params:
        a+=formal_param ( ',' a+=formal_param)* -> ^(FORMALPARAM $a*)
        ;

fragment formal_param: 
        PRIMITIVE_TYPE^ ID  
        |'Station'^ ID 
        |'Line'^ ID  
        |'Population'^ ID  
        |'Time'^ ID  
        |'String'^ ID
        ; 

fragment params:
        (ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER) ( ',' (ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER) )*
        ;



//==TOKENS==
STRING : '"'
        {StringBuilder b = new StringBuilder();}
        ( '\\' '"'  {b.appendCodePoint('"'); }
        |  c = ~('"' | '\r' | '\n')   {b.appendCodePoint(c);  } 
        )*
        '"'  
        {setText(b.toString()); }
        ;
        
//STRING : '"' .* '"'; //fix strings!
FUNCTIONS: 'getRate'|'getFrequency'|'getCapacity'|'getSpeed'|'getNumLines'|'getNumStation'|'getNumPassengers'|'getNumInStation'|'getNumOnLine'|'getNumTrains'|'getNumFromTo'|'getNumWaiting'|'getNumMissed'|'checkBottleneck'|'getAvgWaitTime';
MOD_FUNCTIONS: 'changeRate'|'changeFrequency'|'changeSpeed'|'skipStation';
PRIMITIVE_TYPE: 'num' | 'int';
//DERIVED_TYPE: 'Station' | 'Line' | 'Time' | 'Population';
ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' |'_'| 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT: '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT: '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;
NUM: INTEGER ('.'? INTEGER)? ;