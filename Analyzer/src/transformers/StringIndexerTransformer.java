package transformers;

import stages.MlLibType;
import stages.Stage;

public class StringIndexerTransformer {

	private Stage stage;

	public boolean isTransformer() {

		return (stage.getType() == MlLibType.StringIndexer) ? true : false;
	}
}
