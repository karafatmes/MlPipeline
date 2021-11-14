package stages;

public class Stage {

	private KeyType type;
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

	public KeyType getType() {
		return type;
	}

	public void setType(KeyType type) {
		this.type = type;
	}

}
