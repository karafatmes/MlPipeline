package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//import org.jgrapht.Graph;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.SimpleGraph;

import stages.MlLibType;
import stages.Stage;

public class GraphFactory {
	

	public GraphFactory(List<Stage> stages) {
		buildGraph(stages);
	}
	
	
	private void buildGraph(List<Stage> stages) {
		// Object of graph is created.
        Graph<Stage> g = new Graph<Stage>();
		for( Stage stage : stages) {
			g.addVertex(stage);
			if (stages.indexOf(stage) == stages.size()-1) {
				// for the last Vertex do not add edge
				break;
			}
			g.addEdge(stages.get(stages.indexOf(stage)), (stages.get(stages.indexOf(stage)+1)), false);
		}
		System.out.println(g.toString());
	}


	

	
}
