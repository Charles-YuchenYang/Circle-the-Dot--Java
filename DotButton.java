import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * In the application <b>Circle the dot</b>, a <b>DotButton</b> is a specialized type of
 * <b>JButton</b> that represents a dot in the game. It uses different icons to
 * visually reflect its state: a blue icon if the blue dot is currently on this location
 * an orange icon is the dot has been selected and a grey icon otherwise.
 *
 * The icon images are stored in a subdirectory ``data''. They are:
 * data/ball-0.png => grey icon
 * data/ball-1.png => orange icon
 * data/ball-2.png => blue icon
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotButton extends JButton
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int AVAILABLE_DOT_BUTTON 	= 0;
    public static final int SELECTED_DOT_BUTTON 	= 1;
    public static final int DOT_DOT_BUTTON 		= 2;

    // ADD YOUR INSTANCE VARIABLES HERE
    private int row;
    private int column;

    /**
     * Constructor used for initializing a cell of a specified type.
     *
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param type
     *            specifies the type of this cell
     */

    public DotButton(int row, int column, int type) {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        super();
        this.row=row;
        this.column=column;
        ImageIcon lImageIcon=getButtonIcon(type);
        this.setIcon(lImageIcon);
    }

    private ImageIcon getButtonIcon(int type)
    {
        String lIconFullPath="";

        switch (type)
        {
            case AVAILABLE_DOT_BUTTON:
                //grey icon
                lIconFullPath="/data/ball-0.png";
                break;
            case SELECTED_DOT_BUTTON:
                //orange icon
                lIconFullPath="/data/ball-1.png";
                break;
            case DOT_DOT_BUTTON:
                //blue icon
                lIconFullPath="/data/ball-2.png";
                break;
        }

        try
        {
            Image lImage= ImageIO.read(getClass().getResource(lIconFullPath));
            return new ImageIcon(lImage);
        }
        catch(Exception ex)
        {
            System.out.println("Invalid image\n"+ex.toString());
            return null;
        }
    }



    /**
     * Changes the cell type of this cell. The image is updated accordingly.
     *
     * @param type
     *            the type to set
     */

    public void setType(int type) 
    {
    	this.setIcon(getButtonIcon(type));
    }


    /**
     * Getter method for the attribute row.
     *
     * @return the value of the attribute row
     */

    public int getRow() {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return row;
    }

    /**
     * Getter method for the attribute column.
     *
     * @return the value of the attribute column
     */

    public int getColumn() {
// REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
        return column;
    }
}