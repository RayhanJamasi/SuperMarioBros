import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
/*
 * The purpose of this class is to handle all the logic that deals with
 * mario and how he interacts with the environment. This includes how he is moving
 * and the different settings he has on each level. Also consists of how he can 
 * jump or fall in different areas
 */

public class Charectar extends JLabel implements ActionListener {
	
	//TIP FOR ANIMATED: store the 4 images in an array and call at particular index at particular button
	// for upgrade with charectar facing diff directions
	public static ImageIcon [] iconArray = SuperMarioApplication.iconArray; 
	private static ImageIcon icon; //basic game
	
	//String key for charectar
	private String [] key;
	public static int dX, dY; //tracks change in movement
	
	//variables for changing the physics for the jumping and falling
	//depending on the level
	public static int stopGoingUp = 0;
	public static int fallingGravity = 0;
	public static int pixelsUp = 0;
	public static int pixelsDown = 0;
	
	//variables for tracking which way their moving
	public static boolean movingLeft = false;
	public static boolean movingRight = false;
	
	//this object keeps track of how long charectar should jump
	public Timer jumpTimer = new Timer(25, this);
	public int jumpCounter; //use for how long in the jump / moving during jump 
	public boolean jumping = false; //boolean variable determing when charectar is jumping
	
	//variable that holds the number of coins mario has collected
	public static int coinsCollected = 0;
	
	//variable that holds if the game is finished or not
	public static boolean coinsFinished = false;
		
	//for every action, their is an opposite reaction
	//this object keeps track of how long charectar should fall
	public Timer fallTimer = new Timer(25, this);
	public static int fallCounter; //use for how long in the fall / moving during falling 
	private boolean falling = false; //boolean variable determing when charectar is falling
	
	public Timer movingTimer = new Timer(30, this); // used to ensure that the action performed method
	// is still being called even when not jumping or falling
	
	//accessing variable to get the direction of mario
	public static String marioDirection = LevelFrame.marioDirection;
	
	//constructor for the
	//takes icon and string array as objects
	public Charectar (ImageIcon icon, String [] key) {
		//constructor that sets all the values for the object
		//depending on what was passed in
		super();
		setIcon(icon); //setting the icon
		this.key = key; //setting the key

	}
	
	
	//getter and setters
	public static ImageIcon[] getIconArray() {
		return iconArray;
	}

	//check if you really need icon array getters / setters
	public void setIconArray(ImageIcon[] iconArray) {
		this.iconArray = iconArray;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public static void setIcon(ImageIcon icon) {
		Charectar.icon = icon;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public int getdX() {
		return dX;
	}

	public void setdX(int dX) {
		this.dX = dX;
	}

	public int getdY() {
		return dY;
	}

	public void setdY(int dY) {
		this.dY = dY;
	}

	public Timer getJumpTimer() {
		return jumpTimer;
	}

	public void setJumpTimer(Timer jumpTimer) {
		this.jumpTimer = jumpTimer;
	}

	public int getJumpCounter() {
		return jumpCounter;
	}

	public void setJumpCounter(int jumpCounter) {
		this.jumpCounter = jumpCounter;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public Timer getFallTimer() {
		return fallTimer;
	}

	public void setFallTimer(Timer fallTimer) {
		this.fallTimer = fallTimer;
	}

	public static int getFallCounter() {
		return fallCounter;
	}

	public void setFallCounter(int fallCounter) {
		this.fallCounter = fallCounter;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	//utility method for jumping
	public void jump() {
		jumping = true;
		jumpCounter = 0;
		jumpTimer.start();

	}
	
	//utility method for falling
	public void fall() {
		falling = true;
		movingTimer.start();
		fallCounter = 0;
		fallTimer.start();

	}
	
	//method for moving right
	public void moveRight() {
		movingRight = true;
		dX = 4; //the change in pixels is 4 at a time
		setBounds(getX() + dX, getY(), 25, 25);
	}
	
	//method for moving left
	public void moveLeft() {
		movingLeft = true;
		movingTimer.start();
		dX = -4; //the change in pixels is 4 at a time
		setBounds(getX() + dX, getY(), 25, 25);
	}
	
	//return row of 2D game map that coresponds to 
	//current position of charectar. determine which
	//tile charectar is on and perform action based on that
	//information. 
	public int getRow() {
		//divides y by hieght of the game board, returns integer
		//result as row number for charectar
		return (int)getY()/25;
	}
	
	public int getCol() {
		
		//gets the height of mario as it divides his x value
		//by 25 so it corresponds to the game board (col number)
		return (int)getX()/25;
	}
	
	//method for getting the coin and making the icon 
	//dissapear, so its like if I collected it, replace icon
	//with nothing
	public void collectCoin() {
		int row = getRow();
		int col = getCol();
		
		//collecting coin if he is on it
		if (LevelFrame.boardArray[row][col].getIcon() == Icons.COIN) {
			
			//setting the icon to null if coin is collected
			LevelFrame.boardArray[row][col].setIcon(null);
			coinsCollected++;
		}
	}
	
	//this code finishes the level if peach is successfully saved
	//or if all the coins are collected
	public void finishGame() {
		if ((coinsCollected == LevelFrame.coinsAmt && LevelFrame.coinsAmt 
				!= 0) || LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.PEACH) {
			movingTimer.stop();
			coinsFinished = true;
			
		}
	}
	
	//
	public static void resetMovingTimer() {
		//movingTimer.restart();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		//checking if mario is swimming in the lava, if so, make it so marioDead is
		//true. 
		if (LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.LAVA && 
				LevelFrame.lvlPlayed == 3 && SuperMarioApplication.marioDead == false) {
			SuperMarioApplication.marioDead = true;
		}
				
		//collecting coin logic
		collectCoin();
		
		//checking if they beat the game
		finishGame();
		
		//checking if they went through a fake grass block and making it vanish SOMETIMES to 
		//make it a little easiar
		if (LevelFrame.boardArray[getRow() + 1][getCol() + 1].getIcon() == Icons.FAKEGRASS ||
				LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.FAKEGRASS) {
			LevelFrame.boardArray[getRow()][getCol()].setIcon(null);
		}
		
		//checking if they are picked up a key, setting it to null and updating a variable
		//saying that it was picked up
		if (LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.KEY) {
			LevelFrame.boardArray[getRow()][getCol()].setIcon(null);
			SuperMarioApplication.keyTaken = true;
		}
				
		//checking if he is at a teleporter
		if (LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.TELEPORTER) {
			LevelFrame.mario.setBounds(500, 75, 25, 25);
		}
		
		//jumping logic that increases the counter by one
		if (jumping) {
			jumpCounter++;
			
			//depending on if its lvl 3 or not, the physics would be different
			if (LevelFrame.lvlPlayed != 3) {
				stopGoingUp = 14;
				fallingGravity = 20;
				pixelsDown = 5;
				pixelsUp = -5;
			}
			else {
				stopGoingUp = 20;
				fallingGravity = 15;
				pixelsDown = 3;
				pixelsUp = -3;
				
			}

			if (jumpCounter <= stopGoingUp) { //going up
				dY = pixelsUp; //shift him up
				
				//making sure he doesnt clip through the ceiling when jumping on the spot and 
				//when facing the right or left
				if ((LevelFrame.marioDirection.equals("right") && LevelFrame.boardArray[getRow() 
	                 - 1][getCol() + 1].getIcon() == Icons.GRASS) || (LevelFrame.marioDirection.equals
							("left") && LevelFrame.boardArray[getRow() - 1]
									[getCol() + 1].getIcon() == Icons.GRASS))  {
					dY = 0;
				}
				//changing the icon depending on his direction
				if (LevelFrame.marioDirection.equals("right")) {
					Charectar.setIcon(iconArray[4]);
				}
				
				else if (LevelFrame.marioDirection.equals("left")) {
					Charectar.setIcon(iconArray[5]);
				}
				
			}
			
			else if (jumpCounter <= fallingGravity) { // going down
				dY = pixelsDown; //shifting him down
				
				//changing the icon depending on his direction
				if (LevelFrame.marioDirection.equals("right")) {
					Charectar.setIcon(iconArray[6]);
				}
				
				else if (LevelFrame.marioDirection.equals("left")) {
					Charectar.setIcon(iconArray[7]);
				}
				
				//making sure he doesnt clip through the floor when falling towards the right or left
				if ((LevelFrame.marioDirection.equals("right") && LevelFrame.boardArray[getRow() 
   	                 + 1][getCol() + 1].getIcon() == Icons.GRASS) || (LevelFrame.marioDirection.equals
   							("left") && LevelFrame.boardArray[getRow() + 1]
   									[getCol() + 1].getIcon() == Icons.GRASS))  {
					
					//stop falling and stopping the timer
					falling = false; 
					dY = 0;
					fallTimer.stop();
				}
				
			}
			
			else { //reset everything so that it stops jumping
				jumping = false;
				dY = 0;
				jumpTimer.stop();
				fall();
			}
		}
		
		//logic that handles the falling, increases the counter by 1 each time it runs
		else if (falling) { 
			fallCounter++;
			dY = 5; //move charecter down 5 units at a time
			setBounds(getX(), getY(), 25, 25); //updates charectar position
			
			//making sure he doesnt clip through the wall when hes already on a block and 
			//he jumps straight up (error would only occur if near the edge, this statement
			//prevents it from occuring). I use an1 if statement to prevent this bug, it has paramaters
			//for when mario is facing the right or the left 
			if ((LevelFrame.marioDirection.equals("right") && LevelFrame.boardArray[getRow() 
			                 + 1][getCol() + 1].getIcon() == Icons.GRASS) || (LevelFrame.marioDirection.equals
									("left") && LevelFrame.boardArray[getRow() + 1]
											[getCol() + 1].getIcon() == Icons.GRASS))  {
				falling = false;
				dY = 0;
				fallTimer.stop();
			}
			
			//stop falling after the count is equal or greater than 20
			else if (fallCounter >= 20) {
				falling = false;
				dY = 0;
				fallTimer.stop();	
			}
		}
		
		//seeing if the charectar is jumping up and stopping if wall is 
		//above
		if (jumping && dY < 0 && LevelFrame.boardArray[getRow() 
					- 1][getCol()].getIcon() == Icons.GRASS) {
			jumping = false;
			dY = 0;
			jumpTimer.stop();
		}
		
		//seeing if they are landing on a block
		else if (falling && dY > 0) {
			int nextRow = getRow() + 1;
			if (nextRow >= LevelFrame.boardArray.length || 
					LevelFrame.boardArray[nextRow][getCol()].getIcon() == Icons.GRASS) {
				falling = false;
				dY = 0;
				fallTimer.stop();
			}
		}
		
		//seeing if they are running off a block without jumping and making them
		//fall if they are (similar falling logic)
		if (movingLeft && !jumping) {
			if (LevelFrame.boardArray[getRow() + 1][getCol() + 1].getIcon() != Icons.GRASS) {
				dY = 5;
				if (LevelFrame.boardArray[getRow() + 1][getCol()].getIcon() == Icons.GRASS) {
					falling = false;
					dY = 0;
					fallTimer.stop();
				}
				//
				else if (fallCounter >= 20) {
					falling = false;
					dY = 0;
					fallTimer.stop();

				}
			}
		}
		
		//seeing if they are running off a block without jumping to the right and making them
		//fall if they are (similar falling logic)
		else if (movingRight && !jumping) {
			if (LevelFrame.boardArray[getRow() + 1][getCol() - 1].getIcon() != Icons.GRASS || 
							LevelFrame.boardArray[getRow()+ 1][getCol()].getIcon() == null) {
				dY = 5;
				if (LevelFrame.boardArray[getRow() + 1][getCol()].getIcon() == Icons.GRASS) {
					falling = false;
					dY = 0;
					fallTimer.stop();
				}
				//
				else if (fallCounter >= 20) {
					falling = false;
					dY = 0;
					fallTimer.stop();

				}
			}
		}
		
		//checking if they can go through a wall on the right 
		if ((LevelFrame.boardArray[getRow()][getCol() + 1].getIcon() == Icons.GRASS || 
				LevelFrame.boardArray[getRow()][getCol() + 1].getIcon() == Icons.BORDER) && dX > 0) {
			dX = 0; 
		}
		
		//checking if they can go through a wall on the left
		else if ((LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.GRASS 
				 || LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.BORDER)&& dX < 0) {
			dX = 0;
		}
		
		//checking if they can go through top
		if ((LevelFrame.boardArray[getRow() - 1][getCol()].getIcon() == Icons.GRASS ||
				LevelFrame.boardArray[getRow() - 1][getCol()].getIcon() == Icons.BORDER) && dY < 0) {
			dY = 0;
		}
		
		//checking if they can go through the bottom
		else if (LevelFrame.boardArray[getRow() + 1][getCol()].getIcon() == Icons.GRASS && dY > 0) {
			dY = 0;
			
			//makes sure he does not have falling sprite
			//once he reaches the ground
			if (LevelFrame.marioDirection.equals("right")) {
				Charectar.setIcon(iconArray[2]);
			}
			
			else if (LevelFrame.marioDirection.equals("left")) {
				Charectar.setIcon(iconArray[3]);
			}
		}
		
		//fail-safe to ensure proper gravity
		else if (LevelFrame.boardArray[getRow() + 1][getCol()].getIcon() == null && dY > 0)
			fall();
		
		//if hes not falling and running, prevents error of player jumping
		//from tall heights and not updating the sprite when he lands
		if (dY == 0 && dX > 0 && LevelFrame.marioDirection.equals("right")) {
			Charectar.setIcon(iconArray[0]);
			}
		
		//setting icon depending on direction 
		else if (dY == 0 && dX < 0 && LevelFrame.marioDirection.equals("left")) {
			Charectar.setIcon(iconArray[1]);	
			}
		
		//making sure he is on fire if he is in the lava
		if (LevelFrame.lvlPlayed == 3 && LevelFrame.boardArray[getRow()][getCol()]
									.getIcon() == Icons.LAVA) 
			Charectar.setIcon(iconArray[8]);
		
		if (SuperMarioApplication.marioDead == true) {
			//stopping the falling timer so he does not keep falling
			//after he is dead
			fallTimer.stop();
		}
		//moving mario
		setBounds(getX() + dX, getY() + dY, 25, 25);
	}
}
