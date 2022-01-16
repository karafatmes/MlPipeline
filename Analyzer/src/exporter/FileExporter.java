package exporter;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.StringLiteral;

import stages.Stage;

public class FileExporter {
	
	private List<Stage> stages;
	private String [] columnsOfFile;
	
	
	public FileExporter(List<Stage> stages, String[] columnsOfFile) {
		this.stages = stages;
		this.columnsOfFile = columnsOfFile;
	}
	
	
	public void exportStagesToExternalFile() {
		StringBuilder builder = new StringBuilder();
		builder.append("columnsOfFile:"+Arrays.toString(columnsOfFile)+ "\n");
		int i=1;
		for (Stage stage : stages) {
		
			builder.append("node"+i+":"+ " "+stage.getType().label+ "\n");
			builder.append("inputs for node"+i+":"+ stage.getInputCols() +"\n");
			builder.append("outputs for node"+i+":" +stage.getOutputCols()+ "\n");
			builder.append("-------\n");
			i++;
		}
		System.out.println("current path is " + System.getProperty("user.dir"));
		String path = "/Users/sakes/Documents/Development/MlPipelineProjects/ExportFiles/stages.txt";
		
		try {
			FileWriter exportFile = new FileWriter(path);
			exportFile.write(builder.toString());
			exportFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
