import java.util.Objects;

/**
 * Source code that models an n-by-n board with sliding tiles.
 * Your heuristic function resides in this file.
 */
public class Board {
    private int myNumCreated;
    private int myNumExpanded;
    private int myMaxFringe;
    private final int mySize;
    private final String myBoard;
    private final String myGoalState;
    private final String myInitialState;
    private final String mySearchMethod;


    public Board(int theSize, String theBoard, String theSearchMethod, String theInitialState){
        myBoard = theBoard;
        myGoalState = getGoalState(theSize);
        mySize = theSize;
        mySearchMethod = theSearchMethod;
        myInitialState = theInitialState;
        myNumCreated = 0;
        myNumExpanded = 0;
        myMaxFringe = 0;
    }

    /**
     * @param theSize the size of the board
     * @return the final goal state of the board of the size
     */
    public static String getGoalState(int theSize){
        if(theSize == 2){
            return "213 ";
        }else if(theSize == 3){
            return " 12345678";
        }else if(theSize == 4){
            return "123456789ABCDEF ";
        }else{
            return null;
        }
    }

    public boolean solutionFound(String theState){
        return Objects.equals(theState, myGoalState);
    }


    /**
     * the Manhattan Distance is calculated by adding up how many spots each tile is from where they should be in the goal state
     * @return an integer that represents the Manhattan Distance the state is in from the goal state
     */
    public int heuristic(String theState){
        int  manhattanDistance = 0;
        int correctPos;

        for(int i = 0; i<theState.length();i++){
            correctPos = myGoalState.indexOf(theState.charAt(i));  //finding the correct position of this character
            manhattanDistance += Math.abs(correctPos - i); //adding the difference to the manhattan distance
        }

        return manhattanDistance;
    }




    public int getMaxFringe() {
        return myMaxFringe;
    }
    public void setMaxFringe(int theMaxFringe) {
        myMaxFringe = theMaxFringe;
    }
    public int getNumExpanded() {
        return myNumExpanded;
    }
    public void setNumExpanded(int theNumExpanded) {
        myNumExpanded = theNumExpanded;
    }
    public int getNumCreated() {
        return myNumCreated;
    }
    public void setNumCreated(int theNumCreated) {
        myNumCreated = theNumCreated;
    }
    public String getMyBoard() {
        return myBoard;
    }
    public int getMySize() {
        return mySize;
    }
    public String getMyInitialState() {return myInitialState;}
    public String getMyGoalState() {return myGoalState;}
    public String getMySearchMethod() {return mySearchMethod;}
}

class Node implements Comparable<Node>{
    Node myParent;
    String myState;
    int myHeuristic;
    int myDepth;

    public Node(String theState, Node theParent, int theHeuristic){
        myState = theState;
        myParent = theParent;
        myHeuristic = theHeuristic;
        try {
            myDepth = myParent.myDepth + 1;
        }catch (NullPointerException e){
            myDepth = 0;
        }
    }

    /**
     * sorting the nodes by their heuristics
     */
    public int compareTo(Node theNode){
        return Integer.compare(myHeuristic, theNode.myHeuristic);
    }

    public int getDepth() {
        return myDepth;
    }
}

