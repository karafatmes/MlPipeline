package transformers;

import stages.MlLibType;
import stages.Stage;

public class OneHotEncoderTransformer extends Transformer { 
	
	private Stage stage;

	public boolean isTransformer() {
		
		return (stage.getType() == MlLibType.OneHotEncoder)? true:false; 
	}
		
}
