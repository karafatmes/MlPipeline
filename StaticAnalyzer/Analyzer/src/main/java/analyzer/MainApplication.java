package analyzer;

import java.util.Scanner;

public class MainApplication {

	public static void main(String[] args) {

		printWelcomeMessage();
		int choice = readChoice();
		String path = getPathBasedOnChoice(choice);
		
		Parser parser = new Parser(path);
		parser.parseFile();
		
		DetectorOfStages detector = new DetectorOfStages(parser.getClauses());
		String pipelineStatement = detector.recognizePipelineStatement();
		detector.getNamesOfStagesContainedInPipelineStatement(pipelineStatement);
		
		AnalyzerOfStages analyzer = new AnalyzerOfStages(detector.getNamesOfStages(), parser.getClauses());
		analyzer.analyzeStages();
		
		printFinishMessage();
		
	}

	public static String buildWelcomeMessage() {
		// this method build the welcome message when the user run the application
		StringBuilder welcomeMessage = new StringBuilder();

		welcomeMessage.append("----- Start parsing source file of ml pipeline ----------");
		welcomeMessage.append("--------------------------------------------------- \n");
		welcomeMessage.append("--------Choose one of the following  pipelines to analyze -----\n");
		welcomeMessage.append(" 1) OneHotEncoder Pipeline \n");
		welcomeMessage.append(" 2) Regression Pipeline \n");
		welcomeMessage.append(" 3) Standard Scaler Pipeline \n");
		welcomeMessage.append(" 4) Mixed Scaler Pipeline \n");

		return welcomeMessage.toString();
	}
	
	public static String buildFinishMessage() {
		// this method build the welcome message when the user run the application
		StringBuilder finishMessage = new StringBuilder();
		finishMessage.append("\n----------------------------------------------------\n");
		finishMessage.append("----- Finish of static analysis of src code ----------\n");
		finishMessage.append("----- ------------------------------------- ----------\n");

		return finishMessage.toString();
	}
	
	
	public static void printFinishMessage() {
		System.out.println(buildFinishMessage());
	}

	public static void printWelcomeMessage() {
		System.out.println(buildWelcomeMessage());
	}

	public static int readChoice() {
		// this method read choice user given from keyboard
		Scanner keyboard = new Scanner(System.in);
		int choice = keyboard.nextInt();
		keyboard.close();
		return choice;
	}

	public static String getPathBasedOnChoice(int choice) {
		switch (choice) {

		case 1:
			// path of one hot encoder pipeline
			return "../../ML_Pip_IN_JAVA/OneHotEncoderPipeline/src/main/java/pipeline/PipelineOneHotCodeEncoder.java";

		case 2:
			// path of regression pipeline
			return "../../ML_Pip_IN_JAVA/RegressionPipeline/src/main/java/pipeline/PipelineWithRegression.java";

		case 3:
			// path of standard scaler pipeline
			return "../../ML_Pip_IN_JAVA/StandardScalerPipeline/src/main/java/pipeline/PipelineWithStandardScaler.java";

		case 4:
			// path of mixed scaler pipeline
			return "../../ML_Pip_IN_JAVA/MixScalerPipeline/src/main/java/pipeline/PipelineWithMixedScaler.java";

		default:
			throw new IllegalArgumentException("Wrong argument " + choice);

		}
	}

}
