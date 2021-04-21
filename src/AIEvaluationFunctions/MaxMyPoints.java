package AIEvaluationFunctions;

import myGame.GameState;

//This evaluation function calculate 
//the the number of stones at the AI's home pit
// Looks only on the AI progress

//for amountStonesInPit = 4 -> [0,48]

public class MaxMyPoints implements EvaluationFunction{

	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		return state.stoneCount(6);
	}

}
