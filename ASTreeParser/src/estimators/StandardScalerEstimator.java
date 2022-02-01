package estimators;

import stages.MlLibType;
import stages.Stage;

public class StandardScalerEstimator extends Estimator {
	
	private Stage stage;

	public boolean isEstimator() {

		return (stage.getType() == MlLibType.StandardScaler) ? true : false;
	}
}
