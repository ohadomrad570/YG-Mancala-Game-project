package AICompare;

import myGame.GameState;
import myGame.MancalaGame;

// This class comparing the evaluations functions by let them play againg one another

public class CompringAIAgainstAI extends ComparingPlayers{
	
	
	public CompringAIAgainstAI()
	{
		super();
		this.match = new AIMatch(NUM_STONES, "AIPlayer", "AIPlayer",MancalaGame.TIME_LIMIT);
		this.results = new float[NUM_FUNC][NUM_FUNC];
		
		zeroingResults();
	}

	@Override
	public void compareEvalFuncs()
	{		
		// This method run all the games between all the different agents
		// and put the results in results matrix
				
		// in the cell [i][j] will be the result of the game - i agent VS j agent -
		// when the player i has done the first move
		
		for(int i = 0; i < this.results.length; i++)
		{
			String func1 = funcsFactory.functions.get(i);
			match.setPlayer1PitChooser(getPitChooser( func1));
			
			for(int j = 0 ; j < this.results[0].length; j++)
			{
				String func2 = funcsFactory.functions.get(j);
				
				if(!func1.equals(func2))
				{
					match.setPlayer2PitChooser(getPitChooser(func2));
					
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
		
		analyzeResults();
		
	}
	
	private void analyzeResults()
	{
		// This method analyze the games results
		// and set the games result by according to the difference between the game i VS j and j VS i
		// if i has won j (i started) and j has won i (j started): 
		//			->	each one will gel a 0.5 points
		// if i has won j (i started) and j against i brought a tie (j started)
		//			->	i will get 0.75 point and j will get 0.25 point
		
		for(int i = 0 ; i<NUM_FUNC;i++)
			for(int j = i ; j < NUM_FUNC; j++)
			{
				if(results[i][j] == 1 && results[j][i] == 1)
					results[i][j] = results[j][i] = (float) 0.5;
				
				else if (results[i][j] == (float)1 && results[j][i] == (float)0.5)
				{
					results[i][j] = (float)0.75;
					results[j][i] = (float)0.25;
				}
				else if (results[i][j] == (float)0.5 && results[j][i] == (float)1)
				{
					results[i][j] = (float)0.25;
					results[j][i] = (float)0.75;
				}
					
			}
	}

}
