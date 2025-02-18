import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * This class creates the GUI frame of the game level.
 * It sets up the level's board, loads the map from the text file, 
 * and handles key bindings for the character's movements. It is
 * responsible for working with the graphics and other utiliy methods
 * 
 */


public class LevelFrame extends JFrame implements KeyListener{
	//create a 2D array to hold the board's labels
	public static JLabel[][] boardArray = new JLabel[20][25];
	//create a panel to hold the level
	public static JPanel lvlPanel = new JPanel();
	
	//variable that holds the level number so I can figure out which
	//level was played later on
	public static int lvlPlayed = 0;
	
	//holds a timelimit for how fast floor is removed in lvl 3 
	public static int timeLimit = 2;
	
	//holds how many floors have been altered in lvl 3
	public static int floorsLeft = 18;
	
	//access icon array of mario animations
	public static ImageIcon [] iconArray = SuperMarioApplication.iconArray;
	
	//variables that revolve around calculating time
	public static long currentTime = System.currentTimeMillis();
	public static int thousandths = 0;
	public static int seconds = 0;
	public static int minutes = 0;
	public static long nextTime;
	public static String timerText = "";
	public static String timerSeconds = "0";
	public static Integer holdSeconds;
	
	//variable that checks if the timer should be running
	public static boolean updateTimerCheck = true;
	
	//initialize mario character (icon and key bindings)
	public static Charectar mario = new Charectar(SuperMarioApplication.iconArray[4], 
								new String[] {"a", "d", " "});
		
	//variables that access direction mario is moving
	public static boolean movingLeft = Charectar.movingLeft;
	public static boolean movingRight = Charectar.movingRight;
	
	//variable used in another frame to tell if he standing facing the right or 
	//left
	public static String marioDirection = "right";
	
	//game timer that starts when the game starts (declaring the game timer) and the 
	//correpsonding label and variable
	public static Timer gameTimer;
	public static JLabel timerLabel;
	public static int timePast = 0;
	
	//variable that holds the total amount of coins 
	public static int coinsAmt = 0;
	
	//declaring JComponenets
	JPanel backgroundPanel;
		
	//constructor
	//initialize the level frame with a given level number
	public LevelFrame(int level) {
		super();
		
		//setting title and dimensions
		this.setTitle("Super Mario Game");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1080, 610); 
		//setting setVisible to true to allow the frame to be visible
		this.setVisible(true);
		//Center the frame on the screen
        setLocationRelativeTo(null);
                
        //declaring variable that holds the image url
        String bgURL = "";
        
        //choosing a different background depending on the level and 
        //determining which level is being played
		if (level == 1) {
			bgURL = "images/superMarioBgLvl1.png";
			lvlPlayed = 1;
		}
		
		else if (level == 2) {
			bgURL = "images/superMarioBgLvl2.png";
			lvlPlayed = 2;
		}
		
		else if (level == 3) {
			lvlPlayed = 3;
			bgURL = "images/superMarioBgLvl3.png";
		}
		
		//resetting the textures if level 3 is not being
		//played incase the textures are still being
		//carried over
		if (level != 3) {
			resetTextures();
		}
	
		//creating imageIcon with the correct background image
		ImageIcon bgImage = new ImageIcon(bgURL);
        
		//set the background of the window as an image
		//https://stackoverflow.com/questions/19125707/
		//simplest-way-to-set-image-as-jpanel-background
		backgroundPanel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(bgImage.getImage(), 0, 0, getWidth() - 20, getHeight() - 20, this); 
					//backgroundImage.getIconHeight(), this);
			}
		};
		backgroundPanel.setPreferredSize(new Dimension(bgImage.getIconWidth(), 
				bgImage.getIconHeight()));
        
		//set the size and layout of the frame
		setSize(25*boardArray[0].length, 25*boardArray.length + 10);
		backgroundPanel.setLayout(null);
		
		//customizing and making time label
        // https://stackoverflow.com/questions/59763059/change-the-font-style-size-in-a-java-swing-appliation
        timerLabel = new JLabel(""); //blank text for now
        timerLabel.setFont(new Font("Bullpen", Font.BOLD, 30));
        timerLabel.setBounds(440, 30, 200, 30);
        timerLabel.setForeground(new Color(236, 168, 51));
        backgroundPanel.add(timerLabel);
                
    	//call the method to load the level
		loadLevel(level);
		
		//call the method to create the panel of the level
		//passing imageIcon as an arguement
		createLvlPanel();
		
		//set up key bindings for character controls
		setupKeyBindings();
		//add KeyListener to the panel
		lvlPanel.addKeyListener(this);
		
		//initalizing the game timer to make it update every second
		// https://stackoverflow.com/questions/22366890/java-timer-action-listener
        gameTimer = new Timer(1, new ActionListener() {
        	
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            timePast++; 
	            updateTimer();
	            
	            //passing the levelFrame to isMarioDead() so it can 
	            //close the frame instantly if he falls in lava
	            isMarioDead();
	        }
	    });
                
        //starting game timer
        gameTimer.start();
		
		//setting to transparent
		lvlPanel.setOpaque(false);
		
		//adding componenets to panels and
		//setting the borderLayout
		backgroundPanel.add(lvlPanel);
		setContentPane(backgroundPanel);
		backgroundPanel.setLayout(new BorderLayout());
		
		//make the frame visible
		setVisible(true);
	}

	//this method is responsible for updating the timer in the top right of the 
	//screen. It does this by calculating the new time every tick and updating a 
	//label
	public static void updateTimer() {
		
		//making sure that the timer should be running 
		if (updateTimerCheck != false) {
			//having the current times		
			long nextTime = System.currentTimeMillis();
			
			//this calculates the amount of time past. Calculates the 
			//the amount of time that has past by subtract. Caden helped
			//me with this line below
			long timePast = nextTime - currentTime;
			
			//gets the time past as the timer goes off every tick, so
			//the seconds would be the tenths / 1000 (as their is 1000
			//miliseconds in 
			int thousandths = (int)timePast;
			int seconds = (int)timePast / 1000;
			int minutes = (int)timePast / 60000;
								
			//formatting all of the values for the time to 2 decimal points
			//and ensuring its accurate
			String timerMinutes = String.format("%02d", minutes);
			String timerSeconds = String.format("%02d", seconds - (minutes * 60));
			String timerThousandth = String.format("%01d", thousandths - (seconds * 1000));
			
			//getting value of seconds (used in lvl3Rise for lava)
			holdSeconds = Integer.valueOf(timerSeconds);
			
			//grouping all the values into one string and displaying it in the label
			String timerText = timerMinutes + " " + timerSeconds + " " + timerThousandth;
			timerLabel.setText(timerText);
			
			//running through the lvl3Rise method (did it in the time method as the time
			//method runs consistently)
			if (lvlPlayed == 3) 
				lvl3Rise();
			
		}
	}
	
	//this method load the level from the map text file and add label to board array
	private void loadLevel(int level) {
		try {
			
			//making random object
			Random randCoins = new Random();
			
			//declaring variable that would hold the coin placement letter
			char coinPlace = '0';
			
			//generating random number
			int randCoinSpawns = randCoins.nextInt(3);
			
			//descision structure that determines the coin spawns
			if (randCoinSpawns == 0)
				coinPlace = '1';
			else if (randCoinSpawns == 1) 
				coinPlace = '2';
			else if (randCoinSpawns == 2) 
				coinPlace = '3';
			
			//ensuring coinsAmt value is equal to 0
			coinsAmt = 0;
			
			//open the file for the specific level
			Scanner inputFile = new Scanner(new File("levels/Level" + level + ".txt"));
			
			//This loop go through every square in the grid
			//and ensure that it is set with a JLabel depending 
			//on the charectar
			for (int row = 0; row < boardArray.length; row++) {
				//convert each row to a char array
				char[] lineArray = inputFile.next().toCharArray();
				
				//loop through each character and add labels to the array
				//and possibly changing the image depending on the level
				//and the charectar
				for (int col = 0; col < lineArray.length; col++) {
					
					if (lineArray[col] == 'B') {
						
						//giving a stone texture if on level 3 for specific
						//blocks
						boardArray[row][col] = new JLabel(Icons.BORDER);
						if (lvlPlayed == 3) {
							Icons.BORDER.setImage(Icons.STONE_BORDER.getImage());
						}
					}
					
					else if (lineArray[col] == 'G') {
						//different texture depending on if its the secret level 
						//or not
						boardArray[row][col] = new JLabel(Icons.GRASS);
						if (lvlPlayed == 3) {
							Icons.GRASS.setImage(Icons.STONE.getImage());
						}
					}
					
					//ensuring the it doesnt make the hidden blocks for level 3
					//too early
					else if (lineArray[col] == 'F' && lvlPlayed != 3) {
						//different texture depending on if its the secret level 
						//or not			
						boardArray[row][col] = new JLabel(Icons.FAKEGRASS);
					}
										
					else if (lineArray[col] == coinPlace) {
						boardArray[row][col] = new JLabel(Icons.COIN);
						coinsAmt++;
					}
					
					else if (lineArray[col] == 'T') {
						boardArray[row][col] = new JLabel(Icons.TELEPORTER);
					}
					
					else if (lineArray[col] == 'P') {
						boardArray[row][col] = new JLabel(Icons.PEACH);
					}
					
					else if (lineArray[col] == 'K') {
						boardArray[row][col] = new JLabel(Icons.KEY);
					}
					
					else {
						boardArray[row][col] = new JLabel();
					}
				}
			}
			inputFile.close();	//close the scanner
			
		} catch (FileNotFoundException e) {
			//print error if file is not found
			e.printStackTrace();
		}
	}

	//this method creates a JPanel and adds JLabels to the panel
	private void createLvlPanel() {
		//set the layout and background color
		lvlPanel.setLayout(null);
		
		//loop through each label in the array and ensure that
		//it's bounds are set and adding it to the levelPanel
		for (int row = 0; row < boardArray.length; row++) {
			for (int col = 0; col < boardArray[0].length; col++) {
				
				if (boardArray[row][col].getIcon() == Icons.GRASS) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.COIN) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.BORDER) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.STONE) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.STONE_BORDER) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.FAKEGRASS) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.TELEPORTER) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.KEY) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
				else if (boardArray[row][col].getIcon() == Icons.PEACH) {
					//set the bound and position
					boardArray[row][col].setBounds(col*25, row*25, 25, 25);
					//add the label to the panel
					lvlPanel.add(boardArray[row][col]);
				}
			}
		}
		//set the initial position of mario
		mario.setBounds(25, 425, 25, 25);
		//ensuring Mario's charectar is not still a fireball if user died
		//on level 3
		Charectar.setIcon(SuperMarioApplication.iconArray[2]);
		//add mario to panel
		lvlPanel.add(mario);
		//set the bound of the panel
		lvlPanel.setBounds(0, 0, 25*boardArray[0].length, 25*boardArray.length);
		//setting panel to opaque
		lvlPanel.setOpaque(false);
		//add the panel to the frame
		add(lvlPanel);
	}
	
	//this method is responsible for the movement of lava in the secret
	//level and for ensuring that it works out well
	public static void lvl3Rise() {
		if (lvlPlayed == 3) {

			if (holdSeconds > timeLimit) {
				
				//minusing floorsLeft
				floorsLeft--;
				
				//this will make every row except for the bottom row
				//in danger of becoming lava. I left the bottom row so it 
				//looks as if the room is being filled 
				for (int row = 18; row > floorsLeft; row--) {	
						for (int col = 0; col < 25; col++) {
						
						//changing the icon to Lava and setting the bounds
						//and adding to lvlPanel
						boardArray[row][col].setIcon(Icons.LAVA);
						boardArray[row][col].setBounds(col*25,row*25, 25, 25);
						lvlPanel.add(boardArray[row][col]);
					}
				}
				
				
				try {
					//responsible for making the new blocks appear at the right time 
					// https://www.educative.io/answers/reading-the-nth-line-from-a-file-in-java
					int revealRow = floorsLeft - 1;
					//reading only one specific line of the txt file
					String line = Files.readAllLines(Paths.get("levels/Level3.txt")).get(revealRow);
					
					//converting the string to a charArray
					char [] charArray = line.toCharArray();
					
					//going throguh each charectar in the line of the file that was read
					//and setting important values depending on the charectar
					for (int col = 0; col < 25; col++) {
						if (charArray[col] == 'H') {
							boardArray[revealRow][col].setIcon(Icons.GRASS);
							boardArray[revealRow][col].setBounds(col*25, revealRow*25, 25, 25);
							lvlPanel.add(boardArray[revealRow][col]);
						}
						else if (charArray[col] == 'F') {
							boardArray[revealRow][col].setIcon(Icons.FAKEGRASS);
							Icons.FAKEGRASS.setImage(Icons.STONE.getImage());
							boardArray[revealRow][col].setBounds(col*25, revealRow*25, 25, 25);
							lvlPanel.add(boardArray[revealRow][col]);
						}

						//rerendering and repainting the board to ensure the board was 
						//properly adjusted
						lvlPanel.repaint();
						lvlPanel.revalidate();
					}
					
				//catching fileNotFound exception
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					
				//catching IOException
				} catch (IOException e) {
					e.printStackTrace();
				}
								
				//timelimit goes up by 2, so theirs a 2 minute
				//difference with the lava increase
				timeLimit += 2;
			}
		}
	}
	
	//this method is used to ensure that the textures are reset every time
	//a level is created to ensure textures from level 3 are not carried
	//over to the textures in the first 2 levels
	public void resetTextures() {
		Icons.BORDER.setImage(Icons.DEFAULT_BORDER.getImage());
	    Icons.GRASS.setImage(Icons.DEFAULT_GRASS.getImage());
	    Icons.FAKEGRASS.setImage(Icons.DEFAULT_FAKEGRASS.getImage());
	}

	//set up key bindings for the character's movement and actions
	private void setupKeyBindings() {
		//declare ActionMap and InputMap
		ActionMap actionMap;
		InputMap inputMap;
		
		//get the input and action maps for key bindings
		inputMap = lvlPanel.getInputMap();
		actionMap = lvlPanel.getActionMap();
		
		//bind the "a" key to move mario left
		inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[0].toCharArray()[0]), "left");
		actionMap.put("left", new KeyAction("left"));
		
		//bind the "d" key to move mario right
		inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[1].toCharArray()[0]), "right");
		actionMap.put("right", new KeyAction("right"));
		
		//bind the spacebar key to make mario jump
		inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[2].toCharArray()[0]), "jump");
		actionMap.put("jump", new KeyAction("jump"));
		
	}
	
	//this method checks if the coins are all collected 
	//and resets important variables with the timer. it also
	//opens up the congratulations window 
	public void coinsAreFinished () {
		//checking if the coins were all collected
		//
		if (Charectar.coinsFinished == true) {
			Charectar.coinsFinished = false;
			//resetting timer variables and related variables so the timer
			//resets properly
			updateTimerCheck = false;
			gameTimer.restart();  //neccesary?
			gameTimer.stop();
			timePast = 0;
			thousandths = 0;
			seconds = 0;
			minutes = 0;
			timerLabel.setText("");
			timerText = "";
			
			//determining which level has been played and if they
			//they won it
			if (lvlPlayed == 1) {
				SuperMarioApplication.lvl1Complete = true;
			}
			
			else if (lvlPlayed == 2) {
				SuperMarioApplication.lvl2Complete = true;
			}
			
			//opening up congratulations screen and resetting
			//coinsFinished variable
			this.dispose();
			new FinishLevel();
			Charectar.coinsFinished = false;
		}
	}
	
	//this method checks if mario is dead and if he is
	//it resets important variables with timer and other 
	//things like the coins amount. It disposes
	//the frame and ensures marioDead is now equal to false
	//it will also open up the level frame after
	public void isMarioDead () {
		//checking if mario fell in lava
		//and resetting it if he did
		if (SuperMarioApplication.marioDead == true) {
			SuperMarioApplication.marioDead = false;
			//resetting timer variables and related variables so the timer
			//resets properly
			updateTimerCheck = false;
			//gameTimer.restart();  //neccesary?
			gameTimer.stop();
			timePast = 0;
			thousandths = 0;
			seconds = 0;
			minutes = 0;  
			timerLabel.setText("");
			timerText = "";
						
			//resetting other variables
			Charectar.coinsCollected = 0; 
			Charectar.coinsFinished = false;
			holdSeconds = 0;
					
			//closing frame
			this.dispose();
			
			//prevents mario from running offscreen
			//when he dies
			Charectar.dX = 0;
			Charectar.dY = 0;
			
			//removes everything from the panel and rerenders it
			// https://stackoverflow.com/questions/40380506/how-does-java-repaint-work
			lvlPanel.removeAll();
			lvlPanel.repaint();
			
			//opening the level choice frame cause they died and resetting the variables
			//that relate to how the lava rises
			new ChooseLevelFrame();
			
			//resetting more important variables
			lvlPlayed = 0;
			floorsLeft = 18;
			timeLimit = 2;

		}
	}
	
	
	//not using but need it to use the interface
	public void keyTyped(KeyEvent e) {}
		
	//not using but need it to use the interface
	public void keyPressed(KeyEvent e) {
				
		//depending on direction, changing icon
		if (e.getKeyChar() == 'a') {
				Charectar.setIcon(iconArray[1]);
				marioDirection = "left";
		}
		
		else if (e.getKeyChar() == 'd') {
			Charectar.setIcon(iconArray[0]);
			marioDirection = "right";
		}
		
		//checking if coins are finished
		coinsAreFinished();
	}
	
	
	//handle when the key is released
	@Override
	public void keyReleased(KeyEvent e) {
		
		//if the "a" or "d" key is released, sets the horizontal velocity to 0 (stop moving) 
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'd') {
			mario.setdX(0);
			movingLeft = false;
			movingRight = false;
		}
		
		//choose which picture to put for mario depending on the direction
		//he stands in
		if (e.getKeyChar() == 'd') {
			Charectar.setIcon(iconArray[2]);
		}
		
		else if (e.getKeyChar() == 'a') {
			Charectar.setIcon(iconArray[3]);
		}
		
		//checking if coins are finished
		coinsAreFinished();
	}
	
}
