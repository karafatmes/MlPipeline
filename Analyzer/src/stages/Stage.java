package stages;

public class Stage {

	private MlLibType type;
	private String name;
	private String[] inputCols;
	private String[] outputCols;

	public Stage() {

	}

	public String[] getInputCols() {
		return inputCols;
	}

	public void setInputCols(String[] inputCols) {
		this.inputCols = inputCols;
	}

	public String[] getOutputCols() {
		return outputCols;
	}

	public void setOutputCols(String[] outputCols) {
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
	

}