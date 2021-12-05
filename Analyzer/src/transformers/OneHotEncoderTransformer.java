package transformers;

import stages.KeyType;
import stages.Stage;

public class OneHotEncoderTransformer extends Transformer { 
	
	private Stage stage;

	public boolean isTransformer() {
		
		return (stage.getType() == KeyType.OneHotEncoder)? true:false; 
	}
		
}
