tree grammar simwalk;

options {
  output = AST;
  tokenVocab=simgram;
  ASTLabelType = CommonTree;
}

@header {
  package plt.simetro;
}

/*
***************************
THIS DOESN'T WORK YET!!

***************************
*/

/*
program: 
        (declarations |statements)* 
        ;
        */
program: (line|station|population|stat|simulate|statements|load|showgui|string|time)*                               
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
    (LINE ID ^(STATIONS idlist) ^(FREQUENCY INTEGER) ^(CAPACITY INTEGER) ^(SPEED INTEGER) )
    ;
    
station:
    ^(STATION ID ^(COORDINATES INTEGER INTEGER) ^(POPULATION ID))
    | ^(STATION ID)
    ;

population:
    ^(POPULATION ID (^(POPITEM ID primaryExpr))* )
    ;

idlist:
    ^(IDLIST ID ID*) 
    ;

//==OBJECT VARIABLES== 

stat:
    ^(STAT ID formal_params statements ^(RETURN ID))
        ;

//time
time:
        ^(TIME ID i=INTEGER j=INTEGER?) {System.out.print("Time "+ $ID.text +" = new Time(");
                                          if(j != null) 
                                              System.out.println($i.text + ", " +$j.text + ");");
                                          else
                                              System.out.println($i.text + ");"); }
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
        expression_statement 
        |foreach
        |forloop
        |ifstmt
        |procedures
    //    |simulate

        //|types
        |print_function
        //|mod_procedures
        ;
    
expression_statement:
        assignsExpr
        ;

blockstmt:
         ^(BLOCKSTMT statements) 
          ;

derived_type: 'Station' | 'Line' | 'Time' | 'Population' ;      
       
foreach:
        'foreach'^ derived_type ID blockstmt
        ;

forloop:
        'for'^ ID INTEGER INTEGER blockstmt
        ;

ifstmt:
        ^(IF arithExpr blockstmt ^(ELSE blockstmt))
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
        FUNCTIONS^  (params|formal_params) 
        |mod_procedures
       // |print_function
        //|showgui

        ;
        
mod_procedures:
        MOD_FUNCTIONS^  params 
        ;   
      
print_function:
        'print'^ (STRING |procedures | ID) {System.out.println("System.out.println(\" Hello World\")");}
        ;
               
showgui:
        ShowGUI
        ;

load:
        'load'^ ID 
        ;

simulate:
        'Simulate'^  INTEGER  blockstmt
        ;

//==PARAMETERS
fragment formal_params:
        ^(FORMALPARAM formal_param)
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



