package DiGraph_A5;
import java.util.HashMap;
import java.util.PriorityQueue;
public class DiGraph implements DiGraphInterface {
	
	private HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	private HashMap<Long, Node> idMap = new HashMap<Long, Node>();
	private HashMap<Long, Edge> edgeMap = new HashMap<Long, Edge>();
	private HashMap<String, Edge> elabelMap = new HashMap<String, Edge>();
	
	
  // in here go all your data and methods for the graph

  public DiGraph ( ) { // default constructor
    // explicitly include this
    // we need to have the default constructor
    // if you then write others, this one will still be there
  }
  
  

@Override
public boolean addNode(long idNum, String label) {
	// First, check to see if the node already exists
	if (nodeMap.containsKey(label) || idMap.containsKey(idNum) || idNum < 0) {
		return false;
		// if not then continue to create new node object and add it to the node map and idMap
	} else {
		Node newNode = new Node(idNum, label);
		nodeMap.put(label, newNode);
		idMap.put(idNum, newNode);
	
		return true;
	}
}

@Override
public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
	// create a key for the string made up of the starting node and ending node
	String key = sLabel + "--" + dLabel;
	// checking to make sure it doesn't already exist
	if (edgeMap.containsKey(idNum) || idNum < 0 || elabelMap.containsKey(key) || !nodeMap.containsKey(sLabel) || !nodeMap.containsKey(dLabel)) {
		return false;
		// if not then create a new Edge object
	} else {
		Edge newEdge = new Edge (idNum, sLabel, dLabel, weight, eLabel);
		edgeMap.put(idNum, newEdge);
		// add the edge object to the HM of edges
		nodeMap.get(sLabel).outEdges.add(newEdge);
		// add the edge to the node it comes from 
		nodeMap.get(dLabel).inEdges.add(newEdge);
		// add the edge to the node it goes to
		elabelMap.put(newEdge.getELabel(), newEdge);
		// add the new edge to the edge HM
		return true;
	}

}

@Override 
public boolean delNode(String label) {
	// check to see if node exists
	if (nodeMap.containsKey(label)) {
		// fetch the node we want to delete
		Node delNode = nodeMap.get(label);
		// remove the incoming edges associated with the node from nodemap, edgemap, and elabelmap
		for (Edge temp: delNode.inEdges) {
			nodeMap.get(temp.getSLabel()).outEdges.remove(temp);
			edgeMap.remove(temp.getIdNum());
			String tempy = temp.getSLabel() + "--" + temp.getDLabel();
			elabelMap.remove(tempy);
		}
		// do the same for outgoing edges
		for (Edge temp: delNode.outEdges) {
			nodeMap.get(temp.getDLabel()).inEdges.remove(temp);
			edgeMap.remove(temp.getIdNum());
			String tempy = temp.getSLabel() + "--" + temp.getDLabel();
			elabelMap.remove(tempy);
		}
		// remove node from idmap and nodemap
		idMap.remove(delNode.getIDNUM());
		nodeMap.remove(label);
		return true;
	}
	// if node did not exist, return false
	return false;
}

@Override
public boolean delEdge(String sLabel, String dLabel) {
	// create the label for input
	String edgelabel = sLabel + "--" + dLabel;
	// if it doesn't exist, return false
	if (!elabelMap.containsKey(edgelabel)) {
		return false;
	}
	Edge delEdge = elabelMap.get(edgelabel);
	// remove edge from edgemap, elabelmap
	edgeMap.remove(delEdge.getIdNum());
	elabelMap.remove(edgelabel);
	// remove edge from starting node's list of outgoing edges
	nodeMap.get(sLabel).outEdges.remove(delEdge);
	// remove edge from ending nodes list of incoming edges
	nodeMap.get(dLabel).inEdges.remove(delEdge);
	return true;
}

@Override
public long numNodes() {
	// TODO Auto-generated method stub
	return nodeMap.size();
}

@Override
public long numEdges() {
	// TODO Auto-generated method stub
	return edgeMap.size();
}

public ShortestPathInfo[] shortestPath(String label) {
	// if node does not exist, return null
	if (!nodeMap.containsKey(label)) {
		return null;
	}
	// create an array for paths to each other node
	ShortestPathInfo[] paths = new ShortestPathInfo[nodeMap.size()];
	// find input in nodemap
	Node start = nodeMap.get(label);
	// Create a priority queue and add the input node first
	PriorityQueue<QueuePair> pq = new PriorityQueue<QueuePair>();
	QueuePair qp = new QueuePair(start, (long) 0);
	pq.add(qp);
	int i = 0;
	
	
	while (pq.size() != 0) {
		// pull from the head of the queue
		QueuePair p = pq.poll();
		Node n = p.n;
		Long dist = p.distance;
		if (n.known) {
			continue;
		
		}
		// mark the node as being known, or accessible from starting node
		n.known = true;
		// add n to paths array with the distance value of it
		paths[i] = new ShortestPathInfo(n.getLabel(), n.dv);
		i++;
		
		// go through every edge leaving node
		for (Edge outEdge : n.outEdges) {
			// find the destination of said edge. mark as "dest" for each iteration
			Node dest = nodeMap.get(outEdge.getDLabel());
			// if node has no parent value already, assign it to the node whose edge led to it
			if (dest.pv == null) {
				dest.pv = n;
				// dest distance value is assigned the parent nodes distance + the distance from parent node to it
				dest.dv = dist + outEdge.getWeight();
				// add the destination node to the priority queue
				qp = new QueuePair(dest, dest.dv);
				pq.add(qp);
				// go to next edge if applicable
				continue;
			}
			// if node already had parent value, else statement from last if statement
			// if the node distance is larger than the new possible distance
			if (dest.dv > dist + outEdge.getWeight()) {
				// change it's distance to the new one since it's smaller
				dest.dv = dist + outEdge.getWeight();
				// change the parent to the current parent node we are working under
				dest.pv = n;
				// add to the priority queue
				qp = new QueuePair(dest, dest.dv);
				pq.add(qp);
			}
			
		}
		
	}
	
	// loop to add the nodes that could not be reached, assign them value of -1, add them to paths list
	for (Node n : nodeMap.values()) {
		if (n.known == false) {
			n.dv = -1;
			paths[i] = new ShortestPathInfo(n.getLabel(), n.dv);
			i++;
		}
	}	
	// print answer!
	return paths;
}
  

}