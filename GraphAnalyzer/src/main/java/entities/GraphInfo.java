package entities;

public class GraphInfo {
	
	private String startNode;
	private String endNode;
	private int count;
	
	public GraphInfo( String startNode, String endNode, int count) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.count = count;
	}

	public String getStartNode() {
		return startNode;
	}

	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}

	public String getEndNode() {
		return endNode;
	}

	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public boolean isExists( String startNode, String endNode) {
		return (startNode.equals(this.startNode) && endNode.equals(this.endNode));
	}
	
	public void increaseCount() {
		this.count = this.count+1;
	}

}
