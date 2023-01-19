
import java.util.*;
/*
 * This class represents an undirected graph
 * Author: Valentina Kimberly
 * Student Number: 251197487
 */
public class Graph {

	private Node [] nodeArr;
	private Edge [][] edgeArr;
	
	//constructor 
	Graph(int n){
		edgeArr = new Edge[n][n];
		nodeArr = new Node[n];
		for(int i = 0; i<n ; i++) {
			nodeArr[i] = new Node(i);
		}
		}
	
	
	//returns the node with the specified id
	public Node getNode(int id) throws GraphException{
		Node node = null;
		if(id >=nodeArr.length || id<0) {
			throw new GraphException("ID does not exist. (from getNode function)\n");
		}
		else {
			node = nodeArr[id];
		}
		return node;
	}
	
	//ads an edge of the given type connecting u and v
	public void addEdge(Node u, Node v, String edgeType) throws GraphException{
		if(u.getId() >= edgeArr.length || v.getId() >= edgeArr.length || v.getId() < 0 || u.getId() < 0) {
			throw new GraphException("Either node ID does not exist. (from addEdge function)\n");
		}else if(edgeArr[u.getId()][v.getId()] != null) {
			throw new GraphException("There exist an edge in this node. (from addEdge function)\n");
		}
		else {
		edgeArr[u.getId()][v.getId()] = new Edge(u, v, edgeType);
		edgeArr[v.getId()][u.getId()] = new Edge(v, u, edgeType);
		
	}}
	
	// returns java iterator storing all the edges incident on node u 
	public Iterator<Edge> incidentEdges(Node u) throws GraphException{
		if(invalidNode(u) == true) {
			throw new GraphException("Invalid Node (from Incident Edges)\n");
		
		}
		ArrayList<Edge> info = new ArrayList<Edge>();
		for(int i= 0; i<edgeArr.length ; i++) {
			if(edgeArr[u.getId()][i] != null) {
				info.add(edgeArr[u.getId()][i]);
			}
		}
		Iterator<Edge> it = info.iterator();
		if(it.hasNext()) {
			return it;
		}
		else {
			return null;
		}
		
	}
	

	// helper method to check invalid with 2 nodes parameter 
	private boolean invalidNodes(Node u, Node v) {
		System.out.print(edgeArr.length);
		if( u.getId() < 0  || v.getId() < 0 || u.getId() >=  edgeArr.length || v.getId() >=  edgeArr.length  ) {
			return true;
		}else {
			return false;
		}
	}
	
	// helper method to check invalid with 1 node parameter 
	private boolean invalidNode(Node u) {
		if( u.getId() < 0   || u.getId() >=  edgeArr.length || u.getId() >= edgeArr.length  ) {
			return true;
		}else {
			return false;
		}
	}
		
	//returns the edge connecting nodes u and v 
	public Edge getEdge(Node u, Node v) throws GraphException{
		Edge returnEdge = null;
		System.out.print(v.getId());
		if( invalidNodes(u,v) == true) {
			throw new GraphException("No edge exist (detected in getEdge function)\n");
		}
		
		else {
			
		returnEdge =  edgeArr[u.getId()][v.getId()];
	}
		return returnEdge;}
	
	// returns true if nodes u and v are adjacent 
	public boolean areAdjacent(Node u, Node v) throws GraphException{
		if(invalidNodes(u,v) == true) {
			throw new GraphException("Invalid node! (from areAdjacent function)\n");
		}else {
			if(edgeArr[u.getId()][v.getId()] != null) {
				return true;
			}else {
				return false;
			}
		}
	}
}



