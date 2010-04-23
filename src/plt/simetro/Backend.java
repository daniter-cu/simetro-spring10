package plt.simetro;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;

public class Backend {

		public static void main(String[] args) throws RecognitionException {
			
			String s  = "Population A{(A,10) (B, 23) (C, 19)}";
			CharStream charStream = new ANTLRStringStream(s);
			simgramLexer lexer = new simgramLexer(charStream );

			TokenStream tokenStream = new CommonTokenStream(lexer);
			simgramParser parser = new simgramParser(tokenStream );
			
			simgramParser.program_return r = parser.program();
			CommonTree t = (CommonTree)r.getTree();
			
			System.out.println(t.toStringTree());
			
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