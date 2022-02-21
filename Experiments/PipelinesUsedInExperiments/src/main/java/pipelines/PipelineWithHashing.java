package pipelines;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PipelineWithHashing {
	
	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		// Make predictions.
		Dataset<Row> updated = model.transform(read_data_from_file());

		updated.show();

	}
	
	
	public static PipelineModel create_pipeline() {
		Tokenizer tokenizer = new Tokenizer()
			      .setInputCol("text")
			      .setOutputCol("words");
			  HashingTF hashingTF = new HashingTF()
			      .setNumFeatures(1000)
			      .setInputCol("words")
			      .setOutputCol("features");
			  LogisticRegression lr = new LogisticRegression()
			      .setMaxIter(10)
			      .setRegParam(0.001);

			  Pipeline pipeline = new Pipeline()
			      .setStages(new PipelineStage[] {tokenizer, hashingTF, lr});

			  PipelineModel model = pipeline.fit(read_data_from_file());
		  
		  return model;
	}
	
	
	

	// read data from file
	public static Dataset<Row> read_data_from_file() {
		Dataset<Row> dataSet = createSession().read().format("csv").option("header","true").option("inferSchema", "true").load("./src/main/resources/file.txt");
		dataSet.show();
		dataSet.printSchema();
		return dataSet;
	}
	
	
	public static SparkSession createSession() {
		// Start session with Spark
		System.out.println("Start session \n");

		// First create the session
		SparkSession sparkSession = SparkSession.builder().master("local[2]").appName("Ml Pipeline")
				.config("spark.executor.memory", "1g").config("spark.cores.max", "2").getOrCreate();

		return sparkSession;
	}

}
