package AIEvaluationFunctions;

import myGame.GameState;
//This evaluation function calculate 
//the difference between the number of stones at all pits
//for amountStonesInPit = 4 -> [-48,48]

public class StonesDiff implements EvaluationFunction{
	
	@Override
	public int eval(GameState state) {
		return (int)(
    			(state.stoneCount(6) - state.stoneCount(13))+ // Diff between the home pits
    			// Diff between the regular pits
    			((state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0))
    			-(state.stoneCount(12) + state.stoneCount(11) + state.stoneCount(10) + state.stoneCount(9) + state.stoneCount(8) + state.stoneCount(7))
    			));
	}
}
