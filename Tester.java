import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A tester file that connects your Board and Solver code.
 */
public class Tester {

    private static int mySize;
    private static String myInitialState;
    private static String mySearchMethod;

    public static void main(String[] args){

        mySize = Integer.parseInt(args[0]);
        myInitialState = args[1];
        mySearchMethod = args[2];
        /*mySize = 2;
        myInitialState = "321 ";
        mySearchMethod = "DFS";*/

        if(isSolvable()){
            //there is a solution
            System.out.println("There is a solution");
            Board myBoard = new Board(mySize, myInitialState, mySearchMethod, myInitialState);
            new Solver(myBoard, mySearchMethod);
        }else{
            //there is no solution
            System.out.println("There is no solution");
            outputNoSolution(new Board(mySize, myInitialState, mySearchMethod, myInitialState));
        }

    }

    /**
     * @return true if the board is solvable, false otherwise
     */
    public static boolean isSolvable(){
        //checking if it is an odd or even sized board
        if(getSize() % 2 == 0){
            // Even-sized boards: is solvable if and only if the number of inversions
            //plus the row of the blank square is odd.
            return (numInversions() + rowOfBlankSquare()) / 2 != 0;
        }else{
            //Odd-sized boards: If a board has an odd number of inversions, it is unsolvable
            return numInversions() % 2 == 0;
        }
    }

    /**
     * @return the number of inversions in the board
     * an inversion is any pair of tiles i and j where i < j but i appears
     * after j when considering the board in row-major order
     */
    public static int numInversions(){
        String str = getInitialState().replaceAll("\\s", ""); //getting initial state with no whitespace
        int count = 0; //number of inversions
        for(int i = 0; i<str.length(); i++){
            for(int j = i+1; j<str.length(); j++){
                if(str.charAt(i) > str.charAt(j)){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return the row number of where the blank square is located
     */
    public static int rowOfBlankSquare(){
        int row = -1;
        int size = getSize();
        String str = getInitialState();

        for(int i = 0; i<str.length(); i++){
            if(i%size == 0){
                row++;
            }
            if(str.charAt(i) == 32){
                return row;
            }
        }

        return row;
    }

    /**
     * creates the solution path once a solution has been found, outputs data to a file called "Readme.txt"
     */
    public static void output(Board theBoard, Node theRoot){
        Node temp = theRoot;
        System.out.println("Goal State");
        System.out.println("\""+temp.myState+"\"");
        while(temp.myParent != null){
            System.out.println("\""+temp.myParent.myState+"\"");
            temp = temp.myParent;
        }
        System.out.println("Initial State");

        try {
            //outputting to a file
            FileWriter myWriter = new FileWriter("Readme.txt");
            BufferedWriter bw = new BufferedWriter(myWriter);
            outputToFile(bw, theBoard);
            bw.write(theRoot.getDepth() +", "+ theBoard.getNumCreated()+", " + theBoard.getNumExpanded() +", "+ theBoard.getMaxFringe());
            bw.newLine();
            bw.write("-------------------------------------");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * output if there is no solution
     */
    public static void outputNoSolution(Board theBoard){
        try {
            //outputting to a file
            FileWriter myWriter = new FileWriter("Readme.txt");
            BufferedWriter bw = new BufferedWriter(myWriter);
            outputToFile(bw, theBoard);
            bw.write("-1, 0, 0, 0");
            bw.newLine();
            bw.write("-------------------------------------");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * common code between all outputs to file
     */
   public static void outputToFile(BufferedWriter bw, Board theBoard) throws IOException {
       bw.write("size: "+ theBoard.getMySize());
       bw.newLine();
       bw.write( "initial: \"" + theBoard.getMyInitialState() + "\"");
       bw.newLine();
       bw.write("goal: \"" + theBoard.getMyGoalState() + "\"");
       bw.newLine();
       bw.write("search method: " + theBoard.getMySearchMethod());
       bw.newLine();
    }


    public static int getSize() {
        return mySize;
    }

    public static String getInitialState() {
        return myInitialState;
    }

    public static String getSearchMethod() {
        return mySearchMethod;
    }
}
