import javax.swing.ImageIcon;

/*
 * Name: Rayhan Jamasi
 * 
 * Date: Friday December 13, 2024
 * 
 * Course Code: ICS3U1-04/05 Mrs. Biswas
 * 
 * Title: Super Mario Bros, P Mall - Edition
 * 
 * Description: This project is a GUI game that revolves around JSwing arrays. Mario
 * has to collect all the coins in the level without falling for traps
 * or falling into lava. The functions of this project is to have
 * movement and nice graphics such as backgrounds and other componenets
 * that all add to make a great game. In general, you must complete the first level
 * before you get to the second level and similar to with the secret level, althouhg
 * another condition must be met before you unlock that one. After finishing all 3 levels, 
 * that is the end of the game. 
 * 
 * Major Skils: - Effective use of JSwing componenets that allow for engaging and working code.
 * 						This includes JPanels, JLabels, JButtons, JFrames, and a lot more
 * 				- Using Arrays to find mario and to calculate the hitboxes / movement
 * 				         for mario and as he goes through the level
 * 				- Reading text files to create levels. Specific conditions depending on what
 * 					     is being read
 * 				- ImageIcons
 * 						setting imageicons depending on conditions for mario
 * 
 * Added Features:
 * 
 * Basic feature 1: sprite changes depending on direction
 * Basic feature 2: not running sprite for when mario is not moving 
 * 					(he will not be running on the spot when the user
 * 					releases a key, instead he will freeze
 * Basic feature 3: title frame where you can access settings and level frame
 * Basic Feature 4: Timer that counts how long since you started the game
 * Basic feature 5: Timer that is more accurate and goes to the thousandths
 * Basic feature 6: fixing the movement so that you can fall properly and don't clip
 * 						through blocks as much
 * Basic feature 7: Different coin spawning, not the same each time
 * Basic feature 8: Teleporter that will move charectar to a different spot
 * Basic feature 9: Multiple levels
 * Basic Feature 10: Updating blocks on the secret level that rises
 * Basic Feature 11: Blocks that appear only after a specific time
 * Advanced Feature: DIfferent physics with his velocity and gravity (going up
 * and down) on the space level due to the gravity of the special planet they are on
 * 
 * Areas of Concern:
 * 
 * Cant load the game without stretching screen slightly
 * 
 * Still bugs from the original code. Although I was able to fix a lot of the major
 * ones with the movement, bad timing can lead to possible clipping and the movement
 * can be a bit buggy.
 * 
 * Contribution to Assignment:
 * Rayhan: made all files and methods, completed 99.9% of work
 * Caden: one line of code with the timer
 * 
 */

public class SuperMarioApplication {
	
	//TIP - CHANGE LAVA BLOCK TO NEW MARIO WHERE ITS CONSISTENT
	
	//declaring the ImageIcon array that holds all the 
	//mario related images
	public static ImageIcon [] iconArray = new ImageIcon[9];
	
	//declaring variables that see which level is complete
	//and if the secret key was picked up
	public static boolean lvl1Complete = true;
	public static boolean lvl2Complete = true;
	public static boolean keyTaken = true;
	public static boolean marioDead = false;
		
	//adding the icons to the iconArray
	public static void main(String [] args) {
		//adding to imageIcon 
		iconArray[0] = Icons.MARIO_RIGHT;
		iconArray[1] = Icons.MARIO_LEFT;
		iconArray[2] = Icons.MARIO_STANDING_RIGHT;
		iconArray[3] = Icons.MARIO_STANDING_LEFT;
		iconArray[4] = Icons.MARIO_JUMPING_RIGHT;
		iconArray[5] = Icons.MARIO_JUMPING_LEFT;
		iconArray[6] = Icons.MARIO_FALLING_RIGHT;
		iconArray[7] = Icons.MARIO_FALLING_LEFT;
		iconArray[8] = Icons.MARIO_FIREBALL;
		
		//calling the title frame
			new SuperMarioTitleFrame();
	}

}
