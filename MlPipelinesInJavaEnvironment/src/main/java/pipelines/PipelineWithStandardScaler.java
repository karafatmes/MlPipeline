package pipelines;


import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class PipelineWithStandardScaler extends AbstractPipeline{
	
	public PipelineWithStandardScaler() {
		super();
	}
	
	public PipelineModel create_pipeline() {
		return null;
	}
	
	public Dataset<Row> create_schema() {
		return null;
	}

}
