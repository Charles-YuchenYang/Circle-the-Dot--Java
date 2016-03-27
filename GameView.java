import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//constants
    private static final String GAME_VIEW_TITLE="Circle the Dot";

    private static final int GAME_VIEW_WIDTH_SCALE=45;
    private static final int GAME_VIEW_HEIGHT_SCALE=45;

    // ADD YOUR INSTANCE VARIABLES HERE

    JButton resetButton;
    JButton quitButton;
    BoardView boardView;
    JPanel buttonPanel;


    BorderLayout gameViewLayout;
    BorderLayout buttonPanelLayout;

    /**
     * Constructor used for initializing the Frame
     *
     * @param model
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel model, GameController gameController) {
        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        super(GAME_VIEW_TITLE);

        boardView=new BoardView(model, gameController);
        this.add(boardView, BorderLayout.NORTH);

        this.resetButton=new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(gameController);
        
        
        this.quitButton=new JButton("Quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(gameController);

        this.buttonPanel=new JPanel();
        this.buttonPanel.add(resetButton, BorderLayout.EAST);
        this.buttonPanel.add(quitButton, BorderLayout.WEST);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setSize(GAME_VIEW_WIDTH_SCALE*model.getSize()+100, GAME_VIEW_HEIGHT_SCALE*model.getSize()+150);

        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Getter method for the attribute board.
     *
     * @return a reference to the BoardView instance
     */

    public BoardView getBoardView(){
        // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return boardView;
    }
    
    public void update()
    {
    	boardView.update();
    }

}
