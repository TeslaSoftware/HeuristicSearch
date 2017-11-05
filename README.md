# Heuristic-Search
In this project I have implemented and analyzed the different heuristic search algorithms to solve the 8-puzzle. 8-puzzle is a sliding puzzle on 3 x 3 board with one blank tile and remaining 8 tiles numbered from 1 to 8. Only empty tile can be moved different position. The objective is to place the tiles to achieve the goal configuration. In this program goal configuration is presented below:
-----------
| 1 |2 |3 |
-----------
| 8 |0 |4 |
-----------
| 7 |6 |5 |
-----------
To find optimal solution I have implemented the following heuristic search algorithms:

A* search using the heuristic function f_((n))=g_((n))+h_((n)) where h_((n))  is the number of misplaced tiles (not counting the blank).
A* search with the Manhattan heuristic function as〖 h〗_((n)).
Iterative deepening A* Search with the Manhattan heuristic function as〖 h〗_((n)).
Depth-first Branch and Bound with the Manhattan heuristic function as〖 h〗_((n)).

All of the algorithms except IDA* checking for duplicates and remove them if their cost is lower. 
Performance of each algorithm was measured in terms of expanded nodes, number of moves to find the optimal solution and time it took to find optimal solution and finish the algorithm (in nano seconds). All of this was performed on 4 different types of configuration, from easy, through medium, hard and the worst. The initial states are as follows.

EASY
-----------
| 1 |3 |4 |
-----------
| 8 |6 |2 |
-----------
| 7 |0 |5 |
-----------

MEDIUM
-----------
| 2 |8 |1 |
-----------
| 0 |4 |3 |
-----------
| 7 |6 |5 |
-----------

HARD
-----------
| 2 |8 |1 |
-----------
| 4 |6 |3 |
-----------
| 0 |7 |5 |
-----------

WORST
-----------
| 5 |6 |7 |
-----------
| 4 |0 |8 |
-----------
| 3 |2 |1 |
-----------

Methods:
As a data structure to store open and closed list I have used either Lined List or Priority Queue. For A* priority queue was used to store elements in open list in their ascending order of f(n) and lined list to store closed list. For IDA*, priority queue was used to store elements in closed list ordered by number of children each one has (node with no children go on the front). Linked list was used for open list, with insertions up front. For DFBnB both open and closed list are designed as lined lists, where to open list elements are added after each expansion of node according to their f(n) value on the front of the list. I encountered several problems with implementation of my algorithms, however by careful examination I was able to resolve it.

