package pipelines;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PipelineWithRegression {

	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		Dataset<Row> updated = model.transform(read_data_from_file());

		updated.show();

	}

	public static PipelineModel create_pipeline() {
		// create first stage of pipeline
		StringIndexer feature2Indexer = new StringIndexer().setInputCols( new String[] {"feature_2", "feature_3"}).setOutputCols( new String[] {"feature_2_index","feature_3_index"});
		// create third stage of pipeline
		OneHotEncoder oneHotEncoder = new OneHotEncoder()
				.setInputCols(new String[] { "feature_2_index", "feature_3_index" })
				.setOutputCols(new String[] { "feature_2_encoded", "feature_3_encoded" });
		// create fourth stage of pipeline
		VectorAssembler vectorAssembler = new VectorAssembler()
				.setInputCols(new String[] { "feature_1", "feature_2_encoded", "feature_3_encoded", "feature_4" })
				.setOutputCol("features");
		// create fifth stage of pipeline
		LogisticRegression logisticRegression = new LogisticRegression().setFeaturesCol("features")
				.setLabelCol("label");

		// setup the pipeline
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { feature2Indexer,
				oneHotEncoder, vectorAssembler, logisticRegression });

		PipelineModel model = pipeline.fit(read_data_from_file());

		return model;
	}

	
	// read data from file
	public static Dataset<Row> read_data_from_file() {
		Dataset<Row> dataSet = createSession().read().format("csv").option("header","true").option("inferSchema", "true").load("./src/main/resources/file.txt");
		String [] columnsExcluded = {"featurecol1","featurecol2","featurecol3"};
		Dataset<Row> newdataSet = dataSet.drop(columnsExcluded);
		newdataSet.show();
		newdataSet.printSchema();
		return newdataSet;
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
