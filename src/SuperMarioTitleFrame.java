/*
 * The title frame for the mario game. The main purpose of this
 * is to just have a short clip of mario running and the play button that
 * leads to the level choice screen
 */

import java.awt.Dimension;
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

public class SuperMarioTitleFrame extends JFrame implements ActionListener{
	
	//declaring JComponenets
	JPanel backgroundPanel;
	JButton startButton;
	JLabel titleLabel;
	
	//constructor that builds the frame
	public SuperMarioTitleFrame() throws HeadlessException {
		super();
		
		//image icons for settings gear icon, background image, and start button
		ImageIcon backgroundImage = new ImageIcon("images/superMarioBrosWorld.gif");
		ImageIcon titleImageIcon = new ImageIcon("images/marioP-Edition.gif");
		ImageIcon startImageIcon = new ImageIcon("images/marioStartText.gif");
		
		//ensuring its at a good size
		ImageIcon titleImage = new ImageIcon(titleImageIcon.getImage().
				getScaledInstance(250, 70, Image.SCALE_DEFAULT));
		ImageIcon startImage = new ImageIcon(startImageIcon.getImage().
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
		
		//creating title for the frame
		//setting the dimensions, title, and other values for the frame
		this.setTitle("Super Mario World");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1200, 625); 
		//setting setVisible to true to allow the frame to be visible
		//Center the frame on the screen
        setLocationRelativeTo(null);

		
		//creating the title image and start image
		titleLabel = new JLabel(titleImage);
		titleLabel.setBounds(250, 600, 200, 200);
		
		startButton = new JButton(startImage);
		startButton.setBounds(300, 500, 80, 50);
		startButton.addActionListener(this);
		
		//adding to frame
		backgroundPanel.add(titleLabel);
		backgroundPanel.add(startButton);
		this.add(backgroundPanel);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//going to choose level frame when they click it 
		if (e.getSource() == startButton) {
			this.dispose();
			new ChooseLevelFrame();
		}
		
	}

}
