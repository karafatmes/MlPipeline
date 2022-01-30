package file.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.jgrapht.graph.Multigraph;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import entities.GraphInfo;
import graphfilecreator.GraphCreator;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class FileReader extends Application {

	private static Multigraph multigraph;
	private static Map<String, String> vertices = new HashMap<String, String>();
	private static Map<String, String[]> inputs = new HashMap<String, String[]>();
	private static Map<String, String[]> outputs = new HashMap<String, String[]>();
	double sceneX, sceneY, layoutX, layoutY;

	public static void main(String[] args) {
		// create graph
		multigraph = GraphCreator.createMultiGraph();
		// add first vertex file
		multigraph.addVertex("file");
		vertices.put("node0", "file");
		// file has only outputcols
		// read file firstly
		readFileProducedByAstParser("/Users/sakes/Documents/Development/MlPipelineProjects/ExportFiles/stages.txt");

		launch(args);
	}

	public static void readFileProducedByAstParser(String filepath) {
		try {
			File myObj = new File(filepath);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				if (data.startsWith("columnsOfFile")) {
					// columns of File
					// external File is regarded as node 0
					String[] outputColumnsOfNode0 = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");
					outputs.put("node0", outputColumnsOfNode0);

				} else if (data.startsWith("node")) {
					// start of node
					String node = data.substring(data.indexOf("node"), data.indexOf(":"));
					String vertex = data.substring(data.indexOf(" ") + 1, data.length());
					multigraph.addVertex(vertex);
					vertices.put(node, vertex);
				} else if (data.startsWith("inputs")) {
					// inputs of node
					// fill the input arrays
					String node = data.substring(data.indexOf("node"), data.indexOf(":"));
					String[] inputColumnsOfNode = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");
					inputs.put(node, inputColumnsOfNode);

				} else if (data.startsWith("outputs")) {
					// outputs of node
					// fill the output arrays
					String node = data.substring(data.indexOf("node"), data.indexOf(":"));
					String[] outputColumnsOfNode = data.substring(data.indexOf("[") + 1, data.indexOf("]"))
							.replace("\"", "").replaceAll(" ", "").split(",");

					outputs.put(node, outputColumnsOfNode);

				}

				else if (data.contains("-------")) {
					// end of node
				}
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

//	private static void addEdgesToGraph(Pane pane) {
//		GraphMLExporter<Object, DefaultWeightedEdge> exporter = new GraphMLExporter<Object, DefaultWeightedEdge>();
//		for (int i = 0; i < vertices.size(); i++) {
//			String keyNode = "node" + i;
//			System.out.println("node is " + keyNode);
//			String[] outputCols = outputs.get(keyNode);
//			for (String outputCol : outputCols) {
//				for (Map.Entry<String, String[]> inputNode : inputs.entrySet())
//					if (isContainedInNodeAsInput(inputNode.getValue(), outputCol)) {
//						multigraph.addEdge(vertices.get(keyNode), vertices.get(inputNode.getKey()));
////						buildSingleDirectionalLine(getDot("green", "A"), getDot("green", "A"), pane, true, false);
//					}
//
//			}
//		}
//	}

	private static boolean isContainedInNodeAsInput(String[] inputCols, String outputCol) {
		return Arrays.stream(inputCols).anyMatch(outputCol::equals);
	}

	@Override
	public void start(Stage stage) {
		try {

			// create window
			StackPane root = new StackPane();
			root.setPadding(new Insets(20));
			Pane pane = new Pane();
			root.getChildren().add(pane);
			Scene sc = new Scene(root, 600, 600);
			stage.setScene(sc);
			stage.show();

			// keep info about graph
			ArrayList<GraphInfo> graphInfo = new ArrayList<GraphInfo>();
			// create vertices of Graph
			ArrayList<StackPane> nodes = createVerticesOfGraph();
			// create edges of Graph
			for (int i = 0; i < vertices.size(); i++) {
				String keyNode = "node" + i;
				String[] outputCols = outputs.get(keyNode);
				for (Map.Entry<String, String[]> inputNode : inputs.entrySet()) {
					createDifEdges(nodes, graphInfo, pane, outputCols, keyNode, inputNode);
				}
//				for (String outputCol : outputCols) {
//
//					for (Map.Entry<String, String[]> inputNode : inputs.entrySet()) {
//						if (isContainedInNodeAsInput(inputNode.getValue(), outputCol)) {
//							
//
//						}
//
//					}
//
//				}
			}
			pane.getChildren().addAll(nodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<StackPane> createVerticesOfGraph() {
		ArrayList<StackPane> list = new ArrayList<StackPane>();
		for (Map.Entry<String, String> vertex : vertices.entrySet()) {

			StackPane dotA = getDot("green", vertex.getValue());
			list.add(dotA);

		}
		return list;
	}

	private void createDifEdges(ArrayList<StackPane> nodes, ArrayList<GraphInfo> graphInfo, Pane pane,
			String[] outputCols, String keyNode, Map.Entry<String, String[]> inputNode) {
		StackPane start = null;
		StackPane end = null;
		boolean isFile = false;
		String weight = "";
		for (String outputCol : outputCols) {
			if (isContainedInNodeAsInput(inputNode.getValue(), outputCol)) {
				weight = weight + outputCol + " ,";
			}
		}
		weight = removeComma(weight);
		System.out.println("weight is " + weight);
		if (!weight.equals("")) {
			// there is edge between nodes.
			for (Node n : nodes) {
				for (Node element : ((StackPane) n).getChildren()) {
					if (element instanceof Label) {
						String text = ((Label) element).getText();
						if (text.equals(vertices.get(keyNode))) {
							if( keyNode.equals("node0")) {
								isFile = true;
							}
							else {
								isFile = false;
							}
							start = (StackPane) n;
						} else if (text.equals(vertices.get(inputNode.getKey()))) {

							end = (StackPane) n;
						}
						if (start != null & end != null) {
							System.out.println("edg weight is " + weight);
							buildSingleDirectionalLine(start, end, pane, true, false, weight, isFile);
						}
					}
				}
			}

		}
	}

	private String removeComma(String weight) {
		if (weight != null && weight.length() > 0 && weight.charAt(weight.length() - 1) == ',') {
			weight = weight.substring(0, weight.length() - 1);
		}
		return weight;
	}

	private StackPane getDot(String color, String text) {
		double radius = 80;
		double paneSize = 5 * radius;
		StackPane dotPane = new StackPane();
		Circle dot = new Circle();
		dot.setRadius(radius);
		dot.setStyle("-fx-fill:" + color + ";-fx-stroke-width:2px;-fx-stroke:black;");

		Label txt = new Label(text);
		txt.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");
		dotPane.getChildren().addAll(dot, txt);
		dotPane.setPrefSize(paneSize, paneSize);
		dotPane.setMaxSize(paneSize, paneSize);
		dotPane.setMinSize(paneSize, paneSize);
		dotPane.setOnMousePressed(e -> {
			sceneX = e.getSceneX();
			sceneY = e.getSceneY();
			layoutX = dotPane.getLayoutX();
			layoutY = dotPane.getLayoutY();
		});

		EventHandler<MouseEvent> dotOnMouseDraggedEventHandler = e -> {
			// Offset of drag
			double offsetX = e.getSceneX() - sceneX;
			double offsetY = e.getSceneY() - sceneY;

			// Taking parent bounds
			Bounds parentBounds = dotPane.getParent().getLayoutBounds();

			// Drag node bounds
			double currPaneLayoutX = dotPane.getLayoutX();
			double currPaneWidth = dotPane.getWidth();
			double currPaneLayoutY = dotPane.getLayoutY();
			double currPaneHeight = dotPane.getHeight();

			if ((currPaneLayoutX + offsetX < parentBounds.getWidth() - currPaneWidth)
					&& (currPaneLayoutX + offsetX > -1)) {
				// If the dragNode bounds is within the parent bounds, then you can set the
				// offset value.
				dotPane.setTranslateX(offsetX);
			} else if (currPaneLayoutX + offsetX < 0) {
				// If the sum of your offset and current layout position is negative, then you
				// ALWAYS update your translate to negative layout value
				// which makes the final layout position to 0 in mouse released event.
				dotPane.setTranslateX(-currPaneLayoutX);
			} else {
				// If your dragNode bounds are outside parent bounds,ALWAYS setting the
				// translate value that fits your node at end.
				dotPane.setTranslateX(parentBounds.getWidth() - currPaneLayoutX - currPaneWidth);
			}

			if ((currPaneLayoutY + offsetY < parentBounds.getHeight() - currPaneHeight)
					&& (currPaneLayoutY + offsetY > -1)) {
				dotPane.setTranslateY(offsetY);
			} else if (currPaneLayoutY + offsetY < 0) {
				dotPane.setTranslateY(-currPaneLayoutY);
			} else {
				dotPane.setTranslateY(parentBounds.getHeight() - currPaneLayoutY - currPaneHeight);
			}
		};
		dotPane.setOnMouseDragged(dotOnMouseDraggedEventHandler);
		dotPane.setOnMouseReleased(e -> {
			// Updating the new layout positions
			dotPane.setLayoutX(layoutX + dotPane.getTranslateX());
			dotPane.setLayoutY(layoutY + dotPane.getTranslateY());

			// Resetting the translate positions
			dotPane.setTranslateX(0);
			dotPane.setTranslateY(0);
		});
		return dotPane;
	}

	private void buildSingleDirectionalLine(StackPane startDot, StackPane endDot, Pane parent, boolean hasEndArrow,
			boolean hasStartArrow, String dependency, boolean isFile) {
		Line line = getLine(startDot, endDot, isFile);
		StackPane arrowAB = getArrow(true, line, startDot, endDot);
		if (!hasEndArrow) {
			arrowAB.setOpacity(0);
		}
		StackPane arrowBA = getArrow(false, line, startDot, endDot);
		if (!hasStartArrow) {
			arrowBA.setOpacity(0);
		}
		StackPane weightAB = getWeight(line, dependency);
		parent.getChildren().addAll(line, weightAB, arrowBA, arrowAB);
	}

	private Line getLine(StackPane startDot, StackPane endDot, boolean isFile) {
		Line line = new Line();
		line.setStroke(Color.BLUE);
		if(isFile) {
			line.setStroke(Color.RED);
		}
		line.setStrokeWidth(2);
		line.startXProperty().bind(
				startDot.layoutXProperty().add(startDot.translateXProperty()).add(startDot.widthProperty().divide(2)));
		line.startYProperty().bind(
				startDot.layoutYProperty().add(startDot.translateYProperty()).add(startDot.heightProperty().divide(2)));
		line.endXProperty()
				.bind(endDot.layoutXProperty().add(endDot.translateXProperty()).add(endDot.widthProperty().divide(2)));
		line.endYProperty()
				.bind(endDot.layoutYProperty().add(endDot.translateYProperty()).add(endDot.heightProperty().divide(2)));
		return line;
	}

	private StackPane getArrow(boolean toLineEnd, Line line, StackPane startDot, StackPane endDot) {
		double size = 12; // Arrow size
		StackPane arrow = new StackPane();
		arrow.setStyle(
				"-fx-background-color:#333333;-fx-border-width:1px;-fx-border-color:black;-fx-shape: \"M0,-4L4,0L0,4Z\"");//
		arrow.setPrefSize(size, size);
		arrow.setMaxSize(size, size);
		arrow.setMinSize(size, size);

		// Determining the arrow visibility unless there is enough space between dots.
		DoubleBinding xDiff = line.endXProperty().subtract(line.startXProperty());
		DoubleBinding yDiff = line.endYProperty().subtract(line.startYProperty());
		BooleanBinding visible = (xDiff.lessThanOrEqualTo(size).and(xDiff.greaterThanOrEqualTo(-size))
				.and(yDiff.greaterThanOrEqualTo(-size)).and(yDiff.lessThanOrEqualTo(size))).not();
		arrow.visibleProperty().bind(visible);

		// Determining the x point on the line which is at a certain distance.
		DoubleBinding tX = Bindings.createDoubleBinding(() -> {
			double xDiffSqu = (line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX());
			double yDiffSqu = (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY());
			double lineLength = Math.sqrt(xDiffSqu + yDiffSqu);
			double dt;
			if (toLineEnd) {
				// When determining the point towards end, the required distance is total length
				// minus (radius + arrow half width)
				dt = lineLength - (endDot.getWidth() / 2) - (arrow.getWidth() / 2);
			} else {
				// When determining the point towards start, the required distance is just
				// (radius + arrow half width)
				dt = (startDot.getWidth() / 2) + (arrow.getWidth() / 2);
			}

			double t = dt / lineLength;
			double dx = ((1 - t) * line.getStartX()) + (t * line.getEndX());
			return dx;
		}, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());

		// Determining the y point on the line which is at a certain distance.
		DoubleBinding tY = Bindings.createDoubleBinding(() -> {
			double xDiffSqu = (line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX());
			double yDiffSqu = (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY());
			double lineLength = Math.sqrt(xDiffSqu + yDiffSqu);
			double dt;
			if (toLineEnd) {
				dt = lineLength - (endDot.getHeight() / 2) - (arrow.getHeight() / 2);
			} else {
				dt = (startDot.getHeight() / 2) + (arrow.getHeight() / 2);
			}
			double t = dt / lineLength;
			double dy = ((1 - t) * line.getStartY()) + (t * line.getEndY());
			return dy;
		}, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());

		arrow.layoutXProperty().bind(tX.subtract(arrow.widthProperty().divide(2)));
		arrow.layoutYProperty().bind(tY.subtract(arrow.heightProperty().divide(2)));

		DoubleBinding endArrowAngle = Bindings.createDoubleBinding(() -> {
			double stX = toLineEnd ? line.getStartX() : line.getEndX();
			double stY = toLineEnd ? line.getStartY() : line.getEndY();
			double enX = toLineEnd ? line.getEndX() : line.getStartX();
			double enY = toLineEnd ? line.getEndY() : line.getStartY();
			double angle = Math.toDegrees(Math.atan2(enY - stY, enX - stX));
			if (angle < 0) {
				angle += 360;
			}
			return angle;
		}, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());
		arrow.rotateProperty().bind(endArrowAngle);

		return arrow;
	}

	private StackPane getWeight(Line line, String dependency) {
		double size = 20;
		StackPane weight = new StackPane();
		weight.setStyle("-fx-background-color:grey;-fx-border-width:1px;-fx-border-color:black;");
		weight.setPrefSize(size + 150, size);
		weight.setMaxSize(size + 150, size);
		weight.setMinSize(size + 150, size);
		Label txt = new Label(dependency);
		txt.setStyle("-fx-font-size:8px;-fx-font-weight:bold;");
		txt.setTextFill(Color.color(1, 1, 1));
		weight.getChildren().add(txt);

		DoubleBinding wgtSqrHalfWidth = weight.widthProperty().divide(2);
		DoubleBinding wgtSqrHalfHeight = weight.heightProperty().divide(2);
		DoubleBinding lineXHalfLength = line.endXProperty().subtract(line.startXProperty()).divide(2);
		DoubleBinding lineYHalfLength = line.endYProperty().subtract(line.startYProperty()).divide(2);

		weight.layoutXProperty().bind(line.startXProperty().add(lineXHalfLength.subtract(wgtSqrHalfWidth)));
		weight.layoutYProperty().bind(line.startYProperty().add(lineYHalfLength.subtract(wgtSqrHalfHeight)));
		return weight;
	}

}
