import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Stack;

public class DepthFirstBranchAndBound {
	private LinkedList<Node> openList; //to store all nodes that are on the list of nodes to be expanded
	private LinkedList<Node> closedList; //to store all the nodes that were expanded and evaluated. 
	private int [][] goal; //goal configuration
	private int rows, cols, bound; //number of rows and number of columns, bound is an upper bound of optimal solution
	private Node root;
	private PriorityQueue<Node> pQueue; //priority queue to sort expanded nodes based on their f(n)
	
	public DepthFirstBranchAndBound(int[][] init, int[][] g){
		openList = new LinkedList<Node>();
		closedList = new LinkedList<Node>();
		goal = g;
		rows = g.length;		//storing number of rows
		cols = g[0].length; 	//storing number of columns
		root = new Node(init, 0, null); //initializing root of the search tree
		root.setFn(manhattanFunction(root.getConfiguration())); //calculate and set F(n) of the root
		bound = factorial(rows * cols)+1; //set bound to infinity, or actually number of all possible states plus one
		pQueue = new PriorityQueue<Node>(new Comparator<Node>() //order queue in descending order of f(n) 
				{ public int compare(Node n1, Node n2) 
	        		{
						return n2.getFn() - n1.getFn();
	        		}
				});
	}
	
	
	public void runDFBnB(){
		Node finalNode = null;
		Node current;
		long startTime = System.nanoTime();
		long timeWhenSolutionFound = 0;
		int nodesExpanded = 0;
		openList.add(root);
		System.out.println("Searching... ");
		//while list is not empty
		while(!openList.isEmpty()){
			//get first element from the list
			current = openList.poll();
			//check if configuration at current node is the goal configuration
			if(isMatchingConfig(goal, current)){
				//if it is then compute the bound of the solution and save it as final node
				bound = current.getGn();
				finalNode = current;
				timeWhenSolutionFound = System.nanoTime();
			}
			else {
				//otherwise expand node
				expand(current);
				nodesExpanded++;
				if(nodesExpanded % 100 == 0){
					System.out.print("|" );
				}
				if(nodesExpanded % 10000 == 0){
					System.out.print("\r|" );
				}
			}		
			closedList.add(current); //put current to list of evaluated nodes
		}
		long finishTime = System.nanoTime();
		printPath(finalNode);		
		System.out.println("Total number of nodes expanded: " + nodesExpanded);
		System.out.println("Time to find optimal solution: " + (timeWhenSolutionFound-startTime)+ " nano seconds");
		System.out.println("Total of algorithm run: " + (finishTime-startTime) + " nano seconds");
		
	}
	
	private int manhattanFunction(int [][] curConf){
		int result = 0;
		for(int row =0; row< rows; row++){
			for (int col = 0; col < cols; col++){
				// checking if current position is not blank
				if( curConf[row][col] !=0 ){
					boolean found = false;
					//checking value at given state of configuration and searching for this state in goal config
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

	
	private int factorial(int num){
		int result = 1;
		for(int i = num; i > 0; i-- ){
			result *= i;
		}
		return result;
	}
	
	private boolean isMatchingConfig(int [][] config, Node n){
		return java.util.Arrays.deepEquals(config, n.getConfiguration());
	}
	
	private void expand(Node current){
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
		
		//expand left
		if(colOfBlank !=0){ //check if column is not the first one
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration()); //make deep copy of 2D array
			swap(config, rowOfBlank, colOfBlank, rowOfBlank ,colOfBlank-1); //swap elements according to move left
			//calculate f(n), g(n) and h(n) using Manhattan heuristics
			int gn = current.getGn()+1;
			int hn = manhattanFunction(config);
			int fn = gn + hn;
			//if f(n) is smaller than current bound
			if((fn) < bound){
				//check open list and closed list if configuration already exists
				if(!checkIfConfigurationExists(config, fn)){
					//if it does not or it had worst cost (and duplicate was removed) then create a new node
					Node newNode = new Node(config, gn, current);
					//set newNode as leftMove (child) of current node
					current.setLeftMove(newNode);
					//set h(n)
					newNode.setHn(hn);
					//set f(n)
					newNode.setFn(fn);
					pQueue.add(newNode);
				}
			}				
		}
		//expand right
		if(colOfBlank !=2){ //check if column is not the last one
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration()); //make deep copy of 2D array
			swap(config, rowOfBlank, colOfBlank, rowOfBlank ,colOfBlank+1); //swap elements according to move right
			//calculate f(n), g(n) and h(n) using Manhattan heuristics
			int gn = current.getGn()+1;
			int hn = manhattanFunction(config);
			int fn = gn + hn;
			//if f(n) is smaller than current bound
			if((fn) < bound){
				//check open list and closed list if configuration already exists
				if(!checkIfConfigurationExists(config, fn)){
					//if it does not or it had worst cost (and duplicate was removed) then create a new node
					Node newNode = new Node(config, gn, current);
					//set newNode as rightMove (child) of current node
					current.setRightMove(newNode);
					//set h(n)
					newNode.setHn(hn);
					//set f(n)
					newNode.setFn(fn);
					pQueue.add(newNode);
				}
			}				
		}

		
		//expand up
		if(rowOfBlank !=0){ //check if row is not the first one
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration()); //make deep copy of 2D array
			swap(config, rowOfBlank, colOfBlank, rowOfBlank-1 ,colOfBlank); //swap elements according to move up
			//calculate f(n), g(n) and h(n) using Manhattan heuristics
			int gn = current.getGn()+1;
			int hn = manhattanFunction(config);
			int fn = gn + hn;
			//if f(n) is smaller than current bound
			if((fn) < bound){
				//check open list and closed list if configuration already exists
				if(!checkIfConfigurationExists(config, fn)){
					//if it does not or it had worst cost (and duplicate was removed) then create a new node
					Node newNode = new Node(config, gn, current);
					//set newNode as upMove (child) of current node
					current.setUpMove(newNode);
					//set above calculated h(n)
					newNode.setHn(hn);
					//set f(n)
					newNode.setFn(fn);
					pQueue.add(newNode);
				}
			}				
		}
		//expand down
		if(rowOfBlank !=2){ //check if row is not the last one
			int[][] config = new int[rows][cols];
			copy2DArray(config, current.getConfiguration()); //make deep copy of 2D array
			swap(config, rowOfBlank, colOfBlank, rowOfBlank+1 ,colOfBlank); //swap elements according to move down
			//calculate f(n), g(n) and h(n) using Manhattan heuristics
			int gn = current.getGn()+1;
			int hn = manhattanFunction(config);
			int fn = gn + hn;
			//if f(n) is smaller than current bound
			if((fn) < bound){
				//check open list and closed list if configuration already exists
				if(!checkIfConfigurationExists(config, fn)){
					//if it does not or it had worst cost (and duplicate was removed) then create a new node
					Node newNode = new Node(config, gn, current);
					//set newNode as downMove (child) of current node
					current.setDownMove(newNode);
					//calculate cost using Manhattan heuristic function
					newNode.setHn(hn);
					//set f(n)
					newNode.setFn(fn);
					pQueue.add(newNode);
				}
			}				
		}
		
		//Put all the expanded nodes from priority queue to openList, in ascending order of their f(n) function
		while(!pQueue.isEmpty()){
			openList.addFirst(pQueue.poll());
		}		
	}
	
	//creates deep copy of array
	private void copy2DArray(int [][] to , int [][] from ){
		for(int row= 0; row < rows ; row++){
			for(int col=0; col < cols; col++){
				to [row][col] = from[row][col];
			}				
		}
	}
	
	private boolean checkIfConfigurationExists(int [][]config, int fn){
		Iterator<Node> openItr = openList.iterator();
		while(openItr.hasNext()){
			Node currentNode = openItr.next();
			if(isMatchingConfig(config, currentNode ))
			{ 
				//if f(n) value of new node is smaller than f(n) currently found duplicate then remove duplicate and return false to proceed with creation of a better, less costly node
				if(fn < currentNode.getFn()){
					openList.remove(currentNode);
					return false;
				}			
				return true;
			}
		}
		Iterator<Node>closedItr = closedList.iterator();
		while(closedItr.hasNext()){
			Node currentNode = closedItr.next();
			if(isMatchingConfig(config, currentNode ))
			{ 
				//if f(n) value of new node is smaller than f(n) currently found duplicate then remove duplicate and return false to proceed with creation of a better, less costly node
				if(fn < currentNode.getFn()){
					closedList.remove(currentNode);
					return false;
				}			
				return true;
			}
		}
		return false;		
	}
	
	//swap elements of the array based on the supplied arguments
	private void swap(int[][] config, int rowSource, int colSource, int rowDest, int colDest)
	{
		int temp = config[rowSource][colSource];
		config[rowSource][colSource]= config[rowDest][colDest];
		config[rowDest][colDest] = temp;
	}
	
	private void printPath(Node myNode){
		if(myNode == null) {System.out.println("No solution exists."); return; }
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
	
}
