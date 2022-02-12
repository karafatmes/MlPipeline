package pipeline;
import java.util.ArrayList;
import java.util.List;

import stages.Stage;

public class Pipeline {

	List<Stage> stages;
	private String name;
	
	public Pipeline() {
		this.stages = new ArrayList<Stage>();
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
