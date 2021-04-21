package AIEvaluationFunctions;

import myGame.GameState;
import myGame.Match;

//This evaluation function calculate 
//the the number of stones at the opponent's home pit
//Looks only on the opponent progress
// for amountStonesInPit = 4 -> [0,48]

public class MinOpponentPoints implements EvaluationFunction{
	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		return 2*GameState.numRegularPits*Match.stoneAmountInPit - state.stoneCount(13);
	}	
}

