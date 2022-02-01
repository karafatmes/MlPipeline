package transformers;

import stages.KeyType;
import stages.Stage;

public class VectorAssemblerTransformer {
	
	private Stage stage;

	public boolean isTransformer() {

		return (stage.getType() == KeyType.VectorAssembler) ? true : false;
	}

}
