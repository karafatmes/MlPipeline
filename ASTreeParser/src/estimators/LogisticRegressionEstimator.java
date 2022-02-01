package estimators;

import stages.MlLibType;
import stages.Stage;

public class LogisticRegressionEstimator extends Estimator {
	
	private Stage stage;

	public boolean isEstimator() {

		return (stage.getType() == MlLibType.LogisticRegression) ? true : false;
	}

}
