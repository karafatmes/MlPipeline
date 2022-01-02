package stages;

import java.util.List;

public class Stage {

	private MlLibType type;
	private String name;
	private List<?> inputCols;
	private List<?> outputCols;

	public Stage() {

	}


	public List<?> getInputCols() {
		return inputCols;
	}


	public void setInputCols(List<?> inputCols) {
		this.inputCols = inputCols;
	}


	public List<?> getOutputCols() {
		return outputCols;
	}


	public void setOutputCols(List<?> outputCols) {
		this.outputCols = outputCols;
	}


	public MlLibType getType() {
		return type;
	}

	
	public void setType(MlLibType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Stage getStageByName(String name) {
		return (this.name.equals(name)) ? this : null;
	}


	@Override
	public String toString() {
		return "Stage [type=" + type + ", name=" + name + ", inputCols=" + inputCols + ", outputCols=" + outputCols
				+ "]";
	}


	
	
	
	

}