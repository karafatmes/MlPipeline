package entities;

import java.util.ArrayList;
import java.util.List;

public class NodeOfGraph {
	
	private String name;
	private String value;
	private List<Column> inputs;
	private List<Column> outputs;
	
	public NodeOfGraph() {
		this.inputs = new ArrayList<Column>();
		this.outputs = new ArrayList<Column>();
	}
	
	public NodeOfGraph(String name, String value) {
		this.name = name;
		this.value = value;
		this.inputs = new ArrayList<Column>();
		this.outputs = new ArrayList<Column>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getInputs() {
		return inputs;
	}

	public void setInputs(List<Column> inputs) {
		this.inputs = inputs;
	}

	public List<Column> getOutputs() {
		return outputs;
	}
	

	public void setOutputs(List<Column> outputs) {
		this.outputs = outputs;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
