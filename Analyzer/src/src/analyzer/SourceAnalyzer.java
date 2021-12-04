package src.analyzer;

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
	
	public SourceAnalyzer(IJavaProject javaproject) {
		this.javaProject = javaproject;
	}

	public List<IPackageFragment> getSrcPackagesOfProject() {

		List<IPackageFragment> sourcePackages = new ArrayList<IPackageFragment>();
		try {
			IPackageFragment[] packages = javaProject.getPackageFragments();

			for (IPackageFragment sourcepackage : packages) {

				// Package fragments include all packages in the
				// classpath
				// We will only look at the package from the source
				// folder
				// K_BINARY would include also included JARS, e.g.
				// rt.jar

				if (sourcepackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
					System.out.println("package " + sourcepackage.getElementName());
					printICompilationUnitInfo(sourcepackage);
					sourcePackages.add(sourcepackage);

				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourcePackages;
	}
	
	private void printICompilationUnitInfo(IPackageFragment mypackage) throws JavaModelException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			printCompilationUnitDetails(unit);

		}
	}

	private void printCompilationUnitDetails(ICompilationUnit unit) throws JavaModelException {
		System.out.println("Source file " + unit.getElementName());
		Document doc = new Document(unit.getSource());
		getContentOfUnit(unit);
		System.out.println("Has number of lines: " + doc.getNumberOfLines());
		printIMethods(unit);
	}

	private void printIMethods(ICompilationUnit unit) throws JavaModelException {

		IType[] allTypes = unit.getAllTypes();
		for (IType type : allTypes) {
			printIMethodDetails(type);
		}
	}

	private void printIMethodDetails(IType type) throws JavaModelException {
		IMethod[] methods = type.getMethods();
		for (IMethod method : methods) {

			System.out.println("Method name " + method.getElementName());
			System.out.println("Signature " + method.getSignature());
			System.out.println("Return Type " + method.getReturnType());

		}
	}

	private void getContentOfUnit(ICompilationUnit unit) {
		try {
			IJavaElement[] elements = unit.getChildren();
			CompilationUnit parse = parse(unit);
			MethodVisitor visitor = new MethodVisitor();
			parse.accept(visitor);
			List<MethodDeclaration> methodDeclarations = MethodDeclarationFinder.perform(parse);

			for (MethodDeclaration method : methodDeclarations) {
				System.out.print("Method elements: " + method.getBody().statements());
			}

			System.out.println("okkk");
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}

	public static List<MethodDeclaration> perform(ASTNode node) {
		MethodDeclarationFinder finder = new MethodDeclarationFinder();
		node.accept(finder);
		return finder.getMethods();
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
