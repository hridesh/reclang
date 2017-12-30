package reclang;
import java.io.IOException;

import reclang.Env;
import reclang.Value;
import reclang.AST.Program;

/**
 * This main class implements the Read-Eval-Print-Loop of the interpreter with
 * the help of Reader, Evaluator, and Printer classes. 
 * 
 * @author hridesh
 *
 */
public class Interpreter {
	public static void main(String[] args) {
		System.out.println("Type a program to evaluate and press the enter key,\n" + 
				"e.g. ((lambda (av bv cv) (let ((a av) (b bv) (c cv) (d 279) (e 277)) (+ (* a b) (/ c (- d e))))) 3 100 84) \n" + 
				"or try (letrec ((fact (lambda (n) (if (= n 0) 1 (* n (fact (- n 1))))))) (fact 5)) \n" +
				"or try (letrec ((loop (lambda (x) (loop x)))) (loop 0)) \n" + 
				"Press Ctrl + C to exit.");
		Reader reader = new Reader();
		Evaluator eval = new Evaluator(reader);
		Printer printer = new Printer();
		REPL: while (true) { // Read-Eval-Print-Loop (also known as REPL)
			Program p = null;
			try {
				p = reader.read();
				if(p._e == null) continue REPL;
				Value val = eval.valueOf(p);
				printer.print(val);
			} catch (Env.LookupException e) {
				printer.print(e);
			} catch (IOException e) {
				System.out.println("Error reading input:" + e.getMessage());
			} catch (NullPointerException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
	}
}
