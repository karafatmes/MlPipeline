package estimators;

import stages.MlLibType;
import stages.Stage;

public class OneHotEncoderTransformer extends Estimator { 
	
	private Stage stage;

	public boolean isEstimator() {
		
		return (stage.getType() == MlLibType.OneHotEncoder)? true:false; 
	}
		
}
