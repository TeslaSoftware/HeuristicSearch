import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int[][] goalConf = {
				{1,2,3},
				{8,0,4},
				{7,6,5},
		};
		
		int[][] easyConf = {
				{1,3,4},
				{8,6,2},
				{7,0,5},
		};
		
		int[][] mediumConf = {
				{2,8,1},
				{0,4,3},
				{7,6,5},
		};
		
		int[][] hardConf = {
				{2,8,1},
				{4,6,3},
				{0,7,5},
		};
		
		int[][] worstConf = {
				{5,6,7},
				{4,0,8},
				{3,2,1},
		};
		
		Scanner input = new Scanner(System.in);
		boolean runProgram = true;
		while(runProgram){
			System.out.println("\nPlease select algorithm to run: \n1. A* Search using heuristic function f(n) = g(n) + h(n), considering number of displaced tiles. \n2. A* Search using Manhattan Heuristic function. \n3. Iterative Deepening A* with the Manhattan Heuristic function. \n4. Depth-first Branch and Bound with the Manhattan heuristic function. \n0. To exit the program.");
			switch(input.next().charAt(0)){
				case '1':{
					System.out.println("Please select the difficulty of the puzzle to solven: \n1. Easy \n2. Medium \n3. Hard \n4. Worst");
					switch(input.next().charAt(0)){
						case '1':{
							System.out.println("Testing easy configuration on A* with heuristic function f(n) = g(n) + h(n), where h(n) is number of misplaced tiles:");
							AStarFn asfEasy = new AStarFn(easyConf, goalConf);
							asfEasy.runAStar();
							break;
						}
						case '2':{
							System.out.println("\n\nTesting medium configuration on A* with heuristic function f(n) = g(n) + h(n), where h(n) is number of misplaced tiles:");
							AStarFn asfMedium = new AStarFn(mediumConf, goalConf);
							asfMedium.runAStar();
							break;
						}
						case '3':{
							System.out.println("\n\nTesting hard configuration on A* with heuristic function f(n) = g(n) + h(n), where h(n) is number of misplaced tiles:");
							AStarFn asfHard = new AStarFn(hardConf, goalConf);
							asfHard.runAStar();
							break;
						}
						case '4':{
							System.out.println("\n\nTesting worst configuration on A* with heuristic function f(n) = g(n) + h(n), where h(n) is number of misplaced tiles:");
							AStarFn asfWorst = new AStarFn(worstConf, goalConf);
							asfWorst.runAStar();
							break;
						}	
					}
					break;
				}
				case '2':{
					System.out.println("Please select the difficulty of the puzzle to solven: \n1. Easy \n2. Medium \n3. Hard \n4. Worst");
					switch(input.next().charAt(0)){
						case '1':{
							System.out.println("Testing easy configuration on A* with Manhattan function: ");
							AStarManhattan asmEasy = new AStarManhattan(easyConf, goalConf);
							asmEasy.runAStar();
							break;
						}
						case '2':{
							System.out.println("\n\nTesting medium configuration on A* with Manhattan function: ");
							AStarManhattan asmMedium = new AStarManhattan(mediumConf, goalConf);
							asmMedium.runAStar();
							break;
						}
						case '3':{
							System.out.println("\n\nTesting hard configuration on A* with Manhattan function: ");
							AStarManhattan asmHard = new AStarManhattan(hardConf, goalConf);
							asmHard.runAStar();
							break;
						}
						case '4':{
							System.out.println("\n\nTesting worst configuration on A* with Manhattan function: ");
							AStarManhattan asmWorst = new AStarManhattan(worstConf, goalConf);
							asmWorst.runAStar();
							break;
						}	
					}
					
					break;
				}
				
				case '3':{
					System.out.println("Please select the difficulty of the puzzle to solven: \n1. Easy \n2. Medium \n3. Hard \n4. Worst");
					switch(input.next().charAt(0)){
						case '1':{
							System.out.println("Testing easy configuration on Iterative Deepening A* Search with Manhattan heuristic function: ");
							IDAStar IDASEasy = new IDAStar(easyConf, goalConf);
							IDASEasy.runIDAS();
							break;
						}
						case '2':{
							System.out.println("Testing medium configuration on Iterative Deepening A* Search with Manhattan heuristic function: ");
							IDAStar IDASMedium = new IDAStar(mediumConf, goalConf);
							IDASMedium.runIDAS();
							break;
						}
						case '3':{
							System.out.println("Testing hard configuration on Iterative Deepening A* Search with Manhattan heuristic function: ");
							IDAStar IDASHard = new IDAStar(hardConf, goalConf);
							IDASHard.runIDAS();
							break;
						}
						case '4':{
							System.out.println("Testing worst configuration on Iterative Deepening A* Search with Manhattan heuristic function: ");
							IDAStar IDASWorst = new IDAStar(worstConf, goalConf);
							IDASWorst.runIDAS();
							break;
						}	
					}
					
					break;
				}
				
				case '4':{
					System.out.println("Please select the difficulty of the puzzle to solven: \n1. Easy \n2. Medium \n3. Hard \n4. Worst");
					switch(input.next().charAt(0)){
						case '1':{
							System.out.println("Testing easy configuration on Depth First Branch and Bound Search with Manhattan heuristic function: ");
							DepthFirstBranchAndBound DFBnBEasy = new DepthFirstBranchAndBound(easyConf, goalConf);
							DFBnBEasy.runDFBnB();
							break;
						}
						case '2':{
							System.out.println("Testing medium configuration on Depth First Branch and Bound Search with Manhattan heuristic function: ");
							DepthFirstBranchAndBound DFBnBMedium = new DepthFirstBranchAndBound(mediumConf, goalConf);
							DFBnBMedium.runDFBnB();
							break;
						}
						case '3':{
							System.out.println("Testing hard configuration on Depth First Branch and Bound Search with Manhattan heuristic function: ");
							DepthFirstBranchAndBound DFBnBHard = new DepthFirstBranchAndBound(hardConf, goalConf);
							DFBnBHard.runDFBnB();
							break;
						}
						case '4':{
							System.out.println("Testing worst configuration on Depth First Branch and Bound Search with Manhattan heuristic function: ");
							DepthFirstBranchAndBound DFBnBWorst = new DepthFirstBranchAndBound(worstConf, goalConf);
							DFBnBWorst.runDFBnB();
							break;
						}	
					}
					
					break;
				}

				case '0':{
					runProgram = false;			
				}	
		
			}
		}
		//Close scanner to avoid memory leaks
		input.close();
		System.out.println("Program Terminated. Good bye.");
	}

}
