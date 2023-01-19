/*
 * This class represent a node of the graph 
 * Author: Valentina Kimberly
 * Student Number: 251197487
 */
public class Node {
	private int id;
	private boolean marked;
	// constructor 
	Node(int id){
		this.id = id;
	}
	
	// marks the node with the specified value, either true or false 
	public void markNode(boolean mark){
		this.marked = mark;
	}
	
	// returns the value with which the node has been marked
	public boolean getMark() {
		return marked;
	}
	
	// returns the id of this node 
	public int getId() {
		return id;
	}
}
