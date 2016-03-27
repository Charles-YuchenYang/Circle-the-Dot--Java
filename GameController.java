import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

/**
 * The class <b>GameController</b> is the controller of the game. It implements
 * the interface ActionListener to be called back when the player makes a move. It computes
 * the next step of the game, and then updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener
{
    private static final int WIN=0;
    private static final int LOOSE=1;
    private static final int MOVE=2;

    // ADD YOUR INSTANCE VARIABLES HERE
    GameView gameView;
    GameModel gameModel;
    Vertex[][] gameVertices;


    Random random = new Random();
    final int orangePercentage = 10; //10%
    
    int modelSize = 0;
    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION

        this.gameModel=new GameModel(size);
        this.gameView=new GameView(gameModel, this);
        modelSize = size;
        gameVertices=new Vertex[modelSize][modelSize];
        
    }


    //TODO: based on size's odd/even
    private void initializeBlueDot()
    {
        //hard coded initialize location
        int x=modelSize/2 + (random.nextInt(modelSize/3) - modelSize/6);
        int y=modelSize/2 + (random.nextInt(modelSize/3) - modelSize/6);

        gameModel.setCurrentDot(x, y);
        gameModel.setNextDot(x, y);

    }

    //TODO: 10 perent of the board should have orange dot
    private void initializeOrangeDots()
    {
    	int num = modelSize*modelSize*orangePercentage/100;
    	
    	for (int i = 0; i < num; i++)
    	{
    		int x = random.nextInt(modelSize);
    		int y = random.nextInt(modelSize);
    		
    		gameModel.select(x, y);
    	}
        gameModel.setNumberOfSteps(gameModel.getNumberOfSteps() - num);
    	
    }
    
    /**
     * Starts the game
     */
    public void start(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
    	initializeOrangeDots();
    	initializeBlueDot();
    	gameView.update();
    }


    /**
     * resets the game
     */
    public void reset(){
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
		gameModel.reset();
		start();
    }

    /**
     * Callback used when the user clicks a button or one of the dots.
     * Implements the logic of the game
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
    	switch (e.getActionCommand())
    	{
    	case "click":
	    	DotButton b = (DotButton) e.getSource();
	    	gameModel.select(b.getRow(), b.getColumn());
            int result= blueDotMove();

            //Debugging purpose
            printVertices();

            processResult(result);
            gameView.update();
            break;
    		
    	case "reset":
    		reset();
    		break;
    		
    	case "quit":
    		System.exit(0);
    		break;
    		
    		
    	default:
    	}
    }


    /*
    1. move the blue dot by using breadth-first search and based on the current state() of the board
    2. state means:
        2.1 current blue dot position
        2.2 blockedList
    3 this method will also trigger the JOptionPanel indicating the user's win/loose
        3,1 win: blue dot has no path to target
        3.3 loose: blue dot's position equals one of the targets
    4.return value indicating the result of the move
        4.1 WIN
        4.2 LOOSE
        4.3 MOVE
    */
    private int blueDotMove()
    {
        Point lNextStep=breadthFirstSearhNextBlueDotStep(gameModel.getCurrentDot());
        if(noPathToBondary(gameVertices))
        {
            return WIN;
        }
        if(lNextStep!=null)
        {
            gameModel.setNextDot(lNextStep.getX(), lNextStep.getY());
            if(atBoundary(gameModel.getCurrentDot()))
            {

                return LOOSE;
            }
            return MOVE;
        }
        /*
        need to refine the logic of winning:
        based on the current state of the gameModel
        if the
         */

        return MOVE;
    }

    private boolean noPathToBondary(Vertex[][] aInVertices)
    {
        if(aInVertices!=null)
        {
            for(Vertex[] lVertexRow: aInVertices)
            {
                for(Vertex lVertex: lVertexRow)
                {
                    Point lVertexPosition=lVertex.getPosition();
                    if(atBoundary(lVertexPosition))
                    {
                        if(lVertex.getDistance()!=Integer.MAX_VALUE)
                        {
                            //if there exist a boundary position that the current dot can get to with finite distance
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private boolean atBoundary(Point aInPosition)
    {
        int x=aInPosition.getX();
        int y=aInPosition.getY();
        if(0==x||0==y||modelSize-1==x||modelSize-1==y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    Return the next position that the blue dot should move to
     */
    private Point breadthFirstSearhNextBlueDotStep(Point aInCurrentPoint)
    {
        Point lSourcePosition=aInCurrentPoint;
        LinkedList<Vertex> queue=new LinkedList<>();
        Vertex[][] lVertices=new Vertex[modelSize][modelSize];

        //initialize the topology
        for(int i=0; i< modelSize; i++)
        {
            for(int j=0; j<modelSize; j++)
            {
                lVertices[i][j] = new Vertex(new Point(i, j));
            }
        }

        Vertex source=lVertices[lSourcePosition.getX()][lSourcePosition.getY()];
        source.setDistance(0);
        queue.addLast(source);

        while(!queue.isEmpty())
        {
            Vertex lCurrentVertex=queue.removeFirst();

            List<Point> lAvaliableNeighbourPositions=getAvailableNeighbourPositions(lCurrentVertex);

            for(Point lAvaliableNeighbourPosition: lAvaliableNeighbourPositions)
            {
                int lANPX=lAvaliableNeighbourPosition.getX();
                int lANPY=lAvaliableNeighbourPosition.getY();
                Vertex lNeighbourVertex=lVertices[lANPX][lANPY];
                if(lNeighbourVertex.getDistance()==Integer.MAX_VALUE)
                {
                    lNeighbourVertex.setDistance(lCurrentVertex.getDistance()+1);
                    lNeighbourVertex.setPrev(lCurrentVertex);
                    queue.addLast(lNeighbourVertex);
                }
            }
        }

        //logging the vertices(routing) informatiion into the controller
        gameVertices=lVertices;
        //return a random position with distance 1
        return ramdomChoose(lVertices);
    }

    private Point ramdomChoose(Vertex[][] aInVertices)
    {
        List<Point> lCandidatePositions=new ArrayList<>(6);
        List<Vertex> lCandidateBoundaryVertices=new ArrayList<>(modelSize*4);

        int minStepsToBondary=minStepsFromDotToBoundary(aInVertices);

        //find candidates boundary positions depending on the minSteps from source to boundary
        for(Vertex[] lVertexRow: aInVertices)
        {
            for(Vertex lVertex: lVertexRow)
            {
                Point lVertexPosition=lVertex.getPosition();
                if(atBoundary(lVertexPosition))
                {
                    if(lVertex.getDistance()==minStepsToBondary)
                    {
                        lCandidateBoundaryVertices.add(lVertex);
                    }
                }
            }
        }

        if(lCandidateBoundaryVertices!=null && !lCandidateBoundaryVertices.isEmpty())
        {
            for(Vertex lCandidateBoundaryVertex: lCandidateBoundaryVertices)
            {
                //iterate from boundary back to the source, at the last two steps, we get the desired "next step"
                Vertex sourceNextStep=lCandidateBoundaryVertex;
                for(int i=minStepsToBondary-1;i>0; i--)
                {
                    if(sourceNextStep!=null)
                    {
                        sourceNextStep=sourceNextStep.getPrev();
                    }
                    else
                    {
                        return null;
                    }
                }
                lCandidatePositions.add(sourceNextStep.getPosition());
            }
        }

        Random lRandom=new Random();
        if(lCandidatePositions!=null)
        {
            int lCandidatePositionNum=lCandidatePositions.size();
            if(lCandidatePositionNum!=0)
            {
                Point lPotentialPosition=lCandidatePositions.get(lRandom.nextInt(lCandidatePositionNum));
                return lPotentialPosition;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }


    private int minStepsFromDotToBoundary(Vertex[][] aInVertices)
    {
        int minStepsToBondary=Integer.MAX_VALUE;
        int lCurrentVertexDistance;

        for(int i=0; i<modelSize-1; i++)
        {
            int j=0;
            lCurrentVertexDistance=aInVertices[i][j].getDistance();
            if(lCurrentVertexDistance<minStepsToBondary)
            {
                minStepsToBondary=lCurrentVertexDistance;
            }
            j=modelSize-1;
            lCurrentVertexDistance=aInVertices[i][j].getDistance();
            if(lCurrentVertexDistance<minStepsToBondary)
            {
                minStepsToBondary=lCurrentVertexDistance;
            }
        }
        for(int j=0; j<modelSize-1; j++)
        {
            int i = 0;
            lCurrentVertexDistance = aInVertices[i][j].getDistance();
            if (lCurrentVertexDistance < minStepsToBondary)
            {
                minStepsToBondary = lCurrentVertexDistance;
            }
            i = modelSize-1;
            lCurrentVertexDistance = aInVertices[i][j].getDistance();
            if (lCurrentVertexDistance < minStepsToBondary)
            {
                minStepsToBondary = lCurrentVertexDistance;
            }
        }

        return minStepsToBondary;

    }


    private List<Point> getAvailableNeighbourPositions(Vertex aInSource)
    {
        List<Point> lAvailableNeighbourPositions=new LinkedList<>();
        List<Point> lNeighbourPositions = getNeighbourPositions(aInSource);

        for(Point lNeighbourPosition: lNeighbourPositions)
        {
            int lNeighbourX=lNeighbourPosition.getX();
            int lNeighbourY=lNeighbourPosition.getY();
            if(GameModel.AVAILABLE==gameModel.getCurrentStatus(lNeighbourX, lNeighbourY))
            {
                lAvailableNeighbourPositions.add(lNeighbourPosition);
            }
        }

        return lAvailableNeighbourPositions;
    }

    /*
    base on the topology(the odd line's are shifted a little to the right) of the board
     if the source's coordinate is [x][y]
     it has 6 possible neighbours:

     if x is even:
            [x-1][y-1], [x-1][y],

         [x][y-1],  ([x][y])   [x][y+1],

            [x+1][y-1], [x+1][y]

     if x is odd:
            [x-1][y], [x-1][y+1],

         [x][y-1],  ([x][y])   [x][y+1],

            [x+1][y], [x+1][y+1]
     */
    private List<Point> getNeighbourPositions(Vertex aInSource)
    {
        List<Point> lNeighbourPositions=new LinkedList<>();

        if(aInSource!=null)
        {
            Point lSourcePosition=aInSource.getPosition();
            int x=lSourcePosition.getX();
            int y=lSourcePosition.getY();
            List<Point> lPotentialNeighbourPositions=new ArrayList<>(6);
            if(x%2==0){
                lPotentialNeighbourPositions.add(new Point(x-1, y-1));
                lPotentialNeighbourPositions.add(new Point(x-1, y));
                lPotentialNeighbourPositions.add(new Point(x, y-1));
                lPotentialNeighbourPositions.add(new Point(x, y+1));
                lPotentialNeighbourPositions.add(new Point(x+1, y-1));
                lPotentialNeighbourPositions.add(new Point(x+1, y));
            }
            else
            {
                lPotentialNeighbourPositions.add(new Point(x-1, y));
                lPotentialNeighbourPositions.add(new Point(x-1, y+1));
                lPotentialNeighbourPositions.add(new Point(x, y-1));
                lPotentialNeighbourPositions.add(new Point(x, y+1));
                lPotentialNeighbourPositions.add(new Point(x+1, y));
                lPotentialNeighbourPositions.add(new Point(x+1, y+1));
            }

            for(Point lPotentialNeighbourPosition: lPotentialNeighbourPositions)
            {
                if(withinBoard(lPotentialNeighbourPosition))
                {
                    lNeighbourPositions.add(lPotentialNeighbourPosition);
                }
            }
        }
        return lNeighbourPositions;
    }

    private boolean withinBoard(Point aInPoint)
    {
        int x=aInPoint.getX();
        int y=aInPoint.getY();
        if(x>=0 && x<modelSize && y>=0 && y<modelSize)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void processResult(int aInResult)
    {
        Point lNextDot=gameModel.getNextDot();
        if(lNextDot!=null)
        {
            int lNextDotX=lNextDot.getX();
            int lNextDotY=lNextDot.getY();
            gameModel.setCurrentDot(lNextDotX, lNextDotY);
        }


        String[] lOptions={"Quit", "Play Again"};;
        String lMessage;
        String lPaneTitle;
        String lDefaultOption = lOptions[1];//set "Play Again" to be the default option
        int lResult=-1;

        switch(aInResult)
        {
            case WIN:
                           /*
           win, pop JOptionPanel
           here is logic is not correct,
           I specify the winning situation to be no nextstep
           but the requirement should be not path to any target
            */
                lMessage="Gongratulations, you won in "+ gameModel.getNumberOfSteps()+" steps!\n"+"Would you like to play again?";
                lPaneTitle="Lost";
                lResult=JOptionPane.showOptionDialog(null, lMessage, lPaneTitle, 0, JOptionPane.QUESTION_MESSAGE, null, lOptions, lDefaultOption);
                break;
            case LOOSE:
                //loose, pop JOptionPanel
                 lMessage="You lost! Would you like to play again?";
                 lPaneTitle="Lost";
                 lResult= JOptionPane.showOptionDialog(null, lMessage, lPaneTitle, 0, JOptionPane.QUESTION_MESSAGE, null, lOptions, lDefaultOption);
                break;
            case MOVE:

                break;
        }

        if(lResult==0)
        {
            System.exit(0);
        }
        if(lResult==1)
        {
            reset();
        }

    }



    /**
     * Used by Breadth First Search,
     *
     */
    private class Vertex
    {
        private int distance;//distance is needed for we sometime need the exact distance
        private Point position;
        private Vertex prev;

        public Vertex getPrev()
        {
            return prev;
        }

        public void setPrev(Vertex aInNext)
        {
            prev = aInNext;
        }

        public boolean hasPrev()
        {
            return prev==null;
        }


        public Vertex(Point aInPosition)
        {
            this.position=aInPosition;
            distance = Integer.MAX_VALUE;

        }

        public int getDistance()
        {
            return distance;
        }

        public void setDistance(int aInDistance)
        {
            distance = aInDistance;
        }

        public Point getPosition()
        {
            return position;
        }

    }

    //debugging util method
    public void printVertices()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("Step").append(gameModel.getNumberOfSteps()).append(" Vertices: \n");
        for(int i=0; i<modelSize; i++)
        {
            if(i%2==1)
            {
                sb.append("    ");
            }
            for(int j=0; j<modelSize; j++)
            {
                int lDistance=gameVertices[i][j].getDistance();
                if(lDistance==Integer.MAX_VALUE)
                {
                    lDistance=-1;
                }
                sb.append("[").append(lDistance).append("], ");
            }
            sb.append("\n\n");

        }

        System.out.println(sb.toString());
    }
}
