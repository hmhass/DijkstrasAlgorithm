package DiGraph_A5;

public class Edge {
	
	private long idNum;
	private String eLabel;
	private String sLabel;
	private String dLabel;
	private long weight;

	
	
	public Edge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		this.idNum = idNum;
		this.sLabel = sLabel;
		this.dLabel = dLabel;
		this.weight = weight;
		this.eLabel = sLabel + "--" + dLabel;
	}
	
	
	
	public long getIdNum() {
		return this.idNum;
	}
	
	public String getELabel() {
		return this.eLabel;
	}
	
	public String getSLabel() {
		return this.sLabel;
	}
	
	public String getDLabel() {
		return this.dLabel;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	
	

}
