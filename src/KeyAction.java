import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.text.TextAction;

/*
 * has logic that ensures charectar moves only in 
 * correct directions, this class controls the keybindings
 * (handling movements that are not valid). This ensures
 * the correct logic with mario's movement and the keyboard
 */

public class KeyAction extends TextAction {
	private String key;
		
	//accessing icon array and mario's direction
	public static ImageIcon [] iconArray = SuperMarioApplication.iconArray;
	public static String marioDirection = LevelFrame.marioDirection;
	
	//constructor
	//passes the key to the constructor
	public KeyAction (String key) {
		super(key);
		this.key = key;
	}
	
	/*
	 * this will get the key of mario and and determine
	 * if he can really move left based off what is near him and
	 * if their are certain blocks surrouding him
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//
		Charectar mario = LevelFrame.mario;
		
		//meaning if your clicking the a key with mario. 
		
		// - 1 because you are checking if their is a wall on his left
		//basically its saying if the mario is click the a key and the board
		//array for the spot on his left is not a wall, move him left
		if (e.getActionCommand().equals(mario.getKey()[0])
				&& (LevelFrame.boardArray[mario.getRow()][mario.getCol()].getIcon() !=
				Icons.GRASS && LevelFrame.boardArray[mario.getRow()][mario.getCol()].getIcon() !=
				Icons.BORDER)) {
			mario.moveLeft();
			
		}
		
		else if (e.getActionCommand().equals(mario.getKey()[1])
				&& (LevelFrame.boardArray[mario.getRow()][mario.getCol() + 1].getIcon() !=
				Icons.GRASS && LevelFrame.boardArray[mario.getRow()][mario.getCol() + 1].getIcon
					() !=Icons.BORDER)) {
			
			mario.moveRight();
			
		}
		
		//if jump key, then make sure nothing is above him and jump
		else if (e.getActionCommand().equals(mario.getKey()[2])
				&& (LevelFrame.boardArray[mario.getRow() - 1][mario.getCol()].getIcon() !=
				Icons.GRASS && LevelFrame.boardArray[mario.getRow() - 1][mario.getCol()].getIcon() !=
				Icons.BORDER)) { 
			//ensuring that jump is called if he is not jumping
			if (!mario.isJumping()) {
				mario.jump();
			}
			
		}
	
	}
	
}