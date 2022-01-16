package graph.designer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;

import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.GraphMLExporter;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;

import sun.security.provider.certpath.Vertex;

public class GraphDesigner {

	public static void export(Multigraph g, File target) {
		OutputStreamWriter writer = null;

		try {
			writer = new OutputStreamWriter(new FileOutputStream(target), "UTF-8");
			writer.append("hello");
			GraphMLExporter<Object, DefaultWeightedEdge> exporter = new GraphMLExporter<Object, DefaultWeightedEdge>();
			exporter.exportGraph(g, writer);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}

	public static void designGraph(Multigraph g) {
		JGraphXAdapter<Object, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<Object, DefaultWeightedEdge>(g);
		mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
		layout.execute(graphAdapter.getDefaultParent());

		BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
		File imgFile = new File("src/test/resources/graph.png");
		try {
			ImageIO.write(image, "PNG", imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
