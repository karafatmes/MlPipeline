package experiments.many.pipelines;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;

public class Pipelines {

	public Pipelines() {
		defineStagesInPipeline();
	}

	public void defineStagesInPipeline() {

		// 1) stage
		StringIndexer feature1Indexer = new StringIndexer().setInputCols(new String[] { "feature_1" })
				.setOutputCols(new String[] { "feature_1_index" });
		// 2) stage
		StringIndexer feature2Indexer = new StringIndexer().setInputCols(new String[] { "feature_2" })
				.setOutputCols(new String[] { "feature_2_index" });
		// 3) stage
		StringIndexer feature3Indexer = new StringIndexer().setInputCols(new String[] { "feature_3" })
				.setOutputCols(new String[] { "feature_3_index" });
		// 4) stage
		StringIndexer feature4Indexer = new StringIndexer().setInputCols(new String[] { "feature_4" })
				.setOutputCols(new String[] { "feature_4_index" });
		// 5) stage
		StringIndexer feature5Indexer = new StringIndexer().setInputCols(new String[] { "feature_5" })
				.setOutputCols(new String[] { "feature_5_index" });
		// 6) stage
		StringIndexer feature6Indexer = new StringIndexer().setInputCols(new String[] { "feature_6" })
				.setOutputCols(new String[] { "feature_6_index" });
		// 7) stage
		StringIndexer feature7Indexer = new StringIndexer().setInputCols(new String[] { "feature_7" })
				.setOutputCols(new String[] { "feature_7_index" });
		// 8) stage
		StringIndexer feature8Indexer = new StringIndexer().setInputCols(new String[] { "feature_8" })
				.setOutputCols(new String[] { "feature_8_index" });
		// 9) stage
		StringIndexer feature9Indexer = new StringIndexer().setInputCols(new String[] { "feature_9" })
				.setOutputCols(new String[] { "feature_9_index" });
		// 10) stage
		StringIndexer feature10Indexer = new StringIndexer().setInputCols(new String[] { "feature_10" })
				.setOutputCols(new String[] { "feature_10_index" });
		// define pipeline with stages

		// 1) stage
		StringIndexer feature11Indexer = new StringIndexer().setInputCols(new String[] { "feature_11" })
				.setOutputCols(new String[] { "feature_11_index" });
		// 2) stage
		StringIndexer feature12Indexer = new StringIndexer().setInputCols(new String[] { "feature_12" })
				.setOutputCols(new String[] { "feature_12_index" });
		// 3) stage
		StringIndexer feature13Indexer = new StringIndexer().setInputCols(new String[] { "feature_13" })
				.setOutputCols(new String[] { "feature_13_index" });
		// 4) stage
		StringIndexer feature14Indexer = new StringIndexer().setInputCols(new String[] { "feature_14" })
				.setOutputCols(new String[] { "feature_14_index" });
		// 5) stage
		StringIndexer feature15Indexer = new StringIndexer().setInputCols(new String[] { "feature_15" })
				.setOutputCols(new String[] { "feature_15_index" });
		// 6) stage
		StringIndexer feature16Indexer = new StringIndexer().setInputCols(new String[] { "feature_16" })
				.setOutputCols(new String[] { "feature_16_index" });
		// 7) stage
		StringIndexer feature17Indexer = new StringIndexer().setInputCols(new String[] { "feature_17" })
				.setOutputCols(new String[] { "feature_17_index" });
		// 8) stage
		StringIndexer feature18Indexer = new StringIndexer().setInputCols(new String[] { "feature_18" })
				.setOutputCols(new String[] { "feature_18_index" });
		// 9) stage
		StringIndexer feature19Indexer = new StringIndexer().setInputCols(new String[] { "feature_19" })
				.setOutputCols(new String[] { "feature_19_index" });
		// 10) stage
		StringIndexer feature20Indexer = new StringIndexer().setInputCols(new String[] { "feature_20" })
				.setOutputCols(new String[] { "feature_20_index" });
		// define pipeline with stages
		
		VectorAssembler vectorAssembler1 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis1", "feature_dis2" })
				.setOutputCol("features1");
		
		VectorAssembler vectorAssembler2 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis3", "feature_dis4" })
				.setOutputCol("features2");
		
		VectorAssembler vectorAssembler3 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis5", "feature_dis6" })
				.setOutputCol("features3");
		
		VectorAssembler vectorAssembler4 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis7", "feature_dis8" })
				.setOutputCol("features4");
		
		VectorAssembler vectorAssembler5 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis9", "feature_dis10" })
				.setOutputCol("features5");
		
		VectorAssembler vectorAssembler6 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis11", "feature_dis12" })
				.setOutputCol("features6");
		
		VectorAssembler vectorAssembler7 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis13", "feature_dis14" })
				.setOutputCol("features7");
		
		VectorAssembler vectorAssembler8 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis15", "feature_dis16" })
				.setOutputCol("features8");
		
		VectorAssembler vectorAssembler9 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis17", "feature_dis18" })
				.setOutputCol("features9");
		
		VectorAssembler vectorAssembler10 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis19", "feature_dis20" })
				.setOutputCol("features10");
		
		VectorAssembler vectorAssembler11 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis21", "feature_dis22" })
				.setOutputCol("features11");
		
		VectorAssembler vectorAssembler12 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis23", "feature_dis24" })
				.setOutputCol("features12");
		
		VectorAssembler vectorAssembler13 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis25", "feature_dis26" })
				.setOutputCol("features13");
		
		VectorAssembler vectorAssembler14 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis27", "feature_dis28" })
				.setOutputCol("features14");
		
		VectorAssembler vectorAssembler15 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis29", "feature_dis30" })
				.setOutputCol("features15");
		
		VectorAssembler vectorAssembler16 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis31", "feature_dis32" })
				.setOutputCol("features16");
		
		VectorAssembler vectorAssembler17 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis33", "feature_dis34" })
				.setOutputCol("features17");
		
		VectorAssembler vectorAssembler18 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis35", "feature_dis36" })
				.setOutputCol("features18");
		
		VectorAssembler vectorAssembler19 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis37", "feature_dis38" })
				.setOutputCol("features19");
		
		VectorAssembler vectorAssembler20 = new VectorAssembler()
				.setInputCols(new String[] { "feature_dis39", "feature_dis40" })
				.setOutputCol("features20");
		
		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("category_1_index")
				.setOutputCol("category_1_OHE");
		
		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("category_2_index")
				.setOutputCol("category_2_OHE");
		
		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("category_3_index")
				.setOutputCol("category_3_OHE");
		
		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("category_4_index")
				.setOutputCol("category_4_OHE");
		
		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("category_5_index")
				.setOutputCol("category_5_OHE");
		
		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("category_6_index")
				.setOutputCol("category_6_OHE");
		
		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("category_7_index")
				.setOutputCol("category_7_OHE");
		
		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setInputCol("category_8_index")
				.setOutputCol("category_8_OHE");
		
		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("category_9_index")
				.setOutputCol("category_9_OHE");
		
		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("category_10_index")
				.setOutputCol("category_10_OHE");

		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] { feature1Indexer, feature2Indexer,
				feature3Indexer, feature4Indexer, feature5Indexer, feature6Indexer, feature7Indexer, feature8Indexer,
				feature9Indexer, feature10Indexer, feature11Indexer, feature12Indexer,feature13Indexer, feature14Indexer, feature15Indexer,
				feature16Indexer, feature17Indexer, feature18Indexer, feature19Indexer, feature20Indexer,vectorAssembler1, vectorAssembler2,
				vectorAssembler3, vectorAssembler4, vectorAssembler5,vectorAssembler6, vectorAssembler7, vectorAssembler8,vectorAssembler9,
				vectorAssembler10, vectorAssembler11, vectorAssembler12,
				vectorAssembler13, vectorAssembler14, vectorAssembler15,vectorAssembler16, vectorAssembler17, vectorAssembler18,vectorAssembler19,
				vectorAssembler20, oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5, oneHotEncoder6, oneHotEncoder7,
				oneHotEncoder8, oneHotEncoder9, oneHotEncoder10});
	}

}
