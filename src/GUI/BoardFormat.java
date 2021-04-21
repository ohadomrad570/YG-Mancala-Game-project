package GUI;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

// This class define the board format

public class BoardFormat
{
	public Shape getHomePitShape()
	{
	   return new Rectangle2D.Double(25, 0, BoardPanel.PLAYER_PIT_WIDTH,  BoardPanel.PLAYER_PIT_HEIGHT);
	}

	public Shape getRegularPitShape()
	{
	   return new Rectangle2D.Double(5, 5, BoardPanel.REGULAR_PIT_WIDTH, BoardPanel.REGULAR_PIT_HEIGHT);
	}

	
	public Shape [] getRegularPitStoneShapes(int stoneAmount)
	{
		// This method return a list of the stones of a regular pit as GUI Rectangle2D
		// This method get the number of stones in the pit
		// And build rectangles in a size and coordinates that fit the amount of stones
		
		if (stoneAmount == 0)
			return new Shape [] { new Rectangle2D.Double(0,0,0,0) };
		 
		ArrayList<Rectangle2D.Double> shapeList = new ArrayList<Rectangle2D.Double>();
		// the number of stones that will be in a row
		int dimension = (int) (Math.sqrt(stoneAmount) + 1); 
		int spaceBetweenRect = 10;
		int width = ( BoardPanel.REGULAR_PIT_WIDTH / (dimension) ) - 7;
		int height = ( BoardPanel.REGULAR_PIT_HEIGHT / (dimension) ) - 7;
		   
		for (int i = 0; i < stoneAmount; i++)
		{
			int xMod = (i % dimension); // the stone column
			int yMod = (i / dimension);  // the stone row
			int x = xMod * (width + 4) + spaceBetweenRect; // the stone's x coordinates
			int y = yMod * (height + 4) + spaceBetweenRect; // the stone's y coordinates
			shapeList.add(new Rectangle2D.Double(x, y, width, height));
		}
		   
		Rectangle2D.Double [] rectList = new Rectangle2D.Double[shapeList.size()];
		rectList = shapeList.toArray(rectList);
		return rectList;
	}

	   
	public Shape [] getHomePitStoneShapes(int stoneAmount)
	{
		// This method return a list of the stones of a home pit as GUI Rectangle2D
		// This method get the number of stones in the pit
		// And build rectangles in a size and coordinates that fit the amount of stones
		
		if (stoneAmount == 0)
			return new Shape [] { new Rectangle2D.Double(0,0,0,0) };
		   
		ArrayList<Rectangle2D.Double> shapeList = new ArrayList<Rectangle2D.Double>();
		   
		// the number of stones that will be in a row

		int dimension = (int) (Math.sqrt(stoneAmount) + 1);
		int width = BoardPanel.PLAYER_PIT_WIDTH / (dimension) - 7;
		int height = BoardPanel.PLAYER_PIT_HEIGHT / (dimension) - 7;
		   
		for (int i = 0; i < stoneAmount; i++)
		{
			int xMod = (i % dimension);
			int yMod = (i / dimension);
			int x = xMod * (width + 4) + 30;
			int y = yMod * (height + 4) + 5;
			shapeList.add(new Rectangle2D.Double(x, y, width, height));
		}
		   
		Rectangle2D.Double [] rectList = new Rectangle2D.Double[shapeList.size()];
		rectList = shapeList.toArray(rectList);
		return rectList;
	}

	
	public Color getColor()
	{
		return Color.darkGray;
	}

	
	public Color getFillColor()
	{
		return Color.PINK;
	}
}
