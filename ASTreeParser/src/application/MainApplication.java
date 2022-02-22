package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import pipeline.Pipeline;
import services.DependencyAnalyzer;
import services.FileExporter;
import services.FileHandler;
import services.ProjectAnalyzer;
import services.SourceAnalyzer;
import services.WorkspaceAnalyzer;

public class MainApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		printWelcomeMessage();
		
		IProject[] projects = WorkspaceAnalyzer.findProjectsInWorkspace();

		List<IProject> projectsWithJavaSourceCode = ProjectAnalyzer.keepOnlyProjectsContainedJavaSourceCode(projects);

		printAvailablJavaProjectsForAnalysis(projectsWithJavaSourceCode);

		// based on choice user choose project for analysis.
		int choice = readChoice();

		// start from here to measure time of execution of ASTParser
		long start = System.currentTimeMillis();
		IProject projectForAnalysis = ProjectAnalyzer.getProjectForAnalysis(choice, projectsWithJavaSourceCode);

		// Analyze file that contains the data
		FileHandler fileHandler = new FileHandler(projectForAnalysis);
		String[] columnsInFilefile = fileHandler.getColumnsIncludedInFile();

		IJavaProject javaProject = ProjectAnalyzer.convertToIJavaProject(projectForAnalysis);

		SourceAnalyzer sourceAnalyzer = new SourceAnalyzer(javaProject);

		for (ICompilationUnit unit : sourceAnalyzer.getCompilationUnits()) {
			List<MethodDeclaration> methodDeclarations = sourceAnalyzer.getMethodDeclarationsOfClass(unit);
			
			DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(methodDeclarations);
			for (MethodDeclaration decl : methodDeclarations) {          
				dependencyAnalyzer.findMllibStatementsInMethod(decl);
			}
		}

		for (Pipeline pipeline : DependencyAnalyzer.getPipelines()) {
			for(VariableDeclarationStatement statement : DependencyAnalyzer.getMlLibStatementsInPipeline().get(pipeline.getName())) {
				DependencyAnalyzer.analyzeStagesOfPipeline(statement, pipeline.getName());
			}
		}
	
		FileExporter exporter = new FileExporter(DependencyAnalyzer.getPipelines(), columnsInFilefile);
		exporter.exportStagesToExternalFile();
		
		long end = System.currentTimeMillis();
		long duration = (end - start);
		System.out.println(" duration is "+ duration);
		
		// Clear static content
		DependencyAnalyzer.getPipelines().clear();
		DependencyAnalyzer.getMlLibStatementsInPipeline().clear();
		DependencyAnalyzer.getNameOfStagesInPipeline().clear();
		DependencyAnalyzer.setNumberOfPipelines(0);
		
		printFinishMessage();

		return null;
	}

	public static String buildWelcomeMessage() {
		// this method build the welcome message when the user run the application
		StringBuilder welcomeMessage = new StringBuilder();

		welcomeMessage.append("----- Start of static analysis of src code ----------");
		welcomeMessage.append("--------------------------------------------------- \n");

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
		return choice;
	}

	public void printAvailablJavaProjectsForAnalysis(List<IProject> projects) {
		StringBuilder availableJavaProjectsMessage = new StringBuilder();
		availableJavaProjectsMessage.append("\n--------------------------------------------------------------\n");
		availableJavaProjectsMessage.append("--------Choose one of the following  pipelines to analyze ------\n");
		for (IProject project : projects) {
			availableJavaProjectsMessage
					.append("\n--------" + projects.indexOf(project) + "------" + project.getName() + "-------\n");
		}
		System.out.println(availableJavaProjectsMessage.toString());
	}

}
