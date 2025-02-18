/*
 * the purpose of this frame is to have the user
 * choose a level and have it load in. This will also restrict
 * the level they can play depending on which levels they have beat
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChooseLevelFrame extends JFrame implements ActionListener{
	
	//declaring Jcomponenets
	JLabel levelFrameTitle;
	JButton level1Button;
	JButton level2Button;
	JButton secretLevelButton;
	JPanel backgroundPanel;
	
	//constructor that makes teh frame
	public ChooseLevelFrame() {
		super();
		
		//creating title for cart frame
		//setting the dimensions, title, and other values for the frame
		this.setTitle("Level Choice");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1080, 610); 
		//setting setVisible to true to allow the frame to be visible
		this.setVisible(true);
		//Center the frame on the screen
        setLocationRelativeTo(null);
        
        //image icons for the level choices, bg image, and the title 
		ImageIcon backgroundImage = new ImageIcon("images/marioLevelChoiceBg.png");
		ImageIcon titleImageIcon = new ImageIcon("images/levelChoiceTitle.png");
		
		//checking if they beat the first level and if the level 2 button should be locked
		//or not. Only adding an action listener if they completed the conditions. This will
		//give a locked level or not
		if (SuperMarioApplication.lvl1Complete) {
			ImageIcon level2ImageIcon = new ImageIcon("images/marioLvl2Pic.png");
			
			//resizing the image so it fits well
			ImageIcon level2Image = new ImageIcon(level2ImageIcon.getImage().
					getScaledInstance(120, 120, Image.SCALE_DEFAULT));
			
			//making the level2Image JLabel, setting the bounds, and adding
			//action listener
			level2Button = new JButton(level2Image);
			level2Button.setBounds(250, 600, 100, 100);
			level2Button.addActionListener(this);	
		}
		
		else {
			ImageIcon level2ImageIcon = new ImageIcon("images/marioLvl2PicLocked.png");
			
			//resizing the image so it fits well
			ImageIcon level2Image = new ImageIcon(level2ImageIcon.getImage().
					getScaledInstance(120, 120, Image.SCALE_DEFAULT));
			
			//making the level2Image JLabel, setting the bound
			level2Button = new JButton(level2Image);
			level2Button.setBounds(250, 600, 100, 100);
		}
		
		//checking if they beat the second level and if the key was collected
		if (SuperMarioApplication.lvl2Complete && SuperMarioApplication.keyTaken) {
			ImageIcon levelSecretImageIcon = new ImageIcon("images/marioLvlSecretPic.png");
			
			//resizing the image so it fits well
			ImageIcon levelSecretImage = new ImageIcon(levelSecretImageIcon.getImage().
					getScaledInstance(120, 120, Image.SCALE_DEFAULT));
			
			//making the level1Image JLabel, setting the bounds, and adding
			//action listener
			secretLevelButton = new JButton(levelSecretImage);
			secretLevelButton.setBounds(200, 400, 100, 100);
			secretLevelButton.addActionListener(this);
		}
		
		else {
			ImageIcon levelSecretImageIcon = new ImageIcon("images/marioLvlSecretPicLocked.png");
			
			//resizing the image so it fits well
			ImageIcon levelSecretImage = new ImageIcon(levelSecretImageIcon.getImage().
					getScaledInstance(120, 120, Image.SCALE_DEFAULT));
			
			//making the level1Image JLabel, setting the bounds
			secretLevelButton = new JButton(levelSecretImage);
			secretLevelButton.setBounds(200, 400, 100, 100);
		}
		
		//making image icon for level 1
		ImageIcon level1ImageIcon = new ImageIcon("images/marioLvl1Pic.png");
		
		//ensuring its at a good size for the pictrues
		ImageIcon titleImage = new ImageIcon(titleImageIcon.getImage().
				getScaledInstance(1000, 200, Image.SCALE_DEFAULT));
		ImageIcon level1Image = new ImageIcon(level1ImageIcon.getImage().
				getScaledInstance(120, 120, Image.SCALE_DEFAULT));
		
		
		//set the background of the window as an image
		//https://stackoverflow.com/questions/19125707/
		//simplest-way-to-set-image-as-jpanel-background
		backgroundPanel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this); 
					//backgroundImage.getIconHeight(), this);
			}
		};
		
		backgroundPanel.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), 
						backgroundImage.getIconHeight()));
//		backgroundPanel.setLayout(new BorderLayout());
		
		//making the levelFrameTitle JLabel and setting the bounds
		levelFrameTitle = new JLabel(titleImage);
		levelFrameTitle.setBounds(1000, 200, 100, 100);
		
		//making the level1Image JLabel, setting the bounds, and adding
		//action listener
		level1Button = new JButton(level1Image);
		level1Button.setBounds(400, 400, 100, 100);
		level1Button.addActionListener(this);	
		
		//setting opaque to false so their is no white border around the button
//		level1Button.setOpaque(false);
//		level2Button.setOpaque(false);
//		secretLevelButton.setOpaque(false);
		
		//removing the white border around the buttons
		// https://stackoverflow.com/questions/2713190/how-to-remove-border-around-buttons
		level1Button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		level1Button.setContentAreaFilled(false);
		level2Button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		level2Button.setContentAreaFilled(false);
		secretLevelButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		secretLevelButton.setContentAreaFilled(false);
		
		//adding everyting to the background panel
		backgroundPanel.add(levelFrameTitle);
		backgroundPanel.add(level1Button);
		backgroundPanel.add(level2Button);
		backgroundPanel.add(secretLevelButton);
		
		
		//adding backgroundPanel to frame
		this.add(backgroundPanel);
		
		//making sure mario is fully reset
		SuperMarioApplication.marioDead = false;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//opening up correct level depending on which
		//they choose
		if (e.getSource() == level1Button) {
			this.dispose();
			new LevelFrame(1);	
		}
		
		else if (e.getSource() == level2Button) {
			this.dispose();
			new LevelFrame(2);
		}
		
		else if (e.getSource() == secretLevelButton) {
			this.dispose();
			new LevelFrame(3);
		}
		
		//setting it now instead of inside another class as this ensures the timer
		//resets properly every time a level is finished and even when its replayed
		LevelFrame.updateTimerCheck = true;
		LevelFrame.currentTime = System.currentTimeMillis();
		
	}

}
