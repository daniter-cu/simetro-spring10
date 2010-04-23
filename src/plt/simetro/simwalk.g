tree grammar simwalk;

options {
  tokenVocab=simgram;
  ASTLabelType = CommonTree;
  output=AST;
}


@header {
  package plt.simetro;
}

@members {
boolean listbool = true;
String curr = "";
String cord = "";
public String newID()
{
  curr += "a";
  return curr;  
}
public String newCord()
{
  cord+="c";
  return cord;
}


ArrayList<String> popitems = new ArrayList<String>();
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
 /*       
declarations:
        types
        |load
        ;
        
types:
    (line|station|population|stat|time|string|showgui)
    ;
*/
//==DERIVED TYPES==

line: 
    ^(LINE ID ^(STATIONS i=idlist) ^(FREQUENCY f=INTEGER) ^(CAPACITY c=INTEGER) ^(SPEED s=INTEGER) )
    {
    System.out.println("Line " + $ID + "= new Line("+$i.s+","+$f.text+"," +$c.text +","+ $s.text+");");
    
    }
    ;
    
station:
    ^(STATION sname=ID ^(COORDINATES i=INTEGER j=INTEGER) ^(POPULATION pname=ID))
   
    {
    String cord = newCord();
    System.out.println("Coordinate " +cord +" = new Coordinate("+$i.text+","+$j.text+");");
    System.out.println("Station " +$sname.text+" = new Station("+cord+","+$pname.text+");");
    }
     | ^(STATION ID) {System.out.println("Station " +$sname.text+";"); }
    ;

population:
    ^(POPULATION i=ID popitem* )
    {System.out.println("Population "+$i.text+" = new Population();");
      for(String n : popitems)
      {
        System.out.println($i.text + ".addPopItem("+n+");");
      }
    }
    ;
    
popitem:
    ^(POPITEM ID INTEGER) 
    { String name = newID();
      System.out.println("PopItem " + name + " = new PopItem("+ $ID +","+$INTEGER +");");
      popitems.add(name);
    }
    ;

idlist returns [String s]
@init{$s= "(";}:
    ^(IDLIST (i=ID {
    if(listbool == true)
     { $s+= $i.text;
        listbool = false;
     }
    else
     {  $s+=", ";
        $s+=$i.text;}
    })+) 
    {$s+=")"; listbool = true;}
    ;

//==OBJECT VARIABLES== 

stat:
    ^(STAT ID formal_params statements ^(RETURN ID))
        ;

//time
time:
        ^(TIME ID i=INTEGER j=INTEGER?)  {System.out.print("Time "+ $ID.text +" = new Time(");
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



