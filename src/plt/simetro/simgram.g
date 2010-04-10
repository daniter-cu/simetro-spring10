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
fullexp : exp ;
exp : term (('+' | '-') term )* ;
term : factor (('*' | '/') factor)*;
factor : INTEGER ;

program : line ;

//lines
line: 
    'Line' ID '{'
    'Stations' '('idlist ')' ';' 
    'Frequency' '(' (ID | INTEGER) ')' ';'
    'Capacity' '('(ID | INTEGER) ')' ';'
    'Speed' '(' (ID | INTEGER) ')' ';' 
    '}' 
    ;

idlist : ID (',' ID)* ;




ID: ('a' ..'z' | 'A'..'Z')('a' ..'z' | 'A'..'Z' | '0'..'9')*;
WS: (' ' | '\n' | '\t' | '\r' | '\f' )+ {$channel = HIDDEN;} ;
INTEGER: ('0' .. '9')+;