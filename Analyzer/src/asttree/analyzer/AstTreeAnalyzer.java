package asttree.analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import dependency.analyzer.DependencyAnalyzer;
import file.handler.FileHandler;
import project.analyzer.ProjectAnalyzer;
import src.analyzer.SourceAnalyzer;
import workspace.analyzer.WorkspaceAnalyzer;

public class AstTreeAnalyzer extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		printWelcomeMessage();

		IProject[] projects = WorkspaceAnalyzer.findProjectsInWorkspace();

		List<IProject> projectsWithJavaSourceCode = ProjectAnalyzer.keepOnlyProjectsContainedJavaSourceCode(projects);

		printAvailablJavaProjectsForAnalysis(projectsWithJavaSourceCode);

		// based on choice user choose project for analysis.
		int choice = readChoice();

		IProject projectForAnalysis = ProjectAnalyzer.getProjectForAnalysis(choice, projectsWithJavaSourceCode);

		// Analyze file that contains the data
		FileHandler fileHandler = new FileHandler(projectForAnalysis);
		String[] columnsInFilefile = fileHandler.getColumnsIncludedInFile();

		IJavaProject javaProject = ProjectAnalyzer.convertToIJavaProject(projectForAnalysis);

		SourceAnalyzer sourceAnalyzer = new SourceAnalyzer(javaProject);
		// print classes of project included in source package.

		sourceAnalyzer.printClassesIncludedInSourcePackages();

		List<MethodDeclaration> allMethodDeclarations = new ArrayList<MethodDeclaration>();
		for (ICompilationUnit unit : sourceAnalyzer.getCompilationUnits()) {
			List<MethodDeclaration> methodDeclarations = sourceAnalyzer.getMethodDeclarationsOfClass(unit);
			allMethodDeclarations.addAll(methodDeclarations);
		}

		DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(allMethodDeclarations);
		for (MethodDeclaration decl : allMethodDeclarations) {
			dependencyAnalyzer.findMlLibDependencies(decl);
		}
		List<VariableDeclarationStatement> mlLibStatements = dependencyAnalyzer.getMlLibStatements();

		for (VariableDeclarationStatement statement : mlLibStatements) {
			
			dependencyAnalyzer.analyzeStagesOfPipeline(statement, dependencyAnalyzer.getNameOfStagesInPipeline());
			
		}
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
