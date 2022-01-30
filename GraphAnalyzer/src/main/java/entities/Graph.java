package entities;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private List<NodeOfGraph> nodes;
	private List<Edge> edges;
	
	
	public Graph() {
		this.nodes = new ArrayList<NodeOfGraph>();
		this.edges = new ArrayList<Edge>();
	}


	public List<NodeOfGraph> getNodes() {
		return nodes;
	}


	public void setNodes(List<NodeOfGraph> nodes) {
		this.nodes = nodes;
	}


	public List<Edge> getEdges() {
		return edges;
	}


	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	
	public void addEdge(int startIndex, int endIndex, String weight) {
		this.edges.add(new Edge(startIndex, endIndex, weight));
	}

}
