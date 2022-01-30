package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import entities.Column;
import entities.Graph;
import entities.NodeOfGraph;

/**
 * @author sakes
 *
 */
public class FileReader {

	private static String path;
	private static Graph graph;

	public FileReader(String path, Graph graph) {
		this.graph = graph;
		this.path = path;
	}

	public static void fillGraphWithInfoComingFromFile() {

		readFileProducedByAstParser();

	}

	public static void readFileProducedByAstParser() {
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			NodeOfGraph node = null;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				if (data.startsWith("columnsOfFile")) {
					// this contains info about first Node of Graph -> File
					// columns of File
					// external File is regarded as node 0
					String[] outputColumnsOfNode0 = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");

					node = new NodeOfGraph("node0", "file");
					for (String value : outputColumnsOfNode0) {
						Column outputColumn = new Column(value);
						node.getOutputs().add(outputColumn);
					}

				} else if (data.startsWith("node")) {
					// start of node
					String nodeName = data.substring(data.indexOf("node"), data.indexOf(":"));
					String vertex = data.substring(data.indexOf(" ") + 1, data.length());

					node.setName(nodeName);
					node.setValue(vertex);

				} else if (data.startsWith("inputs")) {
					// inputs of node
					// fill the input arrays
					String[] inputColumnsOfNode = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");

					for (String value : inputColumnsOfNode) {
						Column inputColumn = new Column(value);
						node.getInputs().add(inputColumn);
					}

				} else if (data.startsWith("outputs")) {
					// outputs of node
					// fill the output arrays
					String[] outputColumnsOfNode = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");

					for (String value : outputColumnsOfNode) {
						Column outputColumn = new Column(value);
						node.getOutputs().add(outputColumn);
					}
				}

				else if (data.contains("-------")) {
					// end of node
					graph.getNodes().add(node);
					node = new NodeOfGraph();
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
