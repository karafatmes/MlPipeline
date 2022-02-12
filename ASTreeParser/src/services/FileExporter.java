package services;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.StringLiteral;

import pipeline.Pipeline;
import stages.Stage;

public class FileExporter {
	
	private List<Pipeline> pipelines;
	private String [] columnsOfFile;
	
	
	public FileExporter(List<Pipeline> pipelines, String[] columnsOfFile) {
		this.pipelines = pipelines;
		this.columnsOfFile = columnsOfFile;
	}
	
	
	public void exportStagesToExternalFile() {
		StringBuilder builder = new StringBuilder();
		builder.append("columnsOfFile:"+Arrays.toString(columnsOfFile)+ "\n");
		builder.append("-------\n");
		for(Pipeline pipeline : pipelines) {
				builder.append("------start of "+pipeline.getName() +"-----\n");
			int i=1;
			for (Stage stage : pipeline.getStages()) {
			
				builder.append("node"+i+":"+ " "+stage.getType().label+ "\n");
				builder.append("inputs for node"+i+":"+ stage.getInputCols() +"\n");
				builder.append("outputs for node"+i+":" +stage.getOutputCols()+ "\n");
				builder.append("-------\n");
				i++;
			}
				builder.append("------end of " +pipeline.getName()+"-----\n");
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
