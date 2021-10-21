package pipelines;

import java.util.List;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import com.google.common.collect.ImmutableList;

public class PipelineWithRegression extends AbstractPipeline {

	public PipelineWithRegression() {
		super();
	}

	public PipelineModel create_pipeline() {
		// create first stage of pipeline
		StringIndexer feature2Indexer = new StringIndexer().setInputCol("feature_2").setOutputCol("feature_2_index");
		// create second stage of pipeline
		StringIndexer feature3Indexer = new StringIndexer().setInputCol("feature_3").setOutputCol("feature_3_index");
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
		Pipeline pipeline = new Pipeline()
				.setStages(new PipelineStage[] { feature2Indexer, feature3Indexer, oneHotEncoder,vectorAssembler,logisticRegression  });

		PipelineModel model = pipeline.fit(this.create_schema());

		return model;
	}

	public Dataset<Row> create_schema() {

		/// create schema
		StructType schema = new StructType().add("feature_1", DataTypes.DoubleType, true)
				.add("feature_2", DataTypes.StringType, true).add("feature_3", DataTypes.StringType, true)
				.add("feature_4", DataTypes.IntegerType, true).add("label", DataTypes.DoubleType, true);

		/// create rows
		Row row1 = RowFactory.create(2.0, 'A', "S10", 40, 1.0);
		Row row2 = RowFactory.create(1.0, 'X', "E10", 25, 1.0);
		Row row3 = RowFactory.create(4.0, 'X', "S20", 10, 0.0);
		Row row4 = RowFactory.create(3.0, 'Z', "S10", 20, 0.0);
		Row row5 = RowFactory.create(4.0, 'A', "E10", 30, 1.0);
		Row row6 = RowFactory.create(2.0, 'Z', "S10", 40, 0.0);
		Row row7 = RowFactory.create(5.0, 'X', "D10", 10, 1.0);

		List<Row> rows = ImmutableList.of(row1, row2, row3, row4, row5, row6, row7);

		Dataset<Row> dataSet = getSession().createDataFrame(rows, schema);

		dataSet.show();
		
		return dataSet;
	}

}
