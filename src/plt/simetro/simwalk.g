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

String last = "\n}\n";

public void modLast(String s)
{
  last += s;
}

public String printThis()
{
  return last;
}


ArrayList<String> funcs = new ArrayList<String>();


}



/*
***************************
THIS WORKS SLIGHTLY BETTER!

***************************
*/


//problem with * at end of prog - very weird!
program: 
(declarations |statements)* 

;
//program: (line|station|population|stat|simulate|statements|load|showgui|string|time)*   
//program: (simulate|line|station|population|stat|statements|load|showgui|string|time|primitive_type_declarator)*   
//;

declarations returns [String print]:
        types {$print = $types.print;}
      //  |load
        ;
        
types returns [String print]
@init {$print = "}";}:
    (line|station|population|i=stat { $print = $i.print + "\n}\n";} |time|string|showgui) 
    
    ;

//==DERIVED TYPES==

line: 
    ^(LINE ID ^(STATIONS i=idlist) ^(FREQUENCY f=NUM) ^(CAPACITY c=INTEGER) ^(SPEED s=NUM) )
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
    //lineList.add(new Line(\"<id>\"<separator> <f><separator> <c><separator> <s><separator>new ArrayList\<Station\>(tempList_stations)));
    Line <id> = new Line(\"<id>\"<separator> <f><separator> <s><separator> <c><separator>new ArrayList\<Station\>(tempList_stations));
    lineList.add(<id>);
    lineList.get(lineList.size() - 1).setRvsLine();
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
    
    "Station <sname> = new Station(\"<sname>\"<separator> new Coordinate(<cord>));
    stationList.add(<sname>);
    //stationList.add(new Station(\"<sname>\"<separator> new Coordinate(<cord>)));
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

stat returns [String str, String print]
@init {$str = ""; $print ="";}:
    ^(STAT i=ID f=formal_params ((s=statements) {$str+=$s.text + "\n";})* ^(RETURN j=ID)) //fix this, should be able to return text nums too
    {$print += "public static double " + $i.text + "( " + $f.str + " )\n";
      $print += "{\n";
      $print += $str + "\n";
      $print += "return " + $j.text +";\n";
      $print += "}\n";
 
      //System.out.println($print);
      modLast($print);
      funcs.add($i.text);
    }
    ->template() ""
   // ->template(id = {$i.text}, fp = {$f.str}, s= {$str} ,j = {$j.text})
   // <<
   // public static int <id> ( <fp> )
   // {
    //    <s>
    //    return <j>;
   // }
   // >>
        ;

//time
time:
        ^(TIME id=ID i=INTEGER j=INTEGER?)  ->template(id={$ID.text}, i={$i.text}, j={$j.text})
        "int <id>[] = {<i>,<j>};"
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
primitive_type_declarator returns [String str]:
        ^(PRIMITIVE_TYPE i=ID)  {$str = "double " + $ID.text;} -> template(i={$i}) "double <i>;"
        ;


//==STATEMENTS==

statements  :
        expression_statement 
        |foreach 
        |forloop 
        |ifstmt 
        |func_call
        | print_function 
        |simulate
        | mod_procedures
        ;
 
expression_statement returns [String str]:
        assignsExpr {$str = $assignsExpr.str;}
        | primitive_type_declarator  {$str = $primitive_type_declarator.str + ";";}
        ;

blockstmt:
         ^(BLOCKSTMT statements*) 
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
        String type_list="";
        String type="";
        
        if (new String($derived_type.text).equals("Station")) {
	        type = "Station"; 
	        type_list = "stationList";
	        }
	    else if (new String($derived_type.text).equals("Line")) {
            type = "Line";
            type_list = "lineList";
            }
	        //case "Time": type_list break;
	    else if (new String($derived_type.text).equals("Population")) {
            type="Population";
            type_list = "populationList";
            }
        }
        -> template(dtype = {type}, dlist = {type_list}, myID = {$ID.text}, blk = {$blockstmt.text})
        "
        for (<dtype> <myID> : <dlist>)
        <blk>
        "
        ;
//"for (Object <n> : new ArrayList\<Object\>( Arrays.asList(new String(\"<p>\").replaceAll(\"\\\s+|\\\(|\\\)|\\\[|\\\]\", \"\").split(\",\")))) {
//popItemList.add(new PopItem(stationMap.get(new String(<n>.toString().split(\"\\\.\")[0])), Double.parseDouble(new String(<n>.toString().split(\"\\\.\")[1]))));
//}

//if we do x[0,-9] this is setup to count backwards..
//had to change grammar to for loop to accomodate negatives (hence unaryExpr)
forloop:
        ^('for' myid=ID int1=arithExpr int2=arithExpr blockstmt)
        {
        String inc,rel;
        if (Integer.parseInt($int2.text) > Integer.parseInt($int1.text)) 
            {inc = "++"; rel="<"; } else { inc = "--"; rel=">"; }
        
        }
        -> template(myID = {$myid.text}, INT1 = {$int1.text}, INT2 = {$int2.text}, myinc = {inc}, myrel = {rel}, blk = {$blockstmt.text} )
        "for (int <myID>=<INT1>; <myID> <myrel> <INT2>; <myID><myinc> ) <blk>"
        ;

ifstmt:
        ^(IF arithExpr b1=blockstmt i=(^(ELSE b2=blockstmt))?)
       // ^(IF arithExpr blockstmt)
        ;
        


//==EXPRESSIONS==

assignsExpr returns [String str]:
        ^('=' primitive_type_declarator arithExpr ) {$str = $primitive_type_declarator.str +" = "+ $arithExpr.text+";";}
        ->template(str={$str}) "<str>"
        | ^('=' i=ID a=arithExpr)  {$str = $i.text + " = " + $a.text+";";}
         ->template(str={$str}) "<str>"
        ;

arithExpr:
        ^('and' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> and <j>)"
        |^('or' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> or <j>)"
        |^('!=' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> != <j>)"
        | ^('=='i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> == <j>)"
        | ^('<' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> < <j>)"
        | ^('>' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> > <j>)"
        | ^('<='i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> <= <j>)"
        | ^('>='i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> >= <j>)"
        | ^('+' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> + <j>)"
        | ^('-' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> - <j>)"
        | ^('*' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> * <j>)"
        | ^('/' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> / <j>)"
        | ^('%' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "(<i> % <j>)"
        | ^('^' i=arithExpr j=arithExpr) ->template(i={$i.text}, j={$j.text}) "Math.pow(<i>, <j>)"
        | ^('-' (ID|NUM|INTEGER) )  //unary expression -- unsure play with neg of function return
        | ^('+' (ID|NUM|INTEGER) )
        | func_call
          | ID
          | NUM //-> template(n={$n.text})"<n>"
        | INTEGER
     //   | String
        ;
    
//==PROCEDURES
//procedures:
     //   ^(FUNCTIONS  params )
  //       func_call
    //    |mod_procedures
      //  |print_function
      //  ;
        
func_call returns [String fun]:
      ^(FUNC_CALL ID params)
      {$fun = $ID.text;
      if (!funcs.contains($fun))
        $fun = "tl." + $ID.text;
      
      }
      -> template(i={$fun}, p = {$params.text}) "<i>(<p; separator=\", \">)"
      ;
        
mod_procedures:
        ^(MOD_FUNCTIONS  params)
        -> template(i={$MOD_FUNCTIONS.text}, p = {$params.params}, t= {$params.time}) 
        <<
        if(time_iter == <t>[0])
          sim.<i>(<p; separator=", ">);
        >>
        ;   
     
print_function returns [String print]
@init{$print = "";}:
    /*    ^('print' STRING )
        -> template( mystring = {$STRING.text} )
        "System.out.println(\"<mystring>\");"
        
        | ^('print' func_call )
        -> template( myproc = {$func_call.text} )
        "System.out.println(<myproc>);"
        
        | ^('print' ID )  
        -> template( myID = {$ID.text} )
        "System.out.println(<myID>);"*/
        
        ^('print' (s=STRING {$print+="\""+$s+"\""+"+";}| s=ID {$print+=$s+"+";}| f=func_call{$print+=$s+"+";})*)
        ->template(s={$print})
        "System.out.println(<s>\"\");"
        //{System.out.println("System.out.println(\" Hello World\")");}
        ;

showgui:
        ShowGUI -> template()
        <<
        ShowGui sg=new ShowGui();
        sg.Show(stationList, lineList,tl.getAllTrains());
        >>
        ;

//THIS IS TRICKY..
//load:
 //       ^('load' ID) 
 //       ;

simulate:
        ^('Simulate' INTEGER  blockstmt)
        ->template( blk = {$blockstmt.text}, time = {$INTEGER} )
        <<
        Simulate sim=new Simulate();
        //TimeLine tl=new TimeLine();
        
        sim.createRoutingTables(stationList,lineList);
        
        
        for(int time_iter=0; time_iter \< <time> ;time_iter++){
                        System.out.println("\n*********************************At time "+time_iter+"***********************************");
                        sim.peopleArrive(stationList, time_iter, tl);
                        sim.trainArrive(lineList, time_iter);
                        sim.trainMove(time_iter,tl);
                        
                     /*   System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");
*/


           <blk>
        }
        /*
        int count=1;
        for (ArrayList\<Train\> alt : tl.getAllTrains()) {
            for (Train train : alt)
            {
                System.out.print("The Coordinate for the Train on "+train.getLine().getName()+" in time"+count+": ");
                train.getCoordinate().printCoor();
                System.out.println();
            }
            count++;
        }
    */
       // ShowGui sg=new ShowGui();
        //sg.Show(stationList, lineList,tl.getAllTrains());
        
        
        
       
        >>
        ;

//==PARAMETERS
formal_params returns [String str]
@init {$str = "";}:
        ^(FORMALPARAM (f=formal_param{
    if(listbool == true)
     { $str+= $f.str;
        listbool = false;
     }
    else
     {  $str+=", ";
        $str+=$f.str;}
    })*) 
        ;

formal_param returns [String str]: 
        ^(PRIMITIVE_TYPE ID)  {$str = "int " + $ID.text;}
        |^('Station' ID)  {$str = "Station " + $ID.text;}
        |^('Line' ID)  {$str = "Line " + $ID.text;}
        |^('Population' ID) {$str = "Population " + $ID.text;}  
        |^('Time' ID)  {$str = "int[] " + $ID.text;}
        |^('String' ID) {$str = "String " + $ID.text;}
        ; 

params returns [List params, String time]:
       // ^(PARAM (ID | NUM |('+'|'-'|'*'|'/'|'^')? INTEGER)* )
       ^(PARAM t=ID l+=(ID| NUM | INTEGER)* specialparam?) {$time = $t.text; $params=$l;}
        ;

    //    -> ^(PARAM  $a* $b* $a* )
        

specialparam :
        ^(SPEC ('+'|'-'|'*'|'/'|'^') INTEGER)
        ;
