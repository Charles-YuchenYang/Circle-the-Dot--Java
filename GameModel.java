/**
 * The class <b>GameModel</b> holds the model, the state of the systems.
 * It stores the followiung information:
 * - the current location of the blue dot
 * - the state of all the dots on the board (available, selected or
 *  occupied by the blue dot
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough
 *  appropriate Getters.
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {


    /**
     * predefined values to capture the state of a point
     */
    public static final int AVAILABLE 	= 0;
    public static final int SELECTED 	= 1;
    public static final int DOT 		= 2;

// ADD YOUR INSTANCE VARIABLES HERE
    private int [][] states;
    private int modelSize;


	int steps;
    private Point currentDot;



    private Point nextDot;

    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param size
     *            the size of the board
     */
    public GameModel(int size) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        steps=0;
        modelSize=size;

        //initialize all the dots to be available first
        states = new int [size][size];
        for(int i=0; i<size; i++)
        {
            for(int j=0; j<size; j++)
            {
                states[i][j] = AVAILABLE;
            }
        }
    }


    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . The blue dot is positioned as per instructions, and each
     * dot of the board is either AVAILABLE, or SELECTED (with
     * a probability 1/INITIAL_PROBA). The number of steps is reset.
     */
    public void reset(){

        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        for(int i=0; i<modelSize; i++)
        {
            for(int j=0; j<modelSize; j++)
            {
                states[i][j] = AVAILABLE;
            }
        }
    }


    /**
     * Getter <b>class</b> method for the size of the game
     *
     * @return the value of the attribute sizeOfGame
     */
    public  int getSize(){
        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return modelSize;
    }

    /**
     * returns the current status (AVAILABLE, SELECTED or DOT) of a given dot in the game
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public int getCurrentStatus(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return states[i][j];
    }


    /**
     * Sets the status of the dot at coordinate (i,j) to SELECTED, and
     * increases the number of steps by one
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void select(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        states[i][j] = SELECTED;
        steps++;
    }

    /**
     * Puts the blue dot at coordinate (i,j). Clears the previous location
     * of the blue dot. If the i coordinate is "-1", it means that the blue
     * dot exits the board (the player lost)
     *
     * @param i
     *            the new x coordinate of the blue dot
     * @param j
     *            the new y coordinate of the blue dot
     */
    public void setCurrentDot(int i, int j){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
    	if(currentDot != null)
    	{
    		states[currentDot.getX()][currentDot.getY()] = AVAILABLE;
            currentDot.reset(i, j);
    	}
    	else
    	{
    		currentDot = new Point(i, j);
    	}
    	
        states[i][j] = DOT;
    }

    /**
     * Getter method for the current blue dot
     *
     * @return the location of the curent blue dot
     */
    public Point getCurrentDot(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return currentDot;
    }

    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return steps;
    }

    public void setNumberOfSteps(int aInSteps)
    {
        steps = aInSteps;
    }

    public Point getNextDot()
    {
        return nextDot;
    }

    public void setNextDot(int aInX, int aInY)
    {
        nextDot = new Point (aInX, aInY);
    }
}
