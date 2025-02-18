import javax.swing.ImageIcon;
/*
 * the purpose of this class is to make the icons for the 
 * game that can than be accessed later on in other files
 */

public class Icons {
	

	//used for the game map like blocks, charectars, coins, etc
	public static final ImageIcon GRASS = new ImageIcon("images/marioGrassBlock.png");
	public static final ImageIcon STONE = new ImageIcon("images/stoneBlock.png");
	public static final ImageIcon BORDER = new ImageIcon("images/marioGround.png");
	public static final ImageIcon STONE_BORDER = new ImageIcon ("images/stoneBorder.png");
	public static final ImageIcon COIN = new ImageIcon("images/coin.png");
	public static final ImageIcon FAKEGRASS = new ImageIcon("images/marioGrassBlock.png");
	public static final ImageIcon TELEPORTER = new ImageIcon("images/marioTeleporter.png");
	public static final ImageIcon KEY = new ImageIcon("images/marioKey.png");
	public static final ImageIcon PEACH = new ImageIcon("images/princessPeach.png");
	public static final ImageIcon LAVA = new ImageIcon("images/lava.png");
	
	//these are icons that won't ever change and their purpose is to re-texture 
	//their corresponding icons and update them every time a new level is created. This is 
	//to prevent textures from carrying over from level 3 into other levels
	public static final ImageIcon DEFAULT_GRASS = new ImageIcon("images/marioGrassBlock.png");
	public static final ImageIcon DEFAULT_BORDER = new ImageIcon("images/marioGround.png");
	public static final ImageIcon DEFAULT_FAKEGRASS = new ImageIcon("images/marioGrassBlock.png");
	
	public static final ImageIcon titleImageIcon = new ImageIcon("images/marioP-Edition.gif");
	
	//mario images for walking, jumping, and falling
	public static final ImageIcon MARIO_RIGHT = new ImageIcon("images/mario running right.gif");
	public static final ImageIcon MARIO_LEFT = new ImageIcon("images/mario running left.gif");
	public static final ImageIcon MARIO_STANDING_RIGHT = new ImageIcon("images/mario "
															+ "standing right.png");
	public static final ImageIcon MARIO_STANDING_LEFT = new 
			ImageIcon("images/mario standing left.png");
	
	public static final ImageIcon MARIO_JUMPING_RIGHT = new 
			ImageIcon("images/mario jumping right.png");
	public static final ImageIcon MARIO_JUMPING_LEFT = new 
			ImageIcon("images/mario jumping left.png");
	public static final ImageIcon MARIO_FALLING_RIGHT = new 
			ImageIcon("images/mario falling right.png");
	public static final ImageIcon MARIO_FALLING_LEFT = new 
			ImageIcon("images/mario falling left.png");
	public static final ImageIcon MARIO_FIREBALL = new 
						ImageIcon("images/fireball.gif");
}
