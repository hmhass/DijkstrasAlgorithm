package DiGraph_A5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Node {
	
	private long idNum;
	private String label;
	HashSet<Edge> inEdges;
	HashSet<Edge> outEdges;
	boolean known;
	long dv;
	Node pv;
	
	
	
	public Node(long idNum, String label) {
		this.idNum = idNum;
		this.label = label;
		this.known = false;
		inEdges = new HashSet<Edge>();
		outEdges = new HashSet<Edge>();
		this.dv = 0;
		this.pv = null;
		
	}
	
	public long getIDNUM() {
		return this.idNum;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	
	


}
