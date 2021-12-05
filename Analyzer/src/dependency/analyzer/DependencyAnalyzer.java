package dependency.analyzer;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class DependencyAnalyzer {
	
	private List<MethodDeclaration> methodDeclarations;
	
	public DependencyAnalyzer(List<MethodDeclaration> methodDeclarations) {
		this.methodDeclarations = methodDeclarations;
	}
	
	
	 public void detectDependencies() {
		 
	 }

}
