import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

/*
 * This class represents the roadmap
 * Author: Valentina Kimberly
 * Student Number: 251197487
 */
public class MyMap {
	
	private Graph adjMat;
	private BufferedReader buffreader;
	private int startingNode;
	private int endNode;
	private int widthMap;
	private int lengthMap;
	private int privateRoads;
	private int constructionRoads;
	private int privateUsed = 0; 
	private int constructionUsed = 0;
	private Stack<Node> stack = new Stack<Node>();
	private String types = "";
	
	
	
	//constructor 
	public MyMap(String inputFile)throws MapException, IOException{
		try{
			
			FileReader reader = new FileReader(inputFile);
			buffreader = new BufferedReader(reader);
			int i = 1;
			int counter = 0; // to check if we have accessed 1 horizontal and 1 vertical line 
			int first = 0;
			boolean horizontal = true;
			int currentNode = 0;
			// reads the input file 
			String read  = buffreader.readLine();
			read = buffreader.readLine();
			// reads until the end of file 
			while(read != null) {
			
				 if( i== 1) {
			// reads the second line which is the startnode
					startingNode = Integer.parseInt(read);}
				else if(i == 2) {
			//reads the third line which is the endnode
					endNode = Integer.parseInt(read);}
				else if(i == 3) {
					widthMap = Integer.parseInt(read);
				}
				else if( i==4) {
					lengthMap = Integer.parseInt(read);
					adjMat = new Graph(widthMap * lengthMap);
				}
				else if( i ==5) {
					privateRoads = Integer.parseInt(read);
				}
				else if( i ==6) {
					constructionRoads = Integer.parseInt(read);
				}else {
					
					if(horizontal == true) {
						currentNode = first;
						
						// goes through only the odd position of the string
						for(int j = 1; j< read.length(); j = j+2) {
							char type = read.charAt(j);
							String string = String.valueOf(type);
							if(type == 'P' || type  == 'V' || type == 'C') {
								try {
									adjMat.addEdge(adjMat.getNode(currentNode) , adjMat.getNode(currentNode+1), string);
								} catch (GraphException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							currentNode++;
					}
						horizontal = false;
						counter++;}
					else if(horizontal == false) {
						currentNode = first;
						
						for(int j= 0; j<read.length(); j = j +2) {
							char type = read.charAt(j);
							String string = String.valueOf(type);
							if(type == 'P' || type == 'V' || type == 'C') {
								try {
									adjMat.addEdge(adjMat.getNode(currentNode), adjMat.getNode(currentNode+widthMap), string);
								} catch (GraphException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							currentNode++;
							
						}
						horizontal = true;
						counter ++;
					}
					
					// if we have stored 1 horizontal and 1 vertical 
					if(counter %2 == 0) {
						first = first + widthMap;
					}
				}
				 read = buffreader.readLine();
				 i++;}
			
	}catch(FileNotFoundException e) {
		throw new MapException("File not found ( in MyMap constructor)\n");
	}catch(IOException e) {
		throw new MapException("File not found ( in MyMap constructor)\n");
	}
		}
	
	
	 
	// returns the graph representing the roadmap 
	public Graph getGraph() {
		return adjMat;
	}
	
	// returns the id of the starting node 
	public int getStartingNode() {
		return startingNode;
	}
	
	// returns the id of the destination node 
	public int getDestinationNode() {
		return endNode;
	}
	
	// returns the maximum number allowed of private roads in the path from the starting 
	// node to the destination 
	public int maxPrivateRoads() {
		return privateRoads;
	}
	
	// returns the maximum number allowed of construction roads in the path from the startin gnode 
	// to the destination 
	public int maxConstructionRoads() {
		return constructionRoads;
	}
	
	// helper method for findpath()
	private boolean path(Node s, Node d, int maxPrivate, int maxConstruction) {
		Node popped;
		s.markNode(true);
		
		stack.push(s);
		int totalV = 0;
		int totalC = 0;
		
		if(maxPrivate < privateUsed || maxConstruction < constructionUsed) {
			 popped = stack.pop();
			popped.markNode(false);
			StringBuffer sb1= new StringBuffer(types);  
			//invoking the method  
			if(sb1.length() != 0) {
			sb1.deleteCharAt(sb1.length()-1);  
			//prints the string after deleting the character   
			types = sb1.toString();  }
			if(maxConstruction <constructionUsed) {
				
				constructionUsed--;
			}else if(maxPrivate < privateUsed) {
				privateUsed--;
			}
			return false;
		}
		else if(s.getId() == d.getId()) {
			return true;
		}
		Iterator<Edge> incidentEdges;
		try {
			incidentEdges = adjMat.incidentEdges(s);
			
			while(incidentEdges.hasNext()) {
				Edge edge = incidentEdges.next();
				if(edge.secondNode().getMark() == false) {
					if(edge.getType().equals("V") ) {
						types += "V";
							privateUsed ++;
						
					}
					else if(edge.getType().equals("C") ) {
						types += "C";
							constructionUsed ++;
						
					}else if(edge.getType().equals("P")) {
						types+= "P";
					}
					if(path(edge.secondNode(), d, maxPrivate, maxConstruction) == true) {
						return  true;
		}}}
			popped = stack.pop();
			popped.markNode(false);
			
			//creating a constructor of StringBuffer class  
			StringBuffer sb= new StringBuffer(types);  
			//invoking the method  
			if(sb.length() != 0) {
			sb.deleteCharAt(sb.length()-1);  
			//prints the string after deleting the character   
			types = sb.toString();  }
			for(int index = 0; index<types.length(); index++) {
				if(types.charAt(index) == 'V') {
					totalV++;
				}
				else if(types.charAt(index) == 'C') {
					totalC++;
				}
			}
			privateUsed = totalV;
			constructionUsed = totalC;

			} catch (GraphException e) {
			
			e.printStackTrace();
		}

		return false;}
		
	//returns a java iterator containing the nodes of a path from start to destination
	public Iterator<Node> findPath(int start, int destination, int maxPrivate,int  maxConstruction) {
		try {
			boolean indicator = false;
			Node startNode = adjMat.getNode(start);
			Node endNode = adjMat.getNode(destination);
			if(path(startNode, endNode, maxPrivate, maxConstruction) == true) {
				return stack.iterator();
			}
				if(indicator  == true) {
					return stack.iterator();
				}

		} catch (GraphException e) {
			e.printStackTrace();
		}
		return null;

	}
		
}
