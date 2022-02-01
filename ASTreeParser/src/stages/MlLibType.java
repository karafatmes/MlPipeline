package stages;

public enum MlLibType {

	OneHotEncoder("OneHotEncoder") ,
	VectorAssembler("VectorAssembler"),
	StringIndexer("StringIndexer"),
	LogisticRegression("LogisticRegression"),
	MinMaxScaler("MinMaxScaler"),
	StandardScaler("StandardScaler"), 
	PipelineModel("PipelineModel"),
	Pipeline("Pipeline"),
	PipelineStage("PipelineStage"),
	SparkSession("SparkSession");
	
	public final String label;
	
	private MlLibType(String label) {
		this.label = label;
	}
	
	public static boolean isMlLibType(String word) {
	    for (MlLibType e : values()) {
	        if (e.label.equals(word)) {
	            return true;
	        }
	    }
	    return false;
	}
}

