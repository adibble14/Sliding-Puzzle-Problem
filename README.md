# Sliding-Puzzle-Problem

Expressing the problem as a search problem and identifying proper solving methods. Specifying, 
designing and implementing uninformed & informed search methods

In this programming assignment you will implement a set of search algorithms (BFS, DFS, Greedy, 
A*) to find solution to the sliding puzzle problem

Let’s start by defining the sliding puzzle problem:
For a given puzzle of n x n squares with numbers from 1 to (n x n-1) (one square is empty) in an 
initial configuration, find a sequence of movements for the numbers in order to reach a final given 
configuration, knowing that a number can move (horizontally or vertically) on an adjacent empty 
square. 
You will solve the puzzle for size n = 2 (2 x 2 squares), 3 (3 x 3 squares) and 4 (4 x 4 squares). 
The final configuration/goal state for each puzzle of size n is as follows:
2x2: 2,1,3," "     3x3: " ",1,2,3,4,5,6,7,8       4x4: 1,2,3,4,5,6,7,8,9,A,B,C,D,E,F," "

Your program will accept instructions from the command line.
Your program will generate 2 different outputs – one to the console & and another to a readme
file.
