package analyzer;

import java.util.ArrayList;
import java.util.List;

import stages.KeyType;
import stages.Stage;

public class AnalyzerOfStages {

	private List<Stage> stages;
	private List<String> clauses;
	private String[] namesOfStages;

	public AnalyzerOfStages(String[] namesOfStages, List<String> clauses) {
		stages = new ArrayList<Stage>();
		this.namesOfStages = namesOfStages;
		this.clauses = clauses;
	}
	
	public void analyzeStages() {
		for (String stage: namesOfStages) {
			String stageStatement = findStageStatementInMlpipeline(stage);
			analyzeStageStatement(stageStatement);
		}
	}
	
	public void analyzeStageStatement(String stageStatement) {
		String firstTermOfStageStatement =  stageStatement.split(" ")[0];
		int inputIndex = stageStatement.indexOf("setInputCol");
		int outputIndex = stageStatement.indexOf("setOutputCol");
		
		String contentOfInputCols = stageStatement.substring(inputIndex, outputIndex);
		String contentOfOutputCols = stageStatement.substring(outputIndex, stageStatement.length());
		Stage stage = new Stage();
		stage.setType(getTypeOfStage(firstTermOfStageStatement));
		stage.setInputCols(getInputCols(contentOfInputCols));
		stage.setOutputCols(getOutputCols(contentOfOutputCols));
		
		stages.add(stage);
	}

	public String findStageStatementInMlpipeline(String nameOfStage) {
		for (String clause : clauses) {
			if (clause.contains(nameOfStage)) {
				return clause;
			}
		}
		throw new IllegalStateException(" Pipeline is missing. Not normal situation");
	}
	
	public String[] getInputCols(String contentOfInputCols) {
		String []inputcols;
		if(contentOfInputCols.contains("{") && contentOfInputCols.contains("}")) {
			//statement contains more than one input columns
			String inputColumns = contentOfInputCols.substring(contentOfInputCols.indexOf("{") + 1);
			// between brackets are the stages of pipeline
			String contentBetweenBrackets = inputColumns.substring(0, inputColumns.indexOf("}"));
			inputcols = contentBetweenBrackets.split(",");
		}
		else {
			String inputColumns = contentOfInputCols.substring(contentOfInputCols.indexOf("(") + 1);
			// between brackets are the stages of pipeline
			String contentBetweenParenthesis = inputColumns.substring(0, inputColumns.indexOf(")"));
			inputcols = contentBetweenParenthesis.split(",");
		}
		return inputcols;
	}
	
	public String[] getOutputCols(String contentOfOutputCols) {
		String []outputcols;
		if(contentOfOutputCols.contains("{") && contentOfOutputCols.contains("}")) {
			//statement contains more than one output columns
			String outputColumns = contentOfOutputCols.substring(contentOfOutputCols.indexOf("{") + 1);
			// between brackets are the stages of pipeline
			String contentBetweenBrackets = outputColumns.substring(0, outputColumns.indexOf("}"));
			outputcols = contentBetweenBrackets.split(",");
		}
		else {
			String outputColumns = contentOfOutputCols.substring(contentOfOutputCols.indexOf("(") + 1);
			// between brackets are the stages of pipeline
			String contentBetweenParenthesis = outputColumns.substring(0, outputColumns.indexOf(")"));
			outputcols = contentBetweenParenthesis.split(",");
		}
		return outputcols;
	}
	
	
	 public KeyType getTypeOfStage(String firstTermOfStageStatement) {
		 if( firstTermOfStageStatement.equals(KeyType.LogisticRegression.name())) {
			 return KeyType.LogisticRegression;
		 }
		 else if( firstTermOfStageStatement.equals(KeyType.MinMaxScaler.name())) {
			 return KeyType.MinMaxScaler;
		 }
		 else if( firstTermOfStageStatement.equals(KeyType.OneHotEncoder.name())) {
			 return KeyType.OneHotEncoder;
		 }
		 else if( firstTermOfStageStatement.equals(KeyType.StandardScaler.name())) {
			 return KeyType.StandardScaler;
		 }
		 else if( firstTermOfStageStatement.equals(KeyType.StringIndexer.name())) {
			 return KeyType.StringIndexer;
		 }
		 else if( firstTermOfStageStatement.equals(KeyType.VectorAssembler.name())) {
			 return KeyType.VectorAssembler;
		 }
		 else {
			 throw new IllegalStateException(" Not such type of Stage "+ firstTermOfStageStatement);
		 }
	 }

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public List<String> getClauses() {
		return clauses;
	}

	public void setClauses(List<String> clauses) {
		this.clauses = clauses;
	}

	public String[] getNamesOfStages() {
		return namesOfStages;
	}

	public void setNamesOfStages(String[] namesOfStages) {
		this.namesOfStages = namesOfStages;
	}
	 
	 

}
