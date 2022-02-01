package transformers;

import stages.KeyType;
import stages.Stage;

public class StringIndexerTransformer {

	private Stage stage;

	public boolean isTransformer() {

		return (stage.getType() == KeyType.StringIndexer) ? true : false;
	}
}
