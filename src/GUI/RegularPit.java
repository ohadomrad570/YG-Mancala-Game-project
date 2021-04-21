package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import myGame.HumanPlayer;

// This class constitutes a regular pit on the GUI

public class RegularPit extends Pit{
	
	protected boolean mouseOver;	
	protected int index;
	protected boolean isLower;
	
	public RegularPit( boolean isLower, int index, BoardFormat format)
	{
		super(format);
		this.index = index;
		this.isLower = isLower;
		mouseOver = false;
	}
	
	public void paintComponent(Graphics g)
	{
		// This method paint a regular pit in the GUI
		// By using a format object
		
	      super.paintComponent(g);
	      Graphics2D g2 = (Graphics2D) g;
	      if(mouseOver)
	    	  g2.setColor(Color.blue);
	      else
	    	  g2.setColor(format.getColor());
	      g2.draw(format.getRegularPitShape());
	      g2.setColor(format.getFillColor());
	     
	      lock.lock();
	      for (Shape s : format.getRegularPitStoneShapes(numStones))
	    	  g2.fill(s);
	      lock.unlock();
	   }
	

	
	protected void createListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boolean selected = (myGame.Match.player1Move && ((RegularPit)e.getSource()).index < 6) ||  (!myGame.Match.player1Move && ((RegularPit)e.getSource()).index >= 6);
				if (selected) {
					mouseOver = true;
					refresh();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				boolean selected = (myGame.Match.player1Move && ((RegularPit)e.getSource()).index < 6) ||  (!myGame.Match.player1Move && ((RegularPit)e.getSource()).index >= 6);
				if (selected) {
					mouseOver = false;
					refresh();
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean selected = (myGame.Match.player1Move && ((RegularPit)e.getSource()).index < 6) ||  (!myGame.Match.player1Move && ((RegularPit)e.getSource()).index >= 6);
				if (selected) {
					mouseOver = false;
					HumanPlayer.choice = ((RegularPit)e.getSource()).index;
				}
			}
		});
	}
}
