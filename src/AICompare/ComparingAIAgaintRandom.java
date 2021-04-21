package AICompare;

import myGame.GameState;
import myGame.MancalaGame;

//This class comparing the evaluations functions (AI - Alpha-Beta)to a random player


public class ComparingAIAgaintRandom extends ComparingPlayers {

	public ComparingAIAgaintRandom()
	{
		super();
		this.match = new AIMatch(NUM_STONES, "AIPlayer", "RandomPlayer",MancalaGame.TIME_LIMIT);
		this.results = new float[NUM_FUNC][10];
		
		zeroingResults();
	}
	
	@Override
	public void compareEvalFuncs() {
		// This method run all the games between all the different agents against the Random Player
		// who picks a random moves
		
		// and put the results in results matrix
						
		// in the cell [i][j] will be the result of the game - i agent VS j agent -
		// when the player i has done the first move
				
		for(int i = 0; i < this.results.length; i++)
		{
			String func = funcsFactory.functions.get(i);
			match.setPlayer1PitChooser(getPitChooser(func));
			
			for(int j = 0 ; j < this.results[0].length; j++)
			{		
				int winner = -1;
				try {
					winner = match.play();
					} catch (Exception e) {
					
						e.printStackTrace();
					}

				setPoitns(winner, i, j);
							
				match.setCurrentState(new GameState(NUM_STONES));
			}
		}
	}
}

