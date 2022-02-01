package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.text.Document;

public class SourceAnalyzer {

	private IJavaProject javaProject;
	private List<IPackageFragment> sourcePackages;
	List<ICompilationUnit> compilationUnits;

	public SourceAnalyzer(IJavaProject javaproject) {
		this.javaProject = javaproject;
		sourcePackages = new ArrayList<IPackageFragment>();
		compilationUnits = new ArrayList<ICompilationUnit>();
		
		findSrcPackagesOfProject();
		findICompilationUnitsOfProject();
	}

	public IJavaProject getJavaProject() {
		return javaProject;
	}

	public void setJavaProject(IJavaProject javaProject) {
		this.javaProject = javaProject;
	}

	public List<IPackageFragment> getSourcePackages() {
		return sourcePackages;
	}

	public void setSourcePackages(List<IPackageFragment> sourcePackages) {
		this.sourcePackages = sourcePackages;
	}
	
	public List<ICompilationUnit> getCompilationUnits() {
		return compilationUnits;
	}

	public void setCompilationUnits(List<ICompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
	}


	private void findSrcPackagesOfProject() {

		try {
			IPackageFragment[] packages = javaProject.getPackageFragments();

			for (IPackageFragment sourcepackage : packages) {

				// Package fragments include all packages in the
				// classpath
				// We will only look at the package from the source
				// folder
				// K_BINARY would include also included JARS, e.g.
				// rt.jar
				// keep only packages that contains source code.
				if (sourcepackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
					sourcePackages.add(sourcepackage);
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findICompilationUnitsOfProject() {
		for (IPackageFragment sourcepackage : this.getSourcePackages()) {
			try {
				for (ICompilationUnit unit : sourcepackage.getCompilationUnits()) {
					compilationUnits.add(unit);
				}
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void printClassesIncludedInSourcePackages() {
		System.out.println(" ----- Included the following classes --------\n");
		for (ICompilationUnit unit : this.getCompilationUnits()) {
			System.out.println(" -----" + unit.getElementName()+"      --------\n");
			try {
				Document doc = new Document(unit.getSource());
				System.out.println("---- "+ unit.getElementName() +" has number of lines: " + doc.getNumberOfLines()+"-----\n");
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println(" ----------------------------------------------\n");
	}

	
	public List<MethodDeclaration> getMethodDeclarationsOfClass(ICompilationUnit unit) {
		
		List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();
		CompilationUnit parse = parse(unit);
		
		methodDeclarations = MethodDeclarationFinder.perform(parse);
		
		return methodDeclarations;
	}



	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}


	public static final class MethodDeclarationFinder extends ASTVisitor {
		private final List<MethodDeclaration> methods = new ArrayList<>();

		public static List<MethodDeclaration> perform(ASTNode node) {
			MethodDeclarationFinder finder = new MethodDeclarationFinder();
			node.accept(finder);
			return finder.getMethods();
		}

		@Override
		public boolean visit(final MethodDeclaration method) {
			methods.add(method);
			return super.visit(method);
		}

		/**
		 * @return an immutable list view of the methods discovered by this visitor
		 */
		public List<MethodDeclaration> getMethods() {
			return Collections.unmodifiableList(methods);
		}
	}

}
