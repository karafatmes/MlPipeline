package file.handler;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class FileHandler {
	
	
	private IProject projectFileBelongs;
	
	
	public FileHandler()  {
		
	}
	
	
	public FileHandler(IProject project) {
		this.projectFileBelongs = project;
	}


	public IProject getProjectFileBelongs() {
		return projectFileBelongs;
	}


	public void setProjectFileBelongs(IProject projectFileBelongs) {
		this.projectFileBelongs = projectFileBelongs;
	}
	
	
	public String[] getColumnsIncludedInFile() {
		String [] columns = null;
		File file = (File)projectFileBelongs.findMember("src/main/resources/file.txt");
		System.out.println(" File path is " + file.getRawLocation().toOSString());
		try {
			InputStream input = file.getContents();
			columns = getContentOfFile(input);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columns;
	}
	
	public String[] getContentOfFile(InputStream input) {
		char content= ' ';
		StringBuilder builder = new StringBuilder();
		do {
			try {
				content = (char)input.read();
				if(content!='\n') {
					builder.append(content);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		while ( content != '\n'); 
		
        String lineWithColumns = builder.toString();
        System.out.println(lineWithColumns);
        String[] columns = lineWithColumns.split(",");
        return columns;
	}
	

}
