package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import entities.Edge;
import entities.Graph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.FileReader;
import services.GraphCreator;

public class PanelApplication extends Application{
	
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
		pane.getChildren().addAll(addGraphToPanel(pane, graphCreator));
		
		// look if graph has cycle dependency.
		graphCreator.getNodesAfterTopologicalSorting();
		
	}
	
	public ArrayList<StackPane> addGraphToPanel(Pane pane, GraphCreator graphCreator) {
		Graph graph = graphCreator.getGraph();
		// add nodes of Graph
		ArrayList<StackPane> nodes = graphCreator.createNodesOfGraph();
		for (int i = 0; i < graph.getNodes().size(); i++) {
			
			for (int j = 0; j < graph.getNodes().size(); j++) {
				if(i == j) {
					// Dont look in the same graph if outputs contained in inputs of Node
					continue;
				}
				graphCreator.createEdgesOfGraph(nodes, graph.getNodes().get(i), graph.getNodes().get(j), pane);
			}

		}
	    
		return nodes;
	}
	
	
	
	

}
