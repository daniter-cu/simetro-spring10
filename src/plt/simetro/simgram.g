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



//this is a simple arithmetic grammar

arithExpr : 
					logicExpr
					;

logicExpr :	
					relExpr (('and' | 'or') relExpr)*
					;
//DO WE WANT LOGICAL EXPRESSIONS; DO WE WANT TO USE AND, OR?	

relExpr :	
				addExpr (('=' | '!=' | '<' | '>' | '<=' | '>=') addExpr)*
				;
//DO WE WANT != FOR NOT EQUALS?				
											
addExpr : 
					multExpr (('+' | '-') multExpr)*
					;

multExpr :	
					unaryExpr (('*' | '/' | '%') unaryExpr)*
					;
											
unaryExpr :	
					('+' | '-')* negation
					;
														
negation :	
					'not'* term
					;
//DO WE WANT TO USE 'NOT' FOR NEGATION?
											
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
        {setText(b.toString()); };
        

ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' | 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
COMMENT : '//' .* ('\n' | '\r') {$channel = HIDDEN;};
MULTICOMMENT : '/*' .* '*/' {$channel = HIDDEN;};
INTEGER: ('0' .. '9')+;