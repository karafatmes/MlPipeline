package pipeline;
import java.util.List;

import stages.Stage;

public class Pipeline {

	List<Stage> stages;
	
	public Pipeline() {
		
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}
	
}
