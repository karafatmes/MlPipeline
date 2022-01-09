package estimators;

import stages.MlLibType;
import stages.Stage;

public class StringIndexerTransformer extends Estimator{

	private Stage stage;

	public boolean isEstimator() {

		return (stage.getType() == MlLibType.StringIndexer) ? true : false;
	}
	
	
}
