package services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import pipeline.Pipeline;

import org.eclipse.jdt.core.dom.Statement;

import stages.MlLibType;
import stages.Stage;


/**
 * @author sakes
 *
 */
public class DependencyAnalyzer {
	
	private List<MethodDeclaration> methodDeclarations;
	private Map<String, List<SimpleName>> nameOfStagesInPipeline;
	private List<VariableDeclarationStatement> mlLibStatements;
	private List<Pipeline> pipelines;
	private int numberOfPipelines = 0;
	
	public DependencyAnalyzer(List<MethodDeclaration> methodDeclarations) {
		this.methodDeclarations = methodDeclarations;
		this.mlLibStatements = new ArrayList<VariableDeclarationStatement>();
		this.pipelines = new ArrayList<Pipeline>();
		this.nameOfStagesInPipeline = new HashMap<String, List<SimpleName>>();
	}
	
	
	 public void findMlLibDependencies(MethodDeclaration methodDeclaration) {
		 List<VariableDeclarationStatement> variableStatements= findMllibStatementsInMethod(methodDeclaration);
		 this.mlLibStatements.addAll(variableStatements);
	 }
	 
	 
	 public List<VariableDeclarationStatement> findMllibStatementsInMethod(MethodDeclaration methodDeclaration) {
		 List<VariableDeclarationStatement> variableStatements = new ArrayList<VariableDeclarationStatement>();
		 List<Statement> statements = methodDeclaration.getBody().statements();
		 
		 for (Statement statement : statements) {
			 if(! (statement instanceof VariableDeclarationStatement)) {
				 // keep only VariableDeclarationStatement
				 continue;
			 }
			 
			 VariableDeclarationStatement var = (VariableDeclarationStatement)statement;
			 if( var.getType()!=null && ! MlLibType.isMlLibType(var.getType().toString())) {
				 // ignore not MlPipeline statements 
				 continue;
			 }
			 
			 if( var.getType()!=null && var.getType().toString().equals(MlLibType.Pipeline.label)) {
				 // statement Pipeline contains stages.
				 Pipeline pipeline = new Pipeline();
				 pipeline.setName("pipeline"+numberOfPipelines);
				 pipelines.add(pipeline);
				 numberOfPipelines++;
				 this.nameOfStagesInPipeline.put(pipeline.getName() ,findStagesOfPipeline(var));
			 }
			 
			 variableStatements.add(var);
		 }
		 return variableStatements;
	 }
		 
		 
	public void analyzeStagesOfPipeline(VariableDeclarationStatement statement, List<SimpleName> stages, String pipelineName)	 {
		List<VariableDeclarationFragment> fragments = statement.fragments();
		VariableDeclarationFragment fragment = fragments.get(0);
		
		 if (fragment!=null && !isMlPipelineStage(fragment.getName().toString(), pipelineName)) {
				// ignore statement if it is not MlPipeline Stage
				return;
			}
		 
		 Stage stage = new Stage();
		 stage.setName(fragment.getName().toString());
		 stage.setType(getMlLibType(statement.getType().toString()));
		 
		 List<String> inputcols = new ArrayList<String>();
		 List<String> outputcols = new ArrayList<String>();
		 
		if(fragment.getInitializer()!=null && fragment.getInitializer() instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) fragment.getInitializer();
			 
			if (!stage.getType().label.equals(MlLibType.LogisticRegression.label)) {
				
				stage.setInputCols(findInputColsInStatement(methodInvocation));
				stage.setOutputCols(findsOutputColsInStatement(methodInvocation));
			}
			else if (stage.getType().label.equals(MlLibType.LogisticRegression.label)) {
				// in case of LogisticRegression we have setFeatures and setLabel as input and output method.
				stage.setInputCols(findFeatureColsInStatement(methodInvocation));
				stage.setOutputCols(findLabelColsInStatement(methodInvocation));
			}
			 	 
		}
		for(Pipeline pipeline : this.getPipelines()) {
			if(pipeline.getName().equals(pipelineName)) {
				pipeline.getStages().add(stage);
			}
		}
		System.out.println(" stage is   " + stage.toString());
	}
	
	private List<? extends String> findInputColsInStatement(MethodInvocation methodInvocation)  {
		// this method used to find setInputCol/s inside statement.
		// return inputcols.
		List<? extends String> inputCols = null;
		while(!(methodInvocation.getName().getIdentifier().equals("setInputCol") ||  
				methodInvocation.getName().getIdentifier().equals("setInputCols"))) {
			
			if(methodInvocation.getExpression() instanceof ClassInstanceCreation) {
				// when go to last sentence get out of loop
				break;
			}
			
			methodInvocation = (MethodInvocation)methodInvocation.getExpression();
		}
		 List<ArrayCreation> array = methodInvocation.arguments();
		 if(array!=null && !array.isEmpty() && array.get(0) instanceof ArrayCreation)  {
			//many arguments inside method
			ArrayCreation  arrayElement = array.get(0);
			ArrayInitializer arrayInitilizer = arrayElement.getInitializer();
			inputCols =  arrayInitilizer.expressions();
		}
		else {
			// only one argument inside method.
			inputCols = methodInvocation.arguments();
		}
		return inputCols;
	}
	
	private List<? extends String> findsOutputColsInStatement(MethodInvocation methodInvocation) {
		// this method used to find setOutputCol/s inside statement.
		// return outputcols.
		List<? extends String> outputCols = null;
		while(!(methodInvocation.getName().getIdentifier().equals("setOutputCol") ||  
				methodInvocation.getName().getIdentifier().equals("setOutputCols"))) {
			
			if(methodInvocation.getExpression() instanceof ClassInstanceCreation) {
				// when go to last sentence get out of loop
				break;
			}
			
			methodInvocation = (MethodInvocation)methodInvocation.getExpression();
		}
		List<ArrayCreation> array = methodInvocation.arguments();
		if(array!=null && !array.isEmpty() && array.get(0) instanceof ArrayCreation)  {
			//many arguments inside method
			ArrayCreation  arrayElement = array.get(0);
			ArrayInitializer arrayInitilizer = arrayElement.getInitializer();
			outputCols =  arrayInitilizer.expressions();
		}
		else {
			// only one argument inside method.
			outputCols = methodInvocation.arguments();
		}
		return outputCols;
	}
	
	
	private List<? extends String> findFeatureColsInStatement(MethodInvocation methodInvocation) {
		// this method used to find setFeatureCol/s inside statement.
		// return featurecols.
		List<? extends String> featureCols = null;
		while(!(methodInvocation.getName().getIdentifier().equals("setFeaturesCol") ||  
				methodInvocation.getName().getIdentifier().equals("setFeaturesCols"))) {
			
			if(methodInvocation.getExpression() instanceof ClassInstanceCreation) {
				// when go to last sentence get out of loop
				break;
			}
			
			methodInvocation = (MethodInvocation)methodInvocation.getExpression();
		}
		List<ArrayCreation> array = methodInvocation.arguments();
		if(array!=null && !array.isEmpty() && array.get(0) instanceof ArrayCreation)  {
			//many arguments inside method
			ArrayCreation  arrayElement = array.get(0);
			ArrayInitializer arrayInitilizer = arrayElement.getInitializer();
			featureCols =  arrayInitilizer.expressions();
		}
		else {
			// only one argument inside method.
			featureCols = methodInvocation.arguments();
		}
		return featureCols;
	} 
	
	private List<? extends String> findLabelColsInStatement(MethodInvocation methodInvocation) {
		// this method used to find setLabelCol/s inside statement.
		// return labelcols.
		List<? extends String> laeblCols = null;
		while(!(methodInvocation.getName().getIdentifier().equals("setLabelCol") ||  
				methodInvocation.getName().getIdentifier().equals("setLabelCols"))) {
			
			if(methodInvocation.getExpression() instanceof ClassInstanceCreation) {
				// when go to last sentence get out of loop
				break;
			}
			
			methodInvocation = (MethodInvocation)methodInvocation.getExpression();
		}
		List<ArrayCreation> array = methodInvocation.arguments();
		if(array!=null && !array.isEmpty() && array.get(0) instanceof ArrayCreation)  {
			//many arguments inside method
			ArrayCreation  arrayElement = array.get(0);
			ArrayInitializer arrayInitilizer = arrayElement.getInitializer();
			laeblCols =  arrayInitilizer.expressions();
		}
		else {
			// only one argument inside method.
			laeblCols = methodInvocation.arguments();
		}
		return laeblCols;
	} 
	
	
	
	private List<SimpleName> findStagesOfPipeline(VariableDeclarationStatement statement) {
		List<VariableDeclarationFragment> fragments = statement.fragments();
		VariableDeclarationFragment fragment = fragments.get(0);
		
		if(fragment!=null && fragment.getInitializer()!=null && fragment.getInitializer() instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) fragment.getInitializer();
			List<ArrayCreation> array = methodInvocation.arguments();
			if(array!=null && !array.isEmpty())  {
				ArrayCreation arrayElement = array.get(0);
				ArrayInitializer methodInvocationInside = arrayElement.getInitializer();
				List<SimpleName> arguments = methodInvocationInside.expressions();
				return arguments;
			}
			
		}
		return null;
	}
	
	
	private  boolean isMlPipelineStage(String type, String pipelineName) {
	    for (SimpleName stage : this.getNameOfStagesInPipeline().get(pipelineName)) {
	        if (stage.getIdentifier().equals(type)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private MlLibType getMlLibType( String type) {
		return MlLibType.valueOf(type);
	}
		
		
	 
	 private static Block parse(MethodDeclaration methodDeclaration) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_STATEMENTS);
		parser.setSource(methodDeclaration.getBody().statements().toString().toCharArray());
		parser.setResolveBindings(true);
		return  (Block)parser.createAST(null); // parse
		}


	public Map<String, List<SimpleName>> getNameOfStagesInPipeline() {
		return nameOfStagesInPipeline;
	}


	public void setNameOfStagesInPipeline(Map<String, List<SimpleName>> nameOfStagesInPipeline) {
		this.nameOfStagesInPipeline = nameOfStagesInPipeline;
	}


	public List<VariableDeclarationStatement> getMlLibStatements() {
		return mlLibStatements;
	}


	public void setMlLibStatements(List<VariableDeclarationStatement> mlLibStatements) {
		this.mlLibStatements = mlLibStatements;
	}
	

	public List<Pipeline> getPipelines() {
		return pipelines;
	}


	public void setPipelines(List<Pipeline> pipelines) {
		this.pipelines = pipelines;
	}




	public static final class VariableDeclarationExpressionFinder extends ASTVisitor {
			private final List<VariableDeclarationExpression> expressions = new ArrayList<>();

			public static List<VariableDeclarationExpression> perform(ASTNode node) {
				VariableDeclarationExpressionFinder finder = new VariableDeclarationExpressionFinder();
				node.accept(finder);
				return finder.getVariableExpessions();
			}

			@Override
			public boolean visit(final VariableDeclarationExpression expression) {
				expressions.add(expression);
				return super.visit(expression);
			}

			/**
			 * @return an immutable list view of the methods discovered by this visitor
			 */
			public List<VariableDeclarationExpression> getVariableExpessions() {
				return Collections.unmodifiableList(expressions);
			}
		}

}
