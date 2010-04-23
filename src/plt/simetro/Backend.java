package plt.simetro;

import java.io.IOException;


import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Backend {

		public static void main(String[] args) throws RecognitionException, IOException {
			
			//String s  = "Population A{(A,10) (B, 23) (C, 19)}";
			//CharStream charStream = new ANTLRStringStream(s);
			
			//String file = new String("./Testing/Basic_test.sim");
			//String file = new String("./Testing/Conditional_test.sim");
			String file = new String("./Testing/Loop_test.sim");
			//String file = new String("./Testing/Object_test.sim");
			//String file = new String("./Testing/smalltown.sim");
			
			CharStream charStream = new ANTLRFileStream(file);
			simgramLexer lexer = new simgramLexer(charStream );

			TokenStream tokenStream = new CommonTokenStream(lexer);
			simgramParser parser = new simgramParser(tokenStream );
			
			simgramParser.program_return r = parser.program();
			CommonTree t = (CommonTree)r.getTree();
			
			
			System.out.println(t.toStringTree());
			
			System.out.println("werd up!");
			

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
            nodes.setTokenStream(tokenStream);
           
            simwalk walker = new simwalk(nodes);
            
            walker.program();
           
            System.out.println("werd up!");
		}

	}

/*
	"Line t {" +
	"Stations(A);" +
	"Frequency(10);" +
	"Capacity(1);" +
	"Speed(3);" +
	"}"
*/
