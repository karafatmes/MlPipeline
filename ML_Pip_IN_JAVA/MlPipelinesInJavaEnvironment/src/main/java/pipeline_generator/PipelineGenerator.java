package pipeline_generator;

import java.util.Scanner;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import pipelines.AbstractPipeline;

public class PipelineGenerator {

	public static void main(String[] args) {

		printWelcomeMessage();

		int choice = readChoice();

		PipelineFactory pipelineFactory = new PipelineFactory();

		AbstractPipeline pipeline = pipelineFactory.getPipeline(choice);
		
		PipelineModel model = pipeline.create_pipeline();
		
		Dataset<Row> updated = model.transform(pipeline.create_schema());
		
		updated.show();
		
		printFinishMessage();

	}

	public static String buildWelcomeMessage() {

		// this method build the welcome message when the user run the application
		StringBuilder welcomeMessage = new StringBuilder();
		welcomeMessage.append("--------Welcome to Ml Pipeline--------------------- \n");
		welcomeMessage.append("--------------------------------------------------- \n");
		welcomeMessage.append("--------Choose one of the following pipelines -----\n");
		welcomeMessage.append(" 1) Pipeline reading data from dataframe \n");
		welcomeMessage.append(" 3) Pipeline with Regression \n");
		welcomeMessage.append(" 4) Pipeline with Standard Scaler \n");
		welcomeMessage.append(" 5) Pipeline with Mixed Scaler \n");

		return welcomeMessage.toString();

	}

	public static String buildFinishMessage() {
		// this method build the finish message which inform user that ml pipeline has
		// finished
		StringBuilder finishMessage = new StringBuilder();
		finishMessage.append("------ Ml Pipeline has been executed -----------------\n");
		finishMessage.append("------------------------------------------------------\n");

		return finishMessage.toString();
	}

	public static void printWelcomeMessage() {
		System.out.println(buildWelcomeMessage());
	}

	public static void printFinishMessage() {
		System.out.println(buildFinishMessage());
	}

	public static int readChoice() {
		// this method read choice user given from keyboard
		Scanner keyboard = new Scanner(System.in);
		int choice = keyboard.nextInt();
		keyboard.close();
		return choice;
	}

}
