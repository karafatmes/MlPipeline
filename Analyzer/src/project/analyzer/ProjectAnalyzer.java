package project.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class ProjectAnalyzer {
	
	

	
	
	public static List<IProject> keepOnlyProjectsContainedJavaSourceCode(IProject[] projects) {
		List<IProject> projectsContainedOnlyJava = new ArrayList<IProject>(); 
		for (IProject project : projects) {
			try {
				if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
					IJavaProject javaProject = JavaCore.create(project);
					projectsContainedOnlyJava.add(project);
				} else {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectsContainedOnlyJava;
	}
	
	public static IProject getProjectForAnalysis(int choice, List<IProject> projects) {
		return projects.get(choice);
	}
	
	
	public static IJavaProject convertToIJavaProject(IProject project) {
		return JavaCore.create(project);
	}

}
