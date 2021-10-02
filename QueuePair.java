package DiGraph_A5;

public class QueuePair implements Comparable<QueuePair>{
	
	public Node n;
	public Long distance;
	
	public QueuePair(Node n, Long i) {
		this.n = n;
		this.distance = i;
	}

	@Override
	// used to sort the nodes in the priority queue based on lowest distance.
	public int compareTo(QueuePair o) {
		// TODO Auto-generated method stub
		if (this.distance == o.distance) return 0;
		else if (this.distance > o.distance) return 1;
		else return -1;
			

	}

}
