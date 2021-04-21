package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

// This class constitutes a panel with BachGround Image

public class BackGroundPanel extends JPanel {
	private String imagePath;
	private Image image;
	
	public BackGroundPanel(String imagePath)
	{
		this.imagePath = imagePath;
		image = (new ImageIcon(this.imagePath)).getImage();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		setOpaque(false);
		super.paintComponent(g);
		setOpaque(true);
	}
}
