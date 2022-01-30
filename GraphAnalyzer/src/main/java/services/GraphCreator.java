package services;

import java.util.ArrayList;
import java.util.List;

import entities.Column;
import entities.Graph;
import entities.NodeOfGraph;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class GraphCreator {
	
	private static Graph graph;
	
	public GraphCreator() {
		 graph = new Graph();
	}
	
	
	public void createEdgesOfGraph(ArrayList<StackPane> nodes, NodeOfGraph startEdgeNode, NodeOfGraph endEdgeNode, Pane pane) {
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
										buildSingleDirectionalLine(start, end, pane, true, false, weight, isFile);
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


	public static Graph getGraph() {
		return graph;
	}
	
	


}
