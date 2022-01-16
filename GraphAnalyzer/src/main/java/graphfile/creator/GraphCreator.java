package graphfile.creator;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;

public class GraphCreator {
	
	
	
	public static Multigraph createMultiGraph() {
		// create Graph
		return	 new Multigraph<Object, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}

}
