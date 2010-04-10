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
arithmetic : exp ;
exp : term (('+' | '-') term )* ;
term : factor (('*' | '/') factor)*;
factor : INTEGER ;

program : line ;

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