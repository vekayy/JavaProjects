/*
 * This class represents an edge of the graph 
 * Author: Valentina Kimberly
 * Student Number: 251197487
 */
public class Edge {
	
	private Node firstNode;
	private Node endNode;
	private String edgeType;
	
	// constructor 
	Edge(Node u, Node v, String type){
		firstNode = u;
		endNode = v;
		edgeType = type;
	}
	
	// returns the first endpoint of the edge
	Node firstNode() {
		return firstNode;
	}
	
	// returns the second endpoint of the edge
	Node secondNode() {
		return endNode;
	}
	
	// returns the type of the edge 
	String getType() {
		return edgeType;
	}
}
