package plt.simetro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import org.antlr.runtime.ANTLRFileStream;
import org.antlr.stringtemplate.*;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.DOTTreeGenerator;

import TreeVisualize.ASTFrame;



public class Backend {

		public static void main(String[] args) throws RecognitionException, IOException {
			
			//String s  = "Population A{(A,10) (B, 23) (C, 19)}";
			//CharStream charStream = new ANTLRStringStream(s);
			
			//String file = new String("./Testing/Basic_test.sim");
			//String file = new String("./Testing/Conditional_test.sim");
			//String file = new String("./Testing/Loop_test.sim");
			//String file = new String("./Testing/Object_test.sim");
			//String file = new String("./Testing/smalltown_file.sim");
			String file = new String("./Testing/smalltown.sim");
			
			//==PARSER/LEXER
			CharStream charStream = new ANTLRFileStream(file);
			simgramLexer lexer = new simgramLexer(charStream );

			//TokenStream tokenStream = new CommonTokenStream(lexer);
			TokenRewriteStream tokenStream = new TokenRewriteStream(lexer);
			simgramParser parser = new simgramParser(tokenStream );
			
			simgramParser.program_return r = parser.program();
			CommonTree t = (CommonTree)r.getTree();
			

			//Print AST
			System.out.println(t.toStringTree());
			
			System.out.println("--end AST--");
			
			
			//Visualize AST
            DOTTreeGenerator gen = new DOTTreeGenerator();
            StringTemplate st = gen.toDOT(t);
            //System.out.println(st);

		//	ASTFrame af = new ASTFrame("Tree", t);
		//	af.setVisible(true);
		//	af.setSize(200, 200);
			
			//==WALKER==
			CommonTreeNodeStream nodes = new CommonTreeNodeStream((CommonTree) t);
            nodes.setTokenStream(tokenStream);
           
            simwalk walker = new simwalk(nodes);
            
            
            walker.program();
            
            String print = walker.printThis();
        	//System.out.println(tokenStream.toString());
            
 
            //==FILE I/O
            String[] newfile2 = file.split("/");
            String classname = (newfile2[newfile2.length-1] );

            String mypackage = "package SIMBack;\r\r";
            
            String imports = "import java.util.ArrayList;\n" + 
            		"import java.util.Arrays;\n" + 
            		"import java.util.HashMap;\r\r\r";
            
            String header = "public class "+ classname.replaceAll(".sim", "") + " { \r\r";
            String main = "public static void main (String[] args)  {  \r\r\r\r";
            String globals = "static ArrayList<Station> stationList=new ArrayList<Station>();\r";
            globals += "static ArrayList<Line> lineList=new ArrayList<Line>();\r";
            globals += "static ArrayList<Population> populationList=new ArrayList<Population>();\r";
            globals += "static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();\r";
            globals += "static HashMap<String, Station> stationMap=new HashMap<String, Station>();\r";
            globals += "static ArrayList<Station> tempList_stations=new ArrayList<Station>();\r\r";
            
            BufferedWriter outputStream = new BufferedWriter( new FileWriter(file.replace(".sim", ".java") ));
            try {
            	outputStream.write( mypackage+imports+header + globals + main + tokenStream.toString() + print + " \r\r\r}" );
              }
              finally {
            	  outputStream.close();
              }
              
            
            System.out.println("--end Template--");
		
		

            
		} //end main


	} // end class

