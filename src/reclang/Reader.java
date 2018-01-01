package reclang;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;

import reclang.parser.*;
import reclang.AST.*;

public class Reader {
	Program read() throws IOException {
		String programText = readNextProgram();
		return parse(programText);
	}

	Program parse(String programText) {
		RecLangLexer l = new RecLangLexer(new org.antlr.v4.runtime.ANTLRInputStream(programText));
		RecLangParser p = new RecLangParser(new org.antlr.v4.runtime.CommonTokenStream(l));
		Program program = p.program().ast;
		return program;
	}
	
	static String readFile(String fileName) throws IOException {
		try {
			try (BufferedReader br = new BufferedReader(
					new FileReader(fileName))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				return sb.toString();
			}
		} catch (IOException e) {
			System.out.println("Could not open file " + fileName);
			System.exit(-1); // These are fatal errors
		}
		return "";
	}
	
	private String readNextProgram() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("$ ");
		String programText = br.readLine();
		return runFile(programText);
	}
	
	protected String getProgramDirectory() { return "build"+File.separator+"reclang"+File.separator+"examples"+File.separator; }
	private String runFile(String programText) throws IOException {
		if(programText.startsWith("run ")){
			programText = readFile(getProgramDirectory() + programText.substring(4));
		}
		return programText; 
	}
}
