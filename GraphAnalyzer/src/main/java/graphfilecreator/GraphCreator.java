package graphfilecreator;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class GraphCreator {
	
	public static Multigraph createMultiGraph() {
		// create Graph
		return	 new Multigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}

}
