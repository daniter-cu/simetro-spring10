package plt.simetro;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class Backend {

		public static void main(String[] args) throws RecognitionException {
			
			String s  = "String s = \"yo \\\"what up\";";
			CharStream charStream = new ANTLRStringStream(s);
			simgramLexer lexer = new simgramLexer(charStream );

			TokenStream tokenStream = new CommonTokenStream(lexer);
			simgramParser parser = new simgramParser(tokenStream );
			parser.string();
			System.out.println("werd up!");
		}

	}
