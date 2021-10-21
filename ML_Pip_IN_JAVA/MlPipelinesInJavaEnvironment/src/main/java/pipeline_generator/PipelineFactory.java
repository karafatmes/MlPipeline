package pipeline_generator;

import pipelines.AbstractPipeline;
import pipelines.PipelineReadingDataFromCsv;
import pipelines.PipelineReadingDataFromDataframe;
import pipelines.PipelineWithMixedScaler;
import pipelines.PipelineWithRegression;
import pipelines.PipelineWithStandardScaler;

public class PipelineFactory {

public AbstractPipeline getPipeline( int choice) {
		
		switch(choice) {
			
			case 1:
				// create pipeline reading data from dataframe
				return new PipelineReadingDataFromDataframe();
			
			case 2:
				// create pipeline reading data from csv file
				return new PipelineReadingDataFromCsv();
			
			case 3:
				// create pipeline with regression
				return new PipelineWithRegression();
			
			case 4:
				// create pipeline with standard scaler
				return new PipelineWithStandardScaler();
				
			case 5:
				// create pipeline with mixed scaler
				return new PipelineWithMixedScaler();
			
			default:
				throw new IllegalArgumentException("Wrong argument "+ choice);
			
		}
	}

}
