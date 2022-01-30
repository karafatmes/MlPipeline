package pipeline;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class PipelineOneHotCodeEncoder {

	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		Dataset<Row> updated = model.transform(read_data_from_file());

		updated.show();

	}

	public static PipelineModel create_pipeline() {

		// create first stage of pipeline
		StringIndexer category1Indexer = new StringIndexer().setInputCols(new String[] {"category_1","category_2"}).setOutputCols(
				new String[] {"category_1_index","category_2_index"});
		// create third stage of pipeline
		OneHotEncoder oneHotEncoder = new OneHotEncoder().setInputCol("category_2_index")
				.setOutputCol("category_2_OHE");

		// setup the pipeline
		Pipeline pipeline = new Pipeline()
				.setStages(new PipelineStage[] { category1Indexer, oneHotEncoder });

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
