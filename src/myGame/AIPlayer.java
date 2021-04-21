package myGame;

import AI.PitChooser;

//This class define the AI player in the game

public class AIPlayer extends Player {
	
	private PitChooser chooser;	
	
	public AIPlayer()
	{
		chooser = null;
	}	
	
	public void setChooser(PitChooser chooser) { this.chooser = chooser; }

	@Override
	public void move(GameState context) {
		// Bridge Design Pattern
		 this.move = chooser.choosePit(context);
	}
}
