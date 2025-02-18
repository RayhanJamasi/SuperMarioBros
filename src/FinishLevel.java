/*
 * The purpose of this frame is to have a congratulation message for when
 * the user finishes a level. They can click a button and go back to the 
 * menu after
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinishLevel extends JFrame implements ActionListener{
	
	//declaring JComponents
	JPanel backgroundPanel;
	JButton menuButton;
	JLabel congratsLabel;
	
	//constructor that creates the JFrame
	public FinishLevel() throws HeadlessException {
		super();
		
		
		//creating title for the frame
		//setting the dimensions, title, and other values for the frame
		this.setTitle("Super Mario World");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600); 
		//setting setVisible to true to allow the frame to be visible
		this.setVisible(true);
		//Center the frame on the screen
        setLocationRelativeTo(null);
		
		//image icons for settings gear icon, background image, and start button
		ImageIcon backgroundImage = new ImageIcon("images/marioFinishLvlScreen.jpg");
		ImageIcon congratsImageIcon = new ImageIcon("images/congratsMsgMario.png");
		ImageIcon menuImageIcon = new ImageIcon("images/menuTextMario.png");
		
		//ensuring its at a good size
		ImageIcon congratsImage = new ImageIcon(congratsImageIcon.getImage().
				getScaledInstance(700, 300, Image.SCALE_DEFAULT));
		ImageIcon menuImage = new ImageIcon(menuImageIcon.getImage().
				getScaledInstance(150, 70, Image.SCALE_DEFAULT));
		
		//set the background of the window as an image
		//https://stackoverflow.com/questions/19125707/
		//simplest-way-to-set-image-as-jpanel-background
		backgroundPanel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this); 

			}
		};
		backgroundPanel.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), 
						backgroundImage.getIconHeight()));
		
		//creating the congrats label
		congratsLabel = new JLabel(congratsImage);
		congratsLabel.setBounds(250, 600, 200, 200);
		
		//creating menu button
		menuButton = new JButton(menuImage);
		menuButton.setBounds(300, 500, 80, 50);
		menuButton.addActionListener(this);
		
		//adding to frame
		backgroundPanel.add(congratsLabel, BorderLayout.CENTER);
		backgroundPanel.add(menuButton, BorderLayout.SOUTH);
		this.add(backgroundPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//opening menu frame if they click the menu button
		if (e.getSource() == menuButton) {
			this.dispose();
			
			//resetting important variables and timers
			//for the level
			Charectar.coinsCollected = 0; 
			Charectar.coinsFinished = false;
			
			//ensuring that everything is null in the frame
			//NECCESARY?
			for (int row = 0; row < LevelFrame.boardArray.length; row++) {
				for (int col = 0; col < 25; col++) {
					LevelFrame.boardArray[row][col].setIcon(null);
				}
			}
			
			//calling the title frame
			new SuperMarioTitleFrame();
			
		}
		
	}

}
