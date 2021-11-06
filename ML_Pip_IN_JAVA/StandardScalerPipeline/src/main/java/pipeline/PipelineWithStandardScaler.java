package pipeline;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.StandardScaler;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PipelineWithStandardScaler {

	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		Dataset<Row> updated = model.transform(read_data_from_file());

		updated.show();

	}

	public static PipelineModel create_pipeline() {
		// first stage of pipeline
		VectorAssembler assembler = new VectorAssembler()
			      .setInputCols(new String[] {"featurecol1", "featurecol2", "featurecol3"})
			      .setOutputCol("features");
		// second stage of pipeline
		StandardScaler standScaler = new StandardScaler().setInputCol("features").setOutputCol("sfeatures")
				.setWithMean(true).setWithStd(true);
		// setup the pipeline
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { assembler,standScaler });

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
