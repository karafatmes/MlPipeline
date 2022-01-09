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
	
	private Graph<Stage> g;

	public GraphFactory(List<Stage> stages) {
		buildGraph(stages);
	}
	
	
	private void buildGraph(List<Stage> stages) {
		// Object of graph is created.
        g = new Graph<Stage>();
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


	public Graph<Stage> getGraph() {
		return g;
	}


	public void setGraph(Graph<Stage> g) {
		this.g = g;
	}


	public void lookInputsForEveryVertexInGraph() {
		for( Stage v : g.getMap().keySet()) {
			 List<? extends String> inputs = v.getInputCols();
			 List<String> outputsFromPreviousStages = null;
			 for(String input : inputs) {
				 if(isInputComingFromOtherStage(input,outputsFromPreviousStages)) {
					 // TODO keep only inputs comingFromFile
					 // these inputs are the dependencies
				 }
			 }
		}
	}
	
	public boolean isInputComingFromOtherStage(String input, List<String> outputsFromPreviousStages) {
		return (outputsFromPreviousStages.contains(input) ? true: false);
	}
	

	
}
