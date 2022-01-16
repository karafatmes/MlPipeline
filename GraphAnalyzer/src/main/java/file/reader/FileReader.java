package file.reader;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jgrapht.graph.Multigraph;

import graph.designer.GraphDesigner;
import graphfile.creator.GraphCreator;

public class FileReader {
	
	private static Multigraph multigraph;
	private static Map<String, String> vertices = new HashMap<String, String>();
	private static Map<String, String[]> inputs = new HashMap<String, String[]>();
	private static Map<String, String[]> outputs = new HashMap<String, String[]>();
	
	
	public static void main(String[] args) {
		// create graph 
		multigraph = GraphCreator.createMultiGraph();
		// add first vertex file
		multigraph.addVertex("file");
		vertices.put("node0", "file");
		//file has only outputcols
		// read file firstly
		readFileProducedByAstParser("/Users/sakes/Documents/Development/MlPipelineProjects/ExportFiles/stages.txt");
		
		addEdgesToGraph();
		
		File file = new File("./test.graphml");
		
		GraphDesigner.export(multigraph, file);
		
		GraphDesigner.designGraph(multigraph);
	}

	public static void readFileProducedByAstParser(String filepath) {
		try {
			File myObj = new File(filepath);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				if(data.startsWith("columnsOfFile")) {
					// columns of File
					// external File is regarded as node 0
					String []outputColumnsOfNode0 = data.substring(data.indexOf("[")+1,data.indexOf("]")).replace("\"", "").split(",");
					outputs.put("node0",outputColumnsOfNode0);
					
				}
				else if(data.startsWith("node")){
					// start of node
					String node = data.substring(data.indexOf("node"),data.indexOf(":"));
					String vertex = data.substring(data.indexOf(" ")+1, data.length());
					multigraph.addVertex(vertex);
					vertices.put(node,vertex);
				}
				else if(data.startsWith("inputs")) {
					// inputs of node
					// fill the input arrays
					String node = data.substring(data.indexOf("node"),data.indexOf(":"));
					String []inputColumnsOfNode = data.substring(data.indexOf("[")+1,data.indexOf("]")).replace("\"", "").split(",");
					inputs.put(node,inputColumnsOfNode);
					
				}
				else if(data.startsWith("outputs")) {
					//  outputs of node
					// fill the output arrays
					String node = data.substring(data.indexOf("node"),data.indexOf(":"));
					System.out.println("node is "+ node);
					String []outputColumnsOfNode = data.substring(data.indexOf("[")+1,data.indexOf("]")).replace("\"", "").split(",");
					
					outputs.put(node,outputColumnsOfNode);
					
				}
				
				else if(data.contains("-------")) {
				// end of node
				}
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	private static void addEdgesToGraph() {
	
		for(int i =0; i<vertices.size(); i++) {
			String keyNode = "node"+i;
			System.out.println("node is "+keyNode);
			String[] outputCols = outputs.get(keyNode);
			for( String outputCol : outputCols) {
				for (Map.Entry<String, String[]> inputNode : inputs.entrySet())
					if(isContainedInNodeAsInput(inputNode.getValue(),outputCol)) {
						multigraph.addEdge(vertices.get(keyNode), vertices.get(inputNode.getKey()), outputCol);
					}
			}
		}
	}
	
	private static boolean isContainedInNodeAsInput(String []inputCols, String outputCol) {
		return Arrays.stream(inputCols).anyMatch(outputCol::equals);
	}

}
