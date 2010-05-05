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

//this is to inline declare population/popitems
ArrayList<String> popnames= new ArrayList<String>();
}






/*
***************************
THIS WORKS SLIGHTLY!

***************************
*/


//problem with * at end of prog - very weird!
program: (declarations |statements)*
        ;
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
   // System.out.println("Line " + $ID + "= new Line("+$i.s+","+$f.text+"," +$c.text +","+ $s.text+");");
    }
    //template
    ->template(separator = {","}, id = {$ID.text}, i={$i.s}, f = {$f.text}, c={$c.text}, s={$s.text} )
    "Line <id> = new Line(<i><separator> <f><separator> <c><separator> <s>);"
    ;

//WE HAVE A CIRCULARITY - WHICH WAY ARE WE GOING POP FIRST OR STATIONS
station:
    ^(STATION sname=ID ^(COORDINATES i=INTEGER j=INTEGER) ^(POPULATION pname=ID))
   
    {
    //String cord = newCord();
    //System.out.println("Coordinate " +cord +" = new Coordinate("+$i.text+","+$j.text+");");
    //System.out.println("Station " +$sname.text+" = new Station("+cord+","+$pname.text+");");
    }
    //template
    ->template(separator = {","}, sname = {$sname.text}, cord = {$i.text+", "+$j.text}, pname = {$pname.text}  )
    "Station <sname> = new Station(new Coordinate(<cord>)<separator> <pname>)"
    
     | ^(STATION sname=ID) {System.out.println("Station " +$sname.text+";"); }
     //template
     ->template(separator = {","}, sname = {$sname.text}, cord = {$i.text+", "+$j.text})
     "Station <sname> = new Station(new Coordinate(<cord>);"
    ;
    


population:
    ^(POPULATION i=ID popitem* )
    {
    String poplist = new String();
    //System.out.println("Population "+$i.text+" = new Population();");
    //  for(String n : popitems)
     // {
      //  System.out.println($i.text + ".addPopItem("+n+");");
        
     // }
    poplist = popnames.toString();
    //this is so it doesn't continually add popitems when not associated with the correct population
    popitems.clear();
    popnames.clear();
    }
    //template
    ->template(i = {$i.text}, p = {poplist} )
    "Population <i> = new Population(<p>);
    "
    ;
    
  
popitem:
    ^(POPITEM ID INTEGER) 
    { 
   //   String name = newID();
   //   System.out.println("PopItem " + name + " = new PopItem("+ $ID +","+$INTEGER +");");
    //  popitems.add(name);
    //  popnames.add(new String("("+$ID +","+$INTEGER+")"));
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
    ^(STAT i=ID f=formal_params statements ^(RETURN ID)) //fix this, should be able to return text nums too
    ->template(id = {$i.text}, fp = {$f.text} )
    <<
    public static int <id> ( <fp; separator=", "> )
    {
    
    
    }
    >>
        ;

//time
time:
        ^(TIME ID i=INTEGER j=INTEGER?)  
       /* {//System.out.print("Time "+ $ID.text +" = new Time(");
            if(j != null) 
           // System.out.println($i.text + ", " +$j.text + ");");
            else
            //System.out.println($i.text + ");"); }
            }*/
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
        ^(IF arithExpr blockstmt)
        ;
        


//==EXPRESSIONS==

assignsExpr:
        ^('=' primitive_type_declarator arithExpr )
        | ^('=' ID arithExpr)
        ;

arithExpr:
        ^('and' arithExpr arithExpr)
        |^('or' arithExpr arithExpr)
        |^('!=' arithExpr arithExpr)
        | ^('=='arithExpr arithExpr)
        | ^('<' arithExpr arithExpr)
        | ^('>' arithExpr arithExpr)
        | ^('<='arithExpr arithExpr)
        | ^('>='arithExpr arithExpr)
        | ^('+' arithExpr arithExpr)
        | ^('-' arithExpr arithExpr)
        | ^('*' arithExpr arithExpr)
        | ^('/' arithExpr arithExpr)
        | ^('%' arithExpr arithExpr)
        | ^('^' arithExpr arithExpr)
        | ^('-' (ID|NUM|INTEGER) )  //unary expression -- unsure play with neg of function return
        | ^('+' (ID|NUM|INTEGER) )
        | procedures
        | ID
        | NUM
        | INTEGER
        | String

        ;

//these might be unecessary below now that arithExpr is merged
unaryExpr:
        ^('+' primaryExpr)
        | ^('-' primaryExpr)
        | primaryExpr    
        ;

primaryExpr:
        ID
        |NUM
        |INTEGER
        |procedures
        ;
//==PROCEDURES

procedures:
        ^(FUNCTIONS  params )
        | func_call
        |mod_procedures
        |print_function
        ;
        
func_call:
      ^(FUNC_CALL ID params)
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
        ^('Simulate' INTEGER  blockstmt)
        ;

//==PARAMETERS
formal_params returns [String str]:
        ^(FORMALPARAM f+=formal_param*) {}
        ;

formal_param returns [String str]: 
        ^(PRIMITIVE_TYPE ID)  {$str = $PRIMITIVE_TYPE.text + $ID.text;}
        |^('Station' ID)  {$str = "Station" + $ID.text;}
        |^('Line' ID)  {$str = "Line" + $ID.text;}
        |^('Population' ID) {$str = "Population" + $ID.text;}  
        |^('Time' ID)  {$str = "Time" + $ID.text;}
        |^('String' ID) {$str = "String" + $ID.text;}
        ; 

params:
        ^(PARAM ((ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER))* )
        ;

