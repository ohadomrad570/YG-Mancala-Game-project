package AIEvaluationFunctions;

import myGame.GameState;


//This evaluation function evaluate a state as 1 if its brings an additional turn else 0

//for amountStonesInPit = 4 -> [0,1]

public class MaxRepeatTurns implements EvaluationFunction {
	
	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		if(state.isExtraTurn())
			return 1;
		return 0;
	}

}
