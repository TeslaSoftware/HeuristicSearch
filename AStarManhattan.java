import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;

public class AStarManhattan {
	private Node root; //initial state configuration	
	private int goal [][];
	private int rows, cols;
	private PriorityQueue<Node> openList; //open list of nodes 
	private LinkedList<Node> closedList; //closed list of evaluated nodes
	
	public AStarManhattan(int init[][], int g[][])
	{
		goal = g;
		rows = g.length;
		cols = g[0].length;
		root = new Node(init, 0, null); //initializing root of the search tree
		//define open list as priority queue and define comperator to order node by heuristic cost
		root.setHn(manhattanFunction(root));
		openList = new PriorityQueue<Node>(new Comparator<Node>() 
				{ public int compare(Node n1, Node n2) 
	        		{
						return n1.getFn() - n2.getFn();
	        		}
				});
		root.setFn(manhattanFunction(root));
		openList.add(root);		
		closedList = new LinkedList<Node>();
	}

	
	public void runAStar(){
		int nodesExpanded= 0;
		Node current;
		long startTime = System.nanoTime();
		Node finalNode = root;
		openList.add(root);
		boolean goalReached = false;
		System.out.println("Searching... ");
		while(!openList.isEmpty() && !goalReached)
		{
			//consider the best node in the open list
			current = openList.poll();
			if(current.getHn() == 0){
				goalReached = true;
				finalNode = current;
			}
			else { 
				expandNode(current);
				nodesExpanded++;
				if(nodesExpanded % 100 == 0){
					System.out.print("|" );
				}
				if(nodesExpanded % 10000 == 0){
					System.out.print("\r|" );
				}
			}
			closedList.add(current); //move node to closed list
			
		}
		printPath(finalNode);
		long stopTime = System.nanoTime();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Time elapsed to solve the puzzle: " + elapsedTime + " nano seconds");
		System.out.println("Total number of nodes expanded: " + nodesExpanded);
		
	}
	
	private int manhattanFunction(Node curNode){
		int result = 0;
		int [][] curConf = curNode.getConfiguration();
		for(int row =0; row< rows; row++){
			for (int col = 0; col < cols; col++){
				// checking if current position is not blank
				if(curConf[row][col] !=0){
					//checking value at given state of configuration and searching for this state in goal config
					boolean found = false;
					for(int y =0; y< rows; y++){
						for(int x = 0; x <cols && !found; x++){
							//check if value under goal[y][x] indexes is equal to current value of config
							if(curConf[row][col] == goal[y][x]) {
								//if it is then calculate distance and add it to result
								result += Math.abs(x-col) + Math.abs(y-row);
							}
						}
					}
				}
			}

		}
		return result;
	}
	
	private void swap(int[][] config, int rowSource, int colSource, int rowDest, int colDest)
	{
		int temp = config[rowSource][colSource];
		config[rowSource][colSource]= config[rowDest][colDest];
		config[rowDest][colDest] = temp;
	}
	
	private boolean checkIfConfigurationExists(int [][]config){
		Iterator<Node> openItr = openList.iterator();
		while(openItr.hasNext()){
			if(isMatchingConfig(config, openItr.next() )) { return true;}
		}
		Iterator<Node>closedItr = closedList.iterator();
		while(closedItr.hasNext()){
			if(isMatchingConfig(config, closedItr.next())) {  return true; }
		} 
		return false;		
	}
	
	private boolean isMatchingConfig(int [][] config, Node n){
		return java.util.Arrays.deepEquals(config, n.getConfiguration());
	}
	
	
	private void expandNode(Node current){
		//expanding nodes
		
		//find indexes of blank
		int colOfBlank = 0, rowOfBlank = 0;
		for(int row =0; row< rows; row++){
			for (int col = 0; col < cols; col++){
				if(current.getConfiguration()[row][col] == 0){
					colOfBlank = col;
					rowOfBlank = row;
					break;
				}
			}
		}
		//expand left if it is a legal move
		if(colOfBlank != 0) {
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration());
			swap(config, rowOfBlank, colOfBlank, rowOfBlank ,colOfBlank-1);
			//check if this configuration does not exist in expanded nodes / open queue and closed queue
			if(!checkIfConfigurationExists(config)){
				//add node to open queue list 
				int gn, hn;
				gn = current.getGn()+1;
				Node newNode = new Node(config, gn, current);
				//set newNode as leftMove (child) of current node
				current.setLeftMove(newNode);
				//calculate cost using f(n) = g(n) + h(n)
				hn = manhattanFunction(newNode);
				newNode.setHn(hn);
				newNode.setFn(gn+hn);
				openList.add(newNode);
			}					

		}
		//expand right if it is a legal move
		if(colOfBlank != 2) {
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration());
			swap(config, rowOfBlank, colOfBlank, rowOfBlank ,colOfBlank+1);
			//check if this configuration does not exist in expanded nodes / open queue and closed queue
			if(!checkIfConfigurationExists(config)){
				//add node to open queue list 
				Node newNode = new Node(config, current.getGn()+1, current);
				//set newNode as rightMove (child) of current node
				current.setRightMove(newNode);
				//caclulate cost using f(n) = g(n) + h(n)
				newNode.setHn(manhattanFunction(newNode));
				newNode.setFn(newNode.getGn()+newNode.getHn());
				openList.add(newNode);
			}			
		}
		//expand up if it is a legal move
		if(rowOfBlank != 0) {
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration());
			swap(config, rowOfBlank, colOfBlank, rowOfBlank-1 ,colOfBlank);
			//check if this configuration does not exist in expanded nodes / open queue and closed queue
			if(!checkIfConfigurationExists(config)){
				Node newNode = new Node(config, current.getGn()+1, current);
				//set newNode as upMove (child) of current node
				current.setUpMove(newNode);
				//caclulate cost using f(n) = g(n) + h(n)
				newNode.setHn(manhattanFunction(newNode));
				newNode.setFn(newNode.getGn()+newNode.getHn());
				//add node to open queue list 
				openList.add(newNode);
			}			
		}
		//expand down if it is a legal move
		if(rowOfBlank != 2) {
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration());
			swap(config, rowOfBlank, colOfBlank, rowOfBlank+1 ,colOfBlank);
			//check if this configuration does not exist in expanded nodes / open queue and closed queue
			if(!checkIfConfigurationExists(config)){
				//add node to open queue list 
				Node newNode = new Node(config, current.getGn()+1, current);
				//set newNode as downMove (child) of current node
				current.setDownMove(newNode);
				//caclulate cost using f(n) = g(n) + h(n)
				newNode.setHn(manhattanFunction(newNode));
				newNode.setFn(newNode.getGn()+newNode.getHn());
				openList.add(newNode);
			}			
		}		
	}
	
	private void printPath(Node myNode){
		//Create stack and starting from the result node put go to its parent until you reach the root. 
		Stack<Node> st = new Stack<Node>();
		while(myNode != root){
			//Put each visited node on the stack
			st.push(myNode);
			myNode = myNode.getParent();
		}
		st.push(root);
		int numOfMoves = -1;
		System.out.println("\nSequence of moves in optimal solution: ");
		//Pop all the nodes from the stack to get the optimal sequence
		while(!st.isEmpty()){
			Node current = st.pop();
			current.printConfiguration();
			numOfMoves++;
			System.out.println();
		}
		System.out.println("Total number of moves: " + numOfMoves );
		
		
	}
	
	//creates deep copy of array
	private void copy2DArray(int [][] to , int [][] from ){
		for(int row= 0; row < rows ; row++){
			for(int col=0; col < cols; col++){
				to [row][col] = from[row][col];
			}
		}
	}

}
