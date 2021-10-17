package pipelines;

import java.util.List;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.StandardScaler;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import com.google.common.collect.ImmutableList;

public class PipelineWithStandardScaler extends AbstractPipeline {

	public PipelineWithStandardScaler() {
		super();
	}

	public PipelineModel create_pipeline() {
		// first stage of pipeline
		StandardScaler standScaler = new StandardScaler().setInputCol("features").setOutputCol("sfeatures")
				.setWithMean(true).setWithStd(true);
		// setup the pipeline
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { standScaler });

		PipelineModel model = pipeline.fit(this.create_schema());

		return model;
	}

	public Dataset<Row> create_schema() {
		/// create schema
		StructType schema = new StructType().add("id", DataTypes.IntegerType, true).add("features", new VectorUDT() , true);

		/// create rows
		Row row1 = RowFactory.create(1, Vectors.dense(10.0, 10000.0, 1.0));
		Row row2 = RowFactory.create(2, Vectors.dense(20.0, 30000.0, 2.0));
		Row row3 = RowFactory.create(3, Vectors.dense(30.0, 40000.0, 3.0));

		List<Row> rows = ImmutableList.of(row1, row2, row3);

		Dataset<Row> dataSet = getSession().createDataFrame(rows, schema);

		dataSet.show();

		return dataSet;
	}

}
