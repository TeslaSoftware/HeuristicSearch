
public class Node {
	private Node leftMove, rightMove, upMove, downMove, parent;
	//f(n) = g(n) + h(n)
	private int fn; // f(n) - estimated total cost of path via n to final state
	private int gn;
	private int hn; //heuristic function h(n) - estimated cost from n to final state (goal)
	private int configuration[][];
	
	public Node(int [][] arr, int PathC, Node p) {
		configuration = arr.clone();
		gn= PathC;
		parent = p;
	}
	
	//Accessors
	public Node getLeftMove() { return leftMove; }
	public Node getRightMove() { return rightMove; }
	public Node getUpMove() { return upMove; }
	public Node getDownMove() { return downMove; }
	public int getGn() { return gn;}
	public int getHn() { return hn;}
	public int getFn() { return fn;}
	public Node getParent() {return parent; }
	public int[][] getConfiguration() { return configuration; }
	
	//Mutators
	public void setFn(int c) { fn =c ; }
	public void setHn(int c) { hn = c; }
	public void setLeftMove(Node n) { leftMove = n; }
	public void setRightMove(Node n) { rightMove = n; }
	public void setUpMove(Node n) { upMove = n; }
	public void setDownMove(Node n) { downMove = n; }
	
	public void printConfiguration () {
				System.out.println("-----------");
		for (int row = 0; row < configuration.length; row ++) {
				System.out.print("| ");
			for (int col = 0; col < configuration[0].length; col++ ) {
				System.out.print(configuration[row][col] + " |");
			}
				System.out.println("\n-----------");
		}
		
		
	}
	
	
}
