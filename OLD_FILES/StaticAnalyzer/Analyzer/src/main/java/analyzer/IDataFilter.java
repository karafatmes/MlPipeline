package analyzer;

/** Define in this interface method that will be used in parser to clear useless information  ***/
public interface IDataFilter {
	
	public boolean isImportOrPackageStatement(String line);
	
	public boolean isEmptyStatement(String line);
	
	public boolean isClassStatement(String line);
	
	public boolean isCommentStatement(String line);
	
	public boolean isFinishBlockStatement(String line);
	
	public boolean isReturnStatement(String line);
	
	public boolean isPrintStatement(String line);
	
	public boolean isMethodDefinitionStatement(String line);
	
}
