package analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.janino.Java.Primitive;

public class Parser implements IDataFilter {

	private String path;
	private List<String> clauses;

	public Parser(String path) {
		this.path = path;
		clauses = new ArrayList<String>();
	}

	public void parseFile() {

		// Passing the path to the file as a parameter
		FileReader fr;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));

			// Declaring a string variable
			String line;
			String val = "";
			boolean isCompleted = true;
			while ((line = reader.readLine()) != null) {
				String lineWithoutSpace = line.trim();
				// ignore import,package, codeblock,comment,return statements, and empty lines
				if (isImportOrPackageStatement(lineWithoutSpace) || isEmptyStatement(lineWithoutSpace)
						|| isClassStatement(lineWithoutSpace) || isCommentStatement(lineWithoutSpace)
						|| isFinishBlockStatement(lineWithoutSpace) || isReturnStatement(lineWithoutSpace)
						|| isPrintStatement(lineWithoutSpace)  || isMethodDefinitionStatement(lineWithoutSpace)) {
					continue;
				}
				
				if(!lineWithoutSpace.endsWith(";")) {
					val  =  val + lineWithoutSpace;
					continue;
				}
				lineWithoutSpace = (val=="") ? lineWithoutSpace :val+lineWithoutSpace ;
				
				val="";
				
				// print used for debugging
				System.out.println(lineWithoutSpace);
				// store clause.
				storeClause(lineWithoutSpace);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void storeClause(String clause) {
		clauses.add(clause);
	}
	
	
	public List<String> getClauses() {
		return clauses;
	}

	public void setClauses(List<String> clauses) {
		this.clauses = clauses;
	}

	public boolean isImportOrPackageStatement(String line) {
		if (line != null && !line.isEmpty() && line.startsWith("import") || line.startsWith("package")) {
			return true;
		}
		return false;
	}

	public boolean isEmptyStatement(String line) {
		if (line != null && line.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isClassStatement(String line) {
		if (line != null && !line.isEmpty() && line.contains("class")) {
			return true;
		}
		return false;
	}

	public boolean isCommentStatement(String line) {
		if (line != null && !line.isEmpty() && line.contains("//") || line.contains("/**") || line.contains("**/")) {
			return true;
		}
		return false;
	}

	public boolean isFinishBlockStatement(String line) {
		if (line != null && line.length() == 1 && line.contains("}")) {
			return true;
		}
		return false;
	}
	
	public boolean isStartBlockStatement(String line) {
		if (line != null && !line.isEmpty() && line.contains("{")) {
			return true;
		}
		return false;
	}

	public boolean isReturnStatement(String line) {
		if (line != null && !line.isEmpty() && line.startsWith("return")) {
			return true;
		}
		return false;
	}

	public boolean isPrintStatement(String line) {
		if (line != null && !line.isEmpty() && line.contains("print") || line.contains("show")) {
			return true;
		}
		return false;
	}

	public boolean isAccessModifierPresent(String line) {
		if (line != null && !line.isEmpty() && line.contains("public") || line.contains("package")
				|| line.contains("private") || line.contains("protected")) {
			return true;
		}
		return false;
	}

	public boolean isMethodDefinitionStatement(String line) {
		//TODO make better this filter
		if (areParenthesisPresent(line) && isAccessModifierPresent(line)  && isStartBlockStatement(line)) {
			return true;
		}
		return false;
	}

	public boolean areParenthesisPresent(String line) {
		if (line != null && !line.isEmpty() && line.contains("(") && line.contains(")")) {
			return true;
		}
		return false;
	}

	public boolean isReturnTypePresent(String line) {
		if (containsPrimitiveTypes(line)) {
			return true;
		}
		return false;
	}

	public boolean containsPrimitiveTypes(String line) {
		if (line != null && !line.isEmpty() && line.contains("void") || line.contains("boolean") || line.contains("int")
				|| line.contains("float") || line.contains("double")) {
			return true;
		}
		return false;
	}

}
