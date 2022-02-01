package stages;

import java.util.List;

public class Stage {

	private MlLibType type;
	private String name;
	private List<? extends String> inputCols;
	private List<? extends String> outputCols;

	public Stage() {

	}


	public List<? extends String> getInputCols() {
		return inputCols;
	}


	public void setInputCols(List<? extends String> inputCols) {
		this.inputCols = inputCols;
	}


	public List<? extends String> getOutputCols() {
		return outputCols;
	}


	public void setOutputCols(List<? extends String> outputCols) {
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