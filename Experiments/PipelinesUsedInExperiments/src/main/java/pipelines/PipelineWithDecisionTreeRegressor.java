package pipelines;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.DecisionTreeRegressor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PipelineWithDecisionTreeRegressor {

	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		// Make predictions.
		Dataset<Row> updated = model.transform(read_data_from_file());

		updated.show();

	}

	public static PipelineModel create_pipeline() {
		StringIndexer indexer = new StringIndexer().setInputCol("labelDesicison").setOutputCol("labelIndex")
				.setHandleInvalid("skip");
		
		VectorAssembler vectorAssembler = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis1", "feature_dis2", "feature_dis3" })
				.setOutputCol("features");

		DecisionTreeRegressor regressionModel = new DecisionTreeRegressor().setLabelCol("labelIndex")
				.setFeaturesCol("features");

		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { indexer,vectorAssembler ,regressionModel });

		PipelineModel sparkPipeline = pipeline.fit(read_data_from_file());

		return sparkPipeline;
	}

	// read data from file
	public static Dataset<Row> read_data_from_file() {
		Dataset<Row> dataSet = createSession().read().format("csv").option("header", "true")
				.option("inferSchema", "true").load("./src/main/resources/file.txt");
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
