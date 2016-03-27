import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * The class <b>BoardView</b> provides the current view of the board. It extends
 * <b>JPanel</b> and lays out a two dimensional array of <b>DotButton</b> instances.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class BoardView extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//constants
    private static final int ROW_SHIFT_AMOUNT=40;
    private static final int BOARD_VIEW_WIDTH_SCALE=45;
    private static final int BOARD_VIEW_HEIGHT_SCALE=45;

    // ADD YOUR INSTANCE VARIABLES HERE
    GameModel gameModel;
    GameController gameController;
    int modelSize;

    JPanel[] dotButtonRows;
    DotButton[][] dotButtons;

    GridLayout boardViewLayout;
    FlowLayout dotButtonRowsLayout;


    /**
     * Constructor used for initializing the board. The action listener for
     * the board's DotButton is the game controller
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public BoardView(GameModel gameModel, GameController gameController) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        super();

        this.gameModel=gameModel;
        this.gameController=gameController;
        this.modelSize=gameModel.getSize();
        this.setSize(modelSize*BOARD_VIEW_WIDTH_SCALE+90, modelSize*BOARD_VIEW_HEIGHT_SCALE+90);

        this.dotButtonRows=new JPanel[modelSize];
        this.dotButtons=new DotButton[modelSize][modelSize];

        boardViewLayout=new GridLayout(modelSize, 1);
        this.setLayout(boardViewLayout);
        dotButtonRowsLayout=new FlowLayout();
        
        for(int lRow=0; lRow<modelSize; lRow++)
        {
            dotButtonRows[lRow]=new JPanel(dotButtonRowsLayout);
            if(lRow%2==1)
            {
                dotButtonRows[lRow].setBorder(BorderFactory.createEmptyBorder(0, ROW_SHIFT_AMOUNT, 0, 0));
            }
            for(int lColumn=0; lColumn<modelSize; lColumn++)
            {
            	DotButton b = new DotButton(lRow, lColumn, DotButton.AVAILABLE_DOT_BUTTON);
            	b.setActionCommand("click");
            	b.addActionListener(gameController);
                dotButtons[lRow][lColumn]=b;
                dotButtons[lRow][lColumn].setBorder(BorderFactory.createEmptyBorder());
                dotButtonRows[lRow].add(dotButtons[lRow][lColumn]);
            }
            add(dotButtonRows[lRow]);
        }
    }

    /**
     * update the status of the board's DotButton instances based on the current game model
     */

    public void update(){
        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        for(int i=0; i<modelSize; i++)
        {
            for(int j=0; j<modelSize; j++)
            {
            	DotButton b = dotButtons[i][j];
            	b.setType(gameModel.getCurrentStatus(i, j));
            }
        }
    	
    }

}
