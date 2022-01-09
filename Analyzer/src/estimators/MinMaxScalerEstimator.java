package estimators;

import stages.MlLibType;
import stages.Stage;

public class MinMaxScalerEstimator extends Estimator{
	
	private Stage stage;

	public boolean isEstimator() {

		return (stage.getType() == MlLibType.MinMaxScaler) ? true : false;
	}

}
