package transformers;

import stages.MlLibType;
import stages.Stage;

public class VectorAssemblerTransformer {
	
	private Stage stage;

	public boolean isTransformer() {

		return (stage.getType() == MlLibType.VectorAssembler) ? true : false;
	}

}
