package entities;

public class Edge {
	
	private int startIndex;
	private int endIndex;
	private String weight;
	
	public Edge(int startIndex, int endIndex, String weight) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.weight = weight;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	

}
