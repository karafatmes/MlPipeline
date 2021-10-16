package pipelines;

import org.apache.spark.ml.PipelineModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public abstract class AbstractPipeline {
	
	private SparkSession session;
	
	
	
	public AbstractPipeline() {
		this.session = createSession();
	}

	public SparkSession createSession() {
	//  Start session with Spark
		System.out.println("Start session \n");

	//  First create the session
		SparkSession sparkSession = SparkSession 
		        .builder() 
		        .master("local[2]") 
		        .appName("Ml Pipeline") 
		        .config("spark.executor.memory", "1g") 
		        .config("spark.cores.max", "2")
		        .getOrCreate();

		    return sparkSession;
	}
	
	public abstract PipelineModel create_pipeline();
	
	public abstract Dataset<Row> create_schema();

	
	public SparkSession getSession() {
		return session;
	}
	
	
}
