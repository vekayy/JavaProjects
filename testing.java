import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;
import java.util.Vector;
public class MyMap {
	
	private Graph adjMat;
	private BufferedReader buffreader;
	private int startingNode;
	private int endNode;
	private int widthMap;
	private int lengthMap;
	private int privateRoads;
	private int constructionRoads;
	private boolean horizontal = true;
	private int privateUsed = 0; 
	private int constructionUsed = 0;
	private Stack<Node> stack = new Stack<Node>();
	private Edge copyEdge = null;
	
	
	
	//constructor (double check if the constructor has to be public)
	public MyMap(String inputFile)throws MapException, IOException{
		try{
			
			FileReader reader = new FileReader(inputFile);
			buffreader = new BufferedReader(reader);
			int i = 1;
			int rowNum =0;
			int columnNum = 0;
			int counter = 0; // to check if we have accessed 1 horizontal and 1 vertical line 
			int first = 0;
			boolean horizontal = true;
			int currentNode = 0;
			
			String read  = buffreader.readLine();
			read = buffreader.readLine();
			while(read != null) {
			
//				if(i == 0) {
//			// reads the first line which is the scale	
//					buffreader.readLine();}
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
					
					if(counter %2 == 0) {
						first = first + widthMap;
					}
//					// if the line is even , i.e row 
//					if(i % 2 != 0) {
//						
//					String newLine = buffreader.readLine();
//					for(int j = 0; j< newLine.length(); j++) {
//						char type = newLine.charAt(j);
//						String string = String.valueOf(type);
//						try {
//							adjMat.addEdge(new Node(rowNum), new Node(j), string);
//						} catch (GraphException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
////						// denotes either an intersection of two roads, or a dead end
////						if(newLine.charAt(j) == '+') {
////							
////							adjMat.addEdge(new Node(rowNum), new Node(j), toString('+'));
////						// where should i put '+' ?	
////						}
////						
////						// denotes a private road
////						else if(newLine.charAt(j) == 'V') {
////							
////						}
////						
////						// denotes a public road
////						else if(newLine.charAt(j) == 'P') {
////							
////						}
////						
////						// denotes a construction road
////						else if(newLine.charAt(j) == 'C') {
////							
////						}
////						
////						//denotes a block of houses, a building or a park
////						else if(newLine.charAt(j) == 'B') {
////							
////						}
////				}}
////					// if the line is odd
////					else{
////						
////					}
//					}
//					rowNum++;}
//					// if the line is odd, i.e column
//					else {
//						String newLine = buffreader.readLine();
//						for(int j = 0; j< newLine.length(); j++) {
//							char type = newLine.charAt(j);
//							String string = String.valueOf(type);
//							try {
//								adjMat.addEdge(new Node(j), new Node(columnNum), string);
//							} catch (GraphException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}}
//						columnNum++;}}
//				i++;}
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
	
	
	private boolean path(Node s, Node d, int maxPrivate, int maxConstruction) {
		Node popped;
		s.markNode(true);
		stack.push(s);

		if(maxPrivate < privateUsed || maxConstruction < constructionUsed) {
			 popped = stack.pop();
			popped.markNode(false);
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
			
			
			// follow the algorithm 
			while(incidentEdges.hasNext()) {
				Edge edge = incidentEdges.next();
				copyEdge = edge;
				if(edge.secondNode().getMark() == false) {
					copyEdge = edge;
					if(edge.getType().equals("V") ) {
							privateUsed ++;
						
					}
					else if(edge.getType().equals("C") ) {
							constructionUsed ++;
						
					}
					if(path(edge.secondNode(), d, maxPrivate, maxConstruction) == true) {
						return  true;

			
		}}}
			Edge anotherEdge = copyEdge;
			if(copyEdge.getType().equals("V")) {
				privateUsed--;
			}
			else if(copyEdge.getType().equals("C")) {
				constructionUsed--;
			}
			popped = stack.pop();
			popped.markNode(false);
			
			
			
			
			} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;}
		

				
	
	//returns a java iterator containing the nodes of a path from starft to destination
	public Iterator<Node> findPath(int start, int destination, int maxPrivate,int  maxConstruction) {
		try {
			boolean indicator = false;
			
			Node startNode = adjMat.getNode(start);
			Node endNode = adjMat.getNode(destination);
			if(path(startNode, endNode, maxPrivate, maxConstruction) == true) {
				return stack.iterator();
			}
//			while(indicator == false) {
//				indicator = path(startNode, endNode, maxPrivate, maxConstruction);
				if(indicator  == true) {
					return stack.iterator();
				}
			

			
		} catch (GraphException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
//		Stack<Node> stack = new Stack<Node>();
//		try {
//			Node startNode = adjMat.getNode(start);
//			Node endNode = adjMat.getNode(destination);
//			startNode.markNode(true);
//			 stack.push(startNode);
//			if(startNode.getId() == endNode.getId()) {
//				return stack.iterator();
//			}
//			Iterator<Edge> incidentEdges = adjMat.incidentEdges(startNode);
//			
//			// follow the algorithm 
//			while(incidentEdges.hasNext()) {
//				Edge edge = incidentEdges.next();
//				// double check if it should be second node or first node?
//				if(edge.secondNode().getMark() == false) {
//					if(findPath(edge.secondNode().getId(), destination, maxPrivate, maxConstruction ) != null) {
//						return stack.iterator();
//					}
//				}
//				
//				
//				
//			}
//		} catch (GraphException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		stack.pop();
//		return null;
//		Iterator nodes;
//		Node endNode = new Node(destination);
//		Node startNode = new Node(start);
//		int privateUsed = 0;
//		int constructionUsed = 0;
//		startNode.markNode(true);
//		stack.push(startNode);
//		if(startNode.getId() == endNode.getId()) {
//			 nodes = stack.iterator();
//			return nodes;
//		}
//		try {
//			String privateStr = String.valueOf('V');
//			String constructionStr = String.valueOf('C');
//			Iterator<Edge> incidentEdges = adjMat.incidentEdges(startNode);
//			Edge inciEdge = incidentEdges.next();
//			while(inciEdge != null) {
//				if(inciEdge.firstNode().getMark() == false) {
//					if(inciEdge.getType() == privateStr) {
//						if(privateUsed != maxPrivate) {
//							privateUsed++;
//							if(findPath(inciEdge.firstNode().getId(), destination, maxPrivate, maxConstruction) != null) {
//								nodes = stack.iterator();
//								return nodes;
//							}
//						}
//					}
//					else if(inciEdge.getType() == constructionStr) {
//						if(constructionUsed != maxConstruction) {
//							constructionUsed++;
//							if(findPath(inciEdge.firstNode().getId(), destination, maxPrivate, maxConstruction) != null) {
//								nodes = stack.iterator();
//								return nodes;}
//							
//						}
//					}
//				}
//				
//			}
//			
//		} catch (GraphException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		stack.pop();
//		return null;}
//		
	}
		
//		Node startofNode = new Node(start);
//		Iterator incidentEdges = adjMat.incidentEdges(startofNode);
//		Stack<Node> stack = new Stack<Node>();
//		startofNode.markNode(true);
//		stack.push(startofNode);
//		if(start == destination) {
//			return (Iterator) stack;
//		
//	}while(incidentEdges.hasNext()) {
//		if(startofNode.getMark() == false) {
//			if(findPath())
//		}
//	}
}
