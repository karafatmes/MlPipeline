package analyzer;

import java.util.List;

import stages.KeyType;

public class DetectorOfStages {

	private List<String> clauses;
	private int numberOfStages;
	private String[] namesOfStages;
	
	public DetectorOfStages(List<String> clauses) {
		this.clauses = clauses;
	}
	
	public void detectNumberOfStages(String pipelineClause) {
		// inside of Pipeline Constructor contained the stages
		// so number of arguments in the constructor can give us the number of stages of Pipeline
		
	}
	
	public void getNamesOfStagesContainedInPipelineStatement(String pipelineClause) {
		String [] pipelineStatement = pipelineClause.split(KeyType.PipelineStage.name());
		// TODO use regular expression
		String pipelineStages = pipelineStatement[1].substring(pipelineStatement[1].indexOf("{") + 1);
		// between brackets are the stages of pipeline
		String contentBetweenBrackets = pipelineStages.substring(0, pipelineStages.indexOf("}"));
//		System.out.println(contentBetweenBrackets);
		namesOfStages = contentBetweenBrackets.split(",");
		numberOfStages = namesOfStages.length;
	}
	
	public String recognizePipelineStatement() {
		for (String clause : clauses) {
			String firstTerm = clause.split(" ")[0];
			if(firstTerm.equals(KeyType.Pipeline.name())) {
				return clause;
			}
		}
		throw new IllegalStateException(" Pipeline is missing. Not normal situation");
	}
	
	public void getStagesBasedOnTheirNames() {
		
	}

	public List<String> getClauses() {
		return clauses;
	}

	public void setClauses(List<String> clauses) {
		this.clauses = clauses;
	}

	public int getNumberOfStages() {
		return numberOfStages;
	}

	public void setNumberOfStages(int numberOfStages) {
		this.numberOfStages = numberOfStages;
	}

	public String[] getNamesOfStages() {
		return namesOfStages;
	}

	public void setNamesOfStages(String[] namesOfStages) {
		this.namesOfStages = namesOfStages;
	}

	

}
