package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Column;
import entities.Edge;
import entities.Graph;
import entities.NodeOfGraph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.FileReader;
import services.GraphCreator;

public class PanelApplication extends Application{
	
	private List<Edge> edgesOfGraph;
	private Map<String, List<Column>> inputs = new HashMap<String, List<Column>>();
	private Map<String, List<Column>> outputs= new HashMap<String, List<Column>>();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create window
		StackPane root = new StackPane();
		root.setPadding(new Insets(20));
		Pane pane = new Pane();
		root.getChildren().add(pane);
		Scene sc = new Scene(root, 600, 600);
		primaryStage.setScene(sc);
		primaryStage.show();
		
		GraphCreator graphCreator = new GraphCreator();
		String path = "/Users/sakes/Documents/Development/MlPipelineProjects/ExportFiles/stages.txt";
		FileReader reader = new FileReader(path, graphCreator.getGraph());
		reader.fillGraphWithInfoComingFromFile();
		
	
		addGraphToPanel(pane, graphCreator,false, reader.getPipelines());
		
		for( String pipelineName : reader.getPipelines()) {
			// exclude external source node0
			List<NodeOfGraph> nodesInPipeline = graphCreator.getGraph().getNodes().stream().filter(e -> !e.getName().contentEquals("node0") && e.getPipelineBelongs().equals(pipelineName)).collect(Collectors.toList());
			// Keep inputs, outputs 
			for(NodeOfGraph node : nodesInPipeline) {
				List<Column> inputCols = node.getInputs().stream().collect(Collectors.toList());
				List<Column> outputCols = node.getOutputs().stream().collect(Collectors.toList());
				inputs.put(node.getName()+pipelineName, inputCols);
				outputs.put(node.getName()+pipelineName, outputCols);
			}
		}
		
		// Apply Topological Sorting to get the right order of nodes in Graph
		List<NodeOfGraph> nodesAfterTopologicalSorting = graphCreator.getNodesAfterTopologicalSorting();
		
		
		for(int i=0; i<nodesAfterTopologicalSorting.size(); i++) {
			NodeOfGraph node = nodesAfterTopologicalSorting.get(i);
			// exclude external source node0
			List<NodeOfGraph> nodes = graphCreator.getGraph().getNodes().stream().filter(e -> (!e.getName().contentEquals("node0")) && e.getName().equals(node.getName()) &&
					e.getPipelineBelongs().equals(node.getPipelineBelongs())).collect(Collectors.toList());
			if(!nodes.isEmpty()) {
				node.setInputs(inputs.get(nodes.get(0).getName()+nodes.get(0).getPipelineBelongs()));
				node.setOutputs(outputs.get(nodes.get(0).getName()+nodes.get(0).getPipelineBelongs()));
			}
		}
		
		graphCreator.getGraph().getNodes().clear();
		graphCreator.getGraph().setNodes(nodesAfterTopologicalSorting);
		pane.getChildren().addAll(addGraphToPanel(pane,graphCreator,true, reader.getPipelines()));
		
	}
	
	public ArrayList<StackPane> addGraphToPanel(Pane pane, GraphCreator graphCreator, boolean designLines, List<String> pipelines) {
		Graph graph = graphCreator.getGraph();
		// add nodes of Graph
		ArrayList<StackPane> nodes = graphCreator.createNodesOfGraph();
		
		for( String pipelineName : pipelines) {
			// add edges for nodes between each pipeline.
			List<NodeOfGraph> nodesInPipeline = graph.getNodes().stream().filter(e ->  e.getPipelineBelongs().equals(pipelineName)).collect(Collectors.toList());
		
			for (int i = 0; i < nodesInPipeline.size(); i++) {
				for (int j = 0; j < nodesInPipeline.size(); j++) {
					if(i == j) {
						// Dont look in the same graph if outputs contained in inputs of Node
						continue;
					}
					graphCreator.createEdgesOfGraph(nodes, nodesInPipeline.get(i), nodesInPipeline.get(j), pane, designLines);
				}
			}
		}
		// add edges between external source and other nodes.
		for (NodeOfGraph node : graph.getNodes()) {
			NodeOfGraph nodeExternalSource = graph.getNodes().get(0);
			graphCreator.createEdgesOfGraph(nodes, nodeExternalSource, node, pane, designLines);
		}
		// take care that we have duplicate edges
		List<Edge> edges = graphCreator.getGraph().getEdges().stream().distinct().collect(Collectors.toList());
		graphCreator.getGraph().getEdges().clear();
		graphCreator.getGraph().setEdges(edges);
		edgesOfGraph = graphCreator.getGraph().getEdges();
		return nodes;
	}
	
	
	
	

}
