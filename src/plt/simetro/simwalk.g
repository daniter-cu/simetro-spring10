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

String iter ="";
ArrayList<String> popitems = new ArrayList<String>();

//this is to inline declare population/popitems
ArrayList<String> popnames= new ArrayList<String>();
HashMap<String, String> popstationMap = new HashMap<String, String>();
}



/*
***************************
THIS WORKS SLIGHTLY BETTER!

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
    ^(LINE ID ^(STATIONS i=idlist) ^(FREQUENCY f=NUM) ^(CAPACITY c=INTEGER) ^(SPEED s=INTEGER) )
    {
   // System.out.println("Line " + $ID + "= new Line("+$i.s+","+$f.text+"," +$c.text +","+ $s.text+");");
    String[] stationArr= new String($i.text).split(",");
    iter +="a";
    }
    //template
    ->template(n = {iter}, separator = {","}, id = {$ID.text}, i={$i.s}, f = {$f.text}, c={$c.text}, s={$s.text} )
    //"Line <id> = new Line(<i><separator> <f><separator> <c><separator> <s>);"  
    "
    for (Object <n> : new ArrayList\<Object\>( Arrays.asList(new String(\"<i>\").replaceAll(\"\\\s+|\\\(|\\\)\", \"\").split(\",\")))) {
        tempList_stations.add(stationMap.get((String)<n>));
    }
    lineList.add(new Line(\"<id>\"<separator> <f><separator> <c><separator> <s><separator>new ArrayList\<Station\>(tempList_stations)));
    tempList_stations.clear();"
    ;


station:
    ^(STATION sname=ID ^(COORDINATES i=INTEGER j=INTEGER) (^(POPULATION pname=ID))* )
    {
    //String cord = newCord();
    //System.out.println("Coordinate " +cord +" = new Coordinate("+$i.text+","+$j.text+");");
    //System.out.println("Station " +$sname.text+" = new Station("+cord+","+$pname.text+");");
    popstationMap.put($pname.text, $sname.text);
    //System.out.println(stationpopMap.values().toArray().toString() );
    }
    //declared with population
    ->template(separator = {","}, sname = {$sname.text}, cord = {$i.text+", "+$j.text}, pname = {$pname}  )
    //"Station <sname> = new Station(new Coordinate(<cord>)<separator> <pname>)"
    "stationList.add(new Station(\"<sname>\"<separator> new Coordinate(<cord>)));
    stationMap.put(\"<sname>\"<separator> stationList.get(stationList.size() - 1));"
    
     | ^(STATION sname=ID) 
     {System.out.println("Station " +$sname.text+";"); }
     //declared without population
     ->template(separator = {","}, sname = {$sname.text}, cord = {$i.text+", "+$j.text})
     //"Station <sname> = new Station(new Coordinate(<cord>);"
     "stationList.add(new Station(\"<sname>\"<separator> new Coordinate(<cord>)<separator> <pname>));
     stationMap.put(\"<sname>\"<separator> stationList.get(stationList.size() - 1));"
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
    iter += "a";
    String stn = popstationMap.get($i.text);
    }
    //template
    ->template(i = {$i.text}, p = {poplist}, mystn = {stn}, n = {iter} )
    //"Population <i> = new Population(<p>);"
    "for (Object <n> : new ArrayList\<Object\>( Arrays.asList(new String(\"<p>\").replaceAll(\"\\\s+|\\\(|\\\)|\\\[|\\\]\", \"\").split(\",\")))) {
        popItemList.add(new PopItem(stationMap.get(new String(<n>.toString().split(\"\\\.\")[0])), Double.parseDouble(new String(<n>.toString().split(\"\\\.\")[1]))));
    }
    stationMap.get(\"<mystn>\").setPop(new Population(popItemList));
    ";
    
  
popitem:
    ^(POPITEM ID INTEGER) 
    { 
    //   String name = newID();
    //   System.out.println("PopItem " + name + " = new PopItem("+ $ID +","+$INTEGER +");");
    //  popitems.add(name);
    
    //use this to populate bunch of popitems and then output per POPULATION
      popnames.add(new String("("+$ID +"."+$INTEGER+")"));
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

//String
string:
        ^('String' myid=ID '=' str=STRING)
        ->template(myid={$myid.text}, str={$str.text} ) 
        "String <myid> = \"<str>\";"
        
        | ^('String' ID) 
        ->template(myid={$myid.text} ) 
        "String <myid>;"        
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

/*assuming we can only iterate over
stationList
populationList
lineList
popItems possible, but probably will only be used internally
so we have to declare these as globals from the onset
*/   
foreach:
        ^('foreach' derived_type ID blockstmt)
        {
        //need to choose proper global type
        
        
        }
        -> template(dtype = {$derived_type.text}, myID = {$ID.text}, blk = {$blockstmt.text})
        "for Object n : <dtype>"
        ;

//if we do x[0,-9] this is setup to count backwards..
//had to change grammar to for loop to accomodate negatives (hence unaryExpr)
forloop:
        ^('for' myid=ID int1=unaryExpr int2=unaryExpr blockstmt)
        {
        String inc,rel;
        if (Integer.parseInt($int2.text) > Integer.parseInt($int1.text)) 
            {inc = "++"; rel="<"; } else { inc = "--"; rel=">"; }
        
        }
        -> template(myID = {$myid.text}, INT1 = {$int1.text}, INT2 = {$int2.text}, myinc = {inc}, myrel = {rel}, blk = {$blockstmt.text} )
        "for (int <myID>=<INT1>; <myID> <myrel> <INT2>; <myID><myinc> ) <blk>"
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
        ^('print' STRING )
        -> template( mystring = {$STRING.text} )
        "System.out.println(\"<mystring>\");"
        
        | ^('print' procedures )
        -> template( myproc = {$procedures.text} )
        "System.out.println(<myproc>);"
        
        | ^('print' ID )  
        -> template( myID = {$ID.text} )
        "System.out.println(<myID>);"
        //{System.out.println("System.out.println(\" Hello World\")");}
        ;

showgui:
        ShowGUI
        ;

//THIS IS TRICKY..
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

