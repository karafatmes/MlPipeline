package pipelines;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.OneHotEncoder;

public class Pipelines {

	public Pipelines() {
//		createLinearWorkflow();
//		createWishbone();
//		createPrimaryFlow();
//		createTree();
//		createFlatHierarchy();
		createRightDeepHierarchy();
	}

//	public void createLinearWorkflow() {
//
//		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1")
//				.setOutputCol("feature_1_index");
//		
//		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_1_index")
//				.setOutputCol("feature_2_index");
//		
//		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_2_index")
//				.setOutputCol("feature_3_index");
//		
//		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_3_index")
//				.setOutputCol("feature_4_index");
//		
//		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_4_index")
//				.setOutputCol("feature_5_index");
//		
//		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_5_index")
//				.setOutputCol("feature_6_index");
//		
//		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_6_index")
//				.setOutputCol("feature_7_index");
//		
//		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setInputCol("feature_7_index")
//				.setOutputCol("feature_8_index");
//		
//		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_8_index")
//				.setOutputCol("feature_9_index");
//		
//		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_9_index")
//				.setOutputCol("feature_10_index");
//		
//		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {
//				 oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5, oneHotEncoder6, oneHotEncoder7,
//				oneHotEncoder8, oneHotEncoder9, oneHotEncoder10});
//	}

//	public void createWishbone() {
//		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1").setOutputCol("feature_1_index");
//
//		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_2").setOutputCol("feature_2_index");
//
//		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_1_index")
//				.setOutputCol("feature_3_index");
//
//		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_2_index")
//				.setOutputCol("feature_3_index");
//
//		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_3_index")
//				.setOutputCol("feature_4_index");
//
//		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_4_index")
//				.setOutputCol("feature_5_index");
//
//		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_5_index")
//				.setOutputCol("feature_6_index");
//
//		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setInputCol("feature_6_index")
//				.setOutputCol("feature_7_index");
//
//		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_7_index")
//				.setOutputCol("feature_8_index");
//
//		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_8_index")
//				.setOutputCol("feature_9_index");
//
//		Pipeline pipeline = new Pipeline().setStages(
//				new PipelineStage[] { oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5,
//						oneHotEncoder6, oneHotEncoder7, oneHotEncoder8, oneHotEncoder9, oneHotEncoder10 });
//
//	}

//	public void createPrimaryFlow() {
//		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1").setOutputCol("feature_1_index");
//
//		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_1_index")
//				.setOutputCol("feature_2_index");
//
//		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_2_index")
//				.setOutputCol("feature_3_index");
//
//		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_3_index")
//				.setOutputCol("feature_4_index");
//
//		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_4_index")
//				.setOutputCol("feature_5_index");
//
//		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_5_index")
//				.setOutputCol("feature_6_index");
//
//		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_6_index")
//				.setOutputCol("feature_7_index");
//
//		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setOutputCol("feature_7_index");
//
//		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_7_index")
//				.setOutputCol("feature_8_index");
//
//		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_8_index")
//				.setOutputCol("feature_9_index");
//
//		Pipeline pipeline = new Pipeline().setStages(
//				new PipelineStage[] { oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5,
//						oneHotEncoder6, oneHotEncoder7, oneHotEncoder8, oneHotEncoder9, oneHotEncoder10 });
//	}

//	public void createTree() {
//		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1").setOutputCol("feature_1_index");
//
//		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_2")
//				.setOutputCol("feature_2_index");
//
//		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_1_index")
//				.setOutputCol("feature_3_index");
//
//		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_2_index")
//				.setOutputCol("feature_3_index");
//
//		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_3_index")
//				.setOutputCol("feature_4_index");
//
//		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_4_index")
//				.setOutputCol("feature_5_index");
//
//		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_5_index")
//				.setOutputCol("feature_6_index");
//
//		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setOutputCol("feature_6_index");
//
//		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_6_index")
//				.setOutputCol("feature_7_index");
//
//		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_7_index")
//				.setOutputCol("feature_8_index");
//
//		Pipeline pipeline = new Pipeline().setStages(
//				new PipelineStage[] { oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5,
//						oneHotEncoder6, oneHotEncoder7, oneHotEncoder8, oneHotEncoder9, oneHotEncoder10 });
//	}
	
//	public void createFlatHierarchy() {
//		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1")
//		.setOutputCol("feature_1_index");
//
//OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_1_index")
//		.setOutputCol("feature_2_index");
//
//OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_2_index")
//		.setOutputCol("feature_3_index");
//
//OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_3_index")
//		.setOutputCol("feature_4_index, feature_5_index, feature_6_index");
//
//OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_4_index")
//		.setOutputCol("feature_7_index");
//
//OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_5_index")
//		.setOutputCol("feature_8_index");
//
//OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_6_index")
//		.setOutputCol("feature_9_index");
//
//OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setInputCol("feature_7_index");
//
//OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_8_index");
//
//OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_9_index");
//
//Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {
//		 oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5, oneHotEncoder6, oneHotEncoder7,
//		oneHotEncoder8, oneHotEncoder9, oneHotEncoder10});
//	}
	
	public void createRightDeepHierarchy() {
		

		
		OneHotEncoder oneHotEncoder1 = new OneHotEncoder().setInputCol("feature_1")
				.setOutputCol("feature_2_index");
		
		OneHotEncoder oneHotEncoder2 = new OneHotEncoder().setInputCol("feature_2_index")
				.setOutputCols(new String[] {"feature_3_index", "feature_4_index"});
		
		OneHotEncoder oneHotEncoder3 = new OneHotEncoder().setInputCol("feature_3_index")
				.setOutputCol("feature_6_index");
		
		OneHotEncoder oneHotEncoder4 = new OneHotEncoder().setInputCol("feature_4_index")
				.setOutputCol("feature_5_index");
		
		OneHotEncoder oneHotEncoder5 = new OneHotEncoder().setInputCol("feature_6_index")
				.setOutputCols( new String[] {"feature_7_index", "feature_8_index"});
		
		OneHotEncoder oneHotEncoder6 = new OneHotEncoder().setInputCol("feature_5_index");
				
		
		OneHotEncoder oneHotEncoder7 = new OneHotEncoder().setInputCol("feature_7_index").setOutputCol("feature_9_index");
				
		OneHotEncoder oneHotEncoder8 = new OneHotEncoder().setInputCol("feature_8_index").setOutputCol("feature_10_index");
		
		OneHotEncoder oneHotEncoder9 = new OneHotEncoder().setInputCol("feature_9_index");
				
		
		OneHotEncoder oneHotEncoder10 = new OneHotEncoder().setInputCol("feature_10_index");
				
		
		Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {
				 oneHotEncoder1, oneHotEncoder2, oneHotEncoder3, oneHotEncoder4, oneHotEncoder5, oneHotEncoder6, oneHotEncoder7,
				oneHotEncoder8, oneHotEncoder9, oneHotEncoder10});
	}
}
