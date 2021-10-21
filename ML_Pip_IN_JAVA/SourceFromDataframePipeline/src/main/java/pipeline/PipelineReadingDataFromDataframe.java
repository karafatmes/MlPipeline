package pipeline;

import java.util.List;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import com.google.common.collect.ImmutableList;

public class PipelineReadingDataFromDataframe {

	public static void main(String[] args) {

		PipelineModel model = create_pipeline();

		Dataset<Row> updated = model.transform(create_schema());

		updated.show();

	}

	public static PipelineModel create_pipeline() {

		// create first stage of pipeline
		StringIndexer category1Indexer = new StringIndexer().setInputCol("category_1").setOutputCol("category_1_index");
		// create second stage of pipeline
		StringIndexer category2Indexer = new StringIndexer().setInputCol("category_2").setOutputCol("category_2_index");
		// create third stage of pipeline
		OneHotEncoder oneHotEncoder = new OneHotEncoder().setInputCol("category_2_index")
				.setOutputCol("category_2_OHE");

		// setup the pipeline
		Pipeline pipeline = new Pipeline()
				.setStages(new PipelineStage[] { category1Indexer, category2Indexer, oneHotEncoder });

		PipelineModel model = pipeline.fit(create_schema());

		return model;

	}

	public static Dataset<Row> create_schema() {

		/// create schema
		StructType schema = new StructType().add("id", DataTypes.IntegerType, true)
				.add("category_1", DataTypes.StringType, true).add("category_2", DataTypes.StringType, true);

		/// create rows
		Row row1 = RowFactory.create(1, "L101", 'R');
		Row row2 = RowFactory.create(2, "L201", 'C');
		Row row3 = RowFactory.create(3, "D111", 'R');
		Row row4 = RowFactory.create(4, "F210", 'R');
		Row row5 = RowFactory.create(5, "D110", 'C');

		List<Row> rows = ImmutableList.of(row1, row2, row3, row4, row5);

		Dataset<Row> dataSet = createSession().createDataFrame(rows, schema);

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
