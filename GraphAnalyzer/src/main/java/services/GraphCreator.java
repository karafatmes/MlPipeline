package services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.Column;
import entities.Edge;
import entities.Graph;
import entities.NodeOfGraph;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class GraphCreator  implements Cloneable  {
	
	private  Graph graph;
	
	public GraphCreator() {
		 graph = new Graph();
	}
	
	
	
	public void createEdgesOfGraph(ArrayList<StackPane> nodes, NodeOfGraph startEdgeNode, NodeOfGraph endEdgeNode, Pane pane, boolean designLines) {
		StackPane start = null;
		StackPane end = null;
		boolean isFile = false;
		String weight = "";
		for (Column outputCol : startEdgeNode.getOutputs()) {
			if (isContainedInNodeAsInput(endEdgeNode.getInputs(), outputCol.getValue())) {
				weight = weight + outputCol.getValue() + " ,";
			}
		}
		weight = removeComma(weight);
		if (!weight.equals("")) {
			// there is edge between nodes.
						for (Node n : nodes) {
							for (Node element : ((StackPane) n).getChildren()) {
								if (element instanceof Label) {
									String text = ((Label) element).getText();
									if (text.equals(startEdgeNode.getName() +" : "+ startEdgeNode.getValue())) {
										// node starts edge
										if( startEdgeNode.getName().equals("node0")) {
											isFile = true;
										}
										else {
											isFile = false;
										}
										start = (StackPane) n;
									} else if (text.equals(endEdgeNode.getName() + " : "+ endEdgeNode.getValue())) {
										// node ends edge
										end = (StackPane) n;
									}
									if (start != null & end != null) {
										if(designLines) {
											buildSingleDirectionalLine(start, end, pane, true, false, weight, isFile);
										}
										int startIndex = graph.getNodes().indexOf(startEdgeNode);
										int endIndex = graph.getNodes().indexOf(endEdgeNode);
										graph.addEdge(startIndex, endIndex, weight);
									}
								}
							}
						}
		}
	}
	
	
	public  ArrayList<StackPane> createNodesOfGraph() {
		ArrayList<StackPane> nodes = new ArrayList<StackPane>();
		for (NodeOfGraph node : graph.getNodes()) {

			StackPane dotA = UiElementsCreator.createDot("green", node.getName()+" : "+node.getValue());
			nodes.add(dotA);

		}
		return nodes;
	}
	

	
	private String removeComma(String weight) {
		if (weight != null && weight.length() > 0 && weight.charAt(weight.length() - 1) == ',') {
			weight = weight.substring(0, weight.length() - 1);
		}
		return weight;
	}
	
	private static boolean isContainedInNodeAsInput(List<Column> inputs, String outputCol) {
		return inputs.stream().anyMatch(input -> input.getValue().equals(outputCol));
	}
	
	

	private void buildSingleDirectionalLine(StackPane startDot, StackPane endDot, Pane parent, boolean hasEndArrow,
			boolean hasStartArrow, String dependency, boolean isFile) {
		Line line = UiElementsCreator.createLine(startDot, endDot, isFile);
		StackPane arrowAB = UiElementsCreator.createArrow(true, line, startDot, endDot);
		if (!hasEndArrow) {
			arrowAB.setOpacity(0);
		}
		StackPane arrowBA = UiElementsCreator.createArrow(false, line, startDot, endDot);
		if (!hasStartArrow) {
			arrowBA.setOpacity(0);
		}
		StackPane weightAB = UiElementsCreator.createWeight(line, dependency);
		parent.getChildren().addAll(line, weightAB, arrowBA, arrowAB);
	}


	public  Graph getGraph() {
		return graph;
	}
	
	
	
	
	public void setGraph(Graph graph) {
		this.graph = graph;
	}



	public List<NodeOfGraph> getNodesAfterTopologicalSorting() {
		List<NodeOfGraph> topologicalList = new ArrayList<NodeOfGraph>();
		
		List<NodeOfGraph> nodesWithNoIncomingEdges = graph.getNodes().stream().filter(node -> node.getInputs().isEmpty()).collect(Collectors.toList());
		
		while(!nodesWithNoIncomingEdges.isEmpty()) {
			NodeOfGraph node = nodesWithNoIncomingEdges.get(0);
			
			nodesWithNoIncomingEdges.remove(node);
			System.out.println(" node is "+ node.getName()+node.getValue());
			topologicalList.add(node);
			Edge edgeToRemove = null;
			System.out.println("edge size is "+ graph.getEdges().size());
			for (Edge e : graph.getEdges()) {
				if(e.getStartIndex()==graph.getNodes().indexOf(node)) {
					NodeOfGraph nodeM = graph.getNodes().get(e.getEndIndex());
					System.out.println("edge is "+ e.getWeight());
					String columnsToRemove[] = e.getWeight().replaceAll(" ","").split(",");
					for (String columnToRemove : columnsToRemove) {
						List<Column> columntToRemove = nodeM.getInputs().stream().filter(column -> column.getValue().equals(columnToRemove)).collect(Collectors.toList());
						if(!columntToRemove.isEmpty()) {
							
							nodeM.getInputs().remove(columntToRemove.get(0));
						}
					}
					edgeToRemove = e;
					
					if(nodeM.getInputs().isEmpty()) {
						nodesWithNoIncomingEdges.add(nodeM);
					}
				}
			}
			if(edgeToRemove!=null) {
				graph.getEdges().remove(edgeToRemove);
			}
			
		}
		if(graph.getEdges().size() >0) {
			throw new IllegalStateException(" Graph has cycle dependency");
		}
		
		
		return topologicalList;
	}


}
