tree grammar simwalk;

options {
  tokenVocab=simgram;
  ASTLabelType = CommonTree;
  output = template;
  rewrite = true;
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
THIS WORKS SLIGHTLY!

***************************
*/


//problem with * at end of prog - very weird!
program: (declarations |statements);

//program: (line|station|population|stat|simulate|statements|load|showgui|string|time)*   
//program: (simulate|line|station|population|stat|statements|load|showgui|string|time|primitive_type_declarator)*   
//;



declarations:
        types
        |load
        ;
        
types:
    (line|station|population|stat|time|string|showgui)
    ;

//==DERIVED TYPES==

line: 
    ^(LINE ID ^(STATIONS i=idlist) ^(FREQUENCY f=INTEGER) ^(CAPACITY c=INTEGER) ^(SPEED s=INTEGER) )
    {
    System.out.println("Line " + $ID + "= new Line("+$i.s+","+$f.text+"," +$c.text +","+ $s.text+");");
    }
    //template
    ->template(separator = {","}, id = {$ID.text}, i={$i.s}, f = {$f.text}, c={$c.text}, s={$s.text} )
    "Line <id> = new Line(<i><separator> <f><separator> <c><separator> <s>);"
    ;

//note: inconsistency with ending ';' at station option 1 vs option2
station:
    ^(STATION sname=ID ^(COORDINATES i=INTEGER j=INTEGER) ^(POPULATION pname=ID))
   
    {
    String cord = newCord();
    System.out.println("Coordinate " +cord +" = new Coordinate("+$i.text+","+$j.text+");");
    System.out.println("Station " +$sname.text+" = new Station("+cord+","+$pname.text+");");
    }
    //template
    ->template(separator = {","}, sname = {$sname.text}, cord = {$i.text+", "+$j.text}, pname = {$pname.text}  )
    "Station <sname> = new Station(new Coordinate(<cord>)<separator> <pname>)"
    
     | ^(STATION sname=ID) {System.out.println("Station " +$sname.text+";"); }
     //template
     ->template(separator = {","}, sname = {$sname.text})
     "Station <sname>;"
    ;
    

//NOT WERKING
//note sure about syntax here - can't seem to get this working
population:
    ^(POPULATION i=ID popitemz=(popitem)* )
    {
    System.out.println("Population "+$i.text+" = new Population();");
      for(String n : popitems)
      {
        System.out.println($i.text + ".addPopItem("+n+");");
      }

    }
    //template
    ->template(i = {$i.text}, popitemz = {$popitemz} )
    "Population <i> = new Population();
    <popitemz>
    "
    ;
    
popitem:
    ^(POPITEM ID INTEGER) 
    { 
      String name = newID();
      System.out.println("PopItem " + name + " = new PopItem("+ $ID +","+$INTEGER +");");
      popitems.add(name);
    }
    //template
	//->template()
	//""
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
        ^(TIME ID i=INTEGER j=INTEGER?)  
        {System.out.print("Time "+ $ID.text +" = new Time(");
            if(j != null) 
            System.out.println($i.text + ", " +$j.text + ");");
            else
            System.out.println($i.text + ");"); }
        ;



//not working properly - forgets quotes
//defining strings
string:
        ^('String' ID '=' STRING) 
        | ^('String' ID) 
        ;

//primitive-type-declarator
primitive_type_declarator:
        ^(PRIMITIVE_TYPE ID)  
        ;


//==STATEMENTS==

statements:
        expression_statement 
        |foreach
        |forloop
        |ifstmt
        |procedures
        |simulate
        
        //|types
        //|print_function
        //|mod_procedures
        ;
 
expression_statement:
        assignsExpr
        ;

blockstmt:
         ^(BLOCKSTMT statements) 
          ;

derived_type: 
    'Station' | 'Line' | 'Time' | 'Population' 
    ;      

//not sure how to translate this into java....will we have collections?       
foreach:
        ^('foreach' derived_type ID blockstmt)
        ;

forloop:
        ^('for' ID INTEGER INTEGER blockstmt)
        //-> template()
        //""
        ;

ifstmt:
        ^(IF arithExpr blockstmt ^(ELSE blockstmt))
        //-> template()
        //""
        
        ;
        


//==EXPRESSIONS==

assignsExpr: 
        ^(ID '=' arithExpr)
        | ^(primitive_type_declarator '=' arithExpr)
        ;

arithExpr:
        logicExpr
        ;

logicExpr:
        ^('and' relExpr relExpr)
        |^('or' relExpr relExpr)
        ;

relExpr:
        ^('!=' addExpr addExpr)
        | ^('==' addExpr addExpr)
        | ^('<' addExpr addExpr)
        | ^('>' addExpr addExpr)
        | ^('<=' addExpr addExpr)
        | ^('>=' addExpr addExpr)
        ;
        
addExpr:
        ^('+' multExpr multExpr)
        | ^('-' multExpr multExpr)
        ;

multExpr:
        ^('*' powerExpr powerExpr)
        |^('/' powerExpr powerExpr)
        |^('%' powerExpr powerExpr)
        ;
                        
powerExpr:
        ^('^' unaryExpr unaryExpr)
        ;
                                                                                                            
unaryExpr:
        ^('+' primaryExpr primaryExpr)
        |^('-' primaryExpr primaryExpr)
        ;

primaryExpr:
        ID
        |NUM
        |INTEGER
        |procedures
        ;

//==PROCEDURES

procedures:
        ^(FUNCTIONS  (params|formal_params) )
        |mod_procedures
        |print_function
        //|showgui

        ;
        
mod_procedures:
        ^(MOD_FUNCTIONS  params)
        ;   
     
print_function:
        ^('print' (STRING |procedures | ID) )
        {System.out.println("System.out.println(\" Hello World\")");}
        //-> template( mystring = {$STRING.text}, myproc = {$procedures}, myID = {$ID.text})
        ;

               
showgui:
        ShowGUI
        ;

load:
        ^('load' ID) 
        ;

simulate:
        ^('Simulate'  INTEGER  blockstmt)
        ;

//==PARAMETERS
formal_params:
        ^(FORMALPARAM formal_param)
        ;

formal_param: 
        ^(PRIMITIVE_TYPE ID)  
        |^('Station' ID) 
        |^('Line' ID)  
        |^('Population' ID)  
        |^('Time' ID)  
        |^('String' ID)
        ; 

params:
        (ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER) ( ',' (ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER) )*
        ;


