package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

//This class constitutes a player home pit in the GUI

public class HomePit extends Pit{

	private String playerName;
	
	public HomePit(BoardFormat format, String playerName)
	{
		super(format);
		this.playerName = playerName;
	}
	

	public void paintComponent(Graphics g)
	{
		// This method paint the player's pit in the GUI
		// By using a format object
		
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setColor(format.getColor());
	    g2.draw(format.getHomePitShape());
	    g2.setColor(format.getFillColor());
	    for (Shape s : format.getHomePitStoneShapes(numStones))
	    	g2.fill(s);
	    
	    g2.setColor(Color.BLACK);
	    g2.drawString(playerName, 50, BoardPanel.PLAYER_PIT_HEIGHT + 20);
	 }
	   
}