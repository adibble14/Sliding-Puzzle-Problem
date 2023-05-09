import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Source code that implements BFS, DFS, GBFS, AStar to
 * solve n-by-n sliding puzzle.
 */
public class Solver {

    private static ArrayList<Node> myFringe;
    private static String currentState;
    private static int positionBlank;
    private final Board myBoard;
    private static int mySize;
    private static Node root;

    public Solver(Board theBoard, String theSearchMethod) {
        myBoard = theBoard;
        mySize = myBoard.getMySize();
        currentState = theBoard.getMyBoard();
        myFringe = new ArrayList<>();

        //determining which algorithm to use
        switch (theSearchMethod) {
            case "BFS" -> bfs();
            case "DFS" -> dfs();
            case "GBFS" -> gbfs();
            case "AStar" -> aStar();
        }
        Tester.output(myBoard, root);
    }

    public void bfs() {
        myFringe.add(new Node(currentState, root, -1)); //adding the current state to the fringe
        myBoard.setMaxFringe(myBoard.getMaxFringe() + 1);

        boolean solution;
        while (myFringe.size() > 0) {
            //Fringe is acting like a queue
            solution = expansion(myFringe.remove(0)); //dequeue from fringe and expand
            if (solution) return; //checking if done
            generation(); //generating children and adding them to fringe
        }
    }

    public void dfs() {
        myFringe.add(new Node(currentState, root, -1)); //adding the current state to the fringe
        myBoard.setMaxFringe(myBoard.getMaxFringe() + 1);

        boolean solution;
        while (myFringe.size() > 0) {
            //Fringe is acting like a stack
            solution = expansion(myFringe.remove(myFringe.size() - 1)); //pop from fringe and expand
            if (solution) return; //checking if done
            generation(); //generating children and adding them to fringe
        }
    }

    public void gbfs() {
        myFringe.add(new Node(currentState, root, myBoard.heuristic(currentState))); //adding the current state to the fringe
        myBoard.setMaxFringe(myBoard.getMaxFringe() + 1);

        boolean solution;
        while (myFringe.size() > 0) {
            //Fringe is acting like a priority queue
            solution = expansion(myFringe.remove(0)); //dequeue from fringe and expand
            if (solution) return; //checking if done
            generation(); //generating children and adding them to fringe
        }
    }

    public void aStar() {
        myFringe.add(new Node(currentState, root, myBoard.heuristic(currentState) + 1)); //adding the current state to the fringe, true cost is 1
        myBoard.setMaxFringe(myBoard.getMaxFringe() + 1);

        boolean solution;
        while (myFringe.size() > 0) {
            //Fringe is acting like a priority queue
            solution = expansion(myFringe.remove(0)); //dequeue from fringe and expand
            if (solution) return; //checking if done
            generation(); //generating children and adding them to fringe
        }
    }

    /**
     * generates possible states to be added to the fringe
     */
    public void generation() {
        positionBlank = currentState.indexOf(" ");  //getting position of the blank
        if ((positionBlank % mySize) != 0) {  //the blank spot can move left
            swapTiles(-1);
        }
        if (((positionBlank + 1) % mySize) != 0) { //the blank spot can move right
            swapTiles(1);
        }
        if ((mySize - positionBlank) <= 0) {//the blank spot can move up
            swapTiles(-mySize);
        }
        if ((positionBlank - ((mySize * mySize) - mySize)) < 0) {//the blank spot can move down
            swapTiles(mySize);
        }
    }

    /**
     * swaps the blank spot with the appropriate character, adds it to fringe if the state doesn't already exist
     */
    public void swapTiles(int theNum) {
        //swapping the blank spot accordingly
        String state = currentState;
        char[] chars = state.toCharArray();
        char temp = chars[positionBlank];
        chars[positionBlank] = chars[positionBlank + theNum];
        chars[positionBlank + theNum] = temp;
        //turn the char array into string
        state = new String(chars);

        //checking to see if we need to calculate heuristics
        if (Objects.equals(Tester.getSearchMethod(), "GBFS") || Objects.equals(Tester.getSearchMethod(), "AStar")) {
            if (!stateExists(state))
                myFringe.add(new Node(state, root, myBoard.heuristic(state))); //adding to fringe if state does not already exist in parent path
            Collections.sort(myFringe);  //sorting the fringe by the heuristics
        } else {
            if (!stateExists(state))
                myFringe.add(new Node(state, root, -1)); //adding to fringe if state does not already exist in parent path
        }

        if (myBoard.getMaxFringe() < myFringe.size()) {
            myBoard.setMaxFringe(myBoard.getMaxFringe() + 1);
        }

        myBoard.setNumCreated(myBoard.getNumCreated() + 1);
    }

    /**
     * checks if the state already exists in the parent path of the node,
     * so we don't have a circular path
     */
    public boolean stateExists(String theState) {
        Node temp = root;
        while (temp.myParent != null) {
            if (Objects.equals(temp.myParent.myState, theState)) {
                return true;
            }
            temp = temp.myParent;
        }
        return false;
    }

    /**
     * expands the given node and checks to see if the solution is found
     */
    public boolean expansion(Node theNode) {
        currentState = theNode.myState;
        positionBlank = currentState.indexOf(" ");
        root = theNode; //update the value of root
        myBoard.setNumExpanded(myBoard.getNumExpanded() + 1);
        return myBoard.solutionFound(currentState);
    }

}