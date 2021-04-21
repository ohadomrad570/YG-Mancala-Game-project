package AIEvaluationFunctions;

import myGame.GameState;
import myGame.Match;

//This evaluation function 
//refer to two parameters:
//						1) The distance of the chosen pit from the home pit - closer - higher grade
// 						2) if the move brings an additional turn - if their- +6 points
//grade = eval(pit, additional turn)
// examples:
//			eval(1, false) = 1
//			eval(6, false) = 6
//			eval(1, true) = 7
//			eval(6, true) = 12
//
//for amountStonesInPit = 4 -> [1,12]

public class Mixed implements EvaluationFunction{

	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		int retVal = state.getLastMove() + 1;
		if(state.isExtraTurn())
			retVal += Match.numRegularPits;
		return retVal;
	}

}
