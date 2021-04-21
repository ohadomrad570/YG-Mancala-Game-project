package AIEvaluationFunctions;

import myGame.GameState;

//This evaluation function calculate 
//the the number of stones at the AI's all pits (including his home pit)
//Looks only on the AI progress

//for amountStonesInPit = 4 -> [0,48]

public class MaxMyPlayingStones implements EvaluationFunction{

	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		return state.stoneCount(6) +  state.stoneCount(5) + state.stoneCount(4) + state.stoneCount(3) + 
				state.stoneCount(2) + state.stoneCount(1) + state.stoneCount(0); 
	}

}
