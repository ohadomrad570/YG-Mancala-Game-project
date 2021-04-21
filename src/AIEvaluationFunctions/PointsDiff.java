package AIEvaluationFunctions;
import myGame.GameState;

// This evaluation function calculate 
//the difference between the number of stones at the home pits of the two players
// for amountStonesInPit = 4 -> [-48,48]


public class PointsDiff implements EvaluationFunction {
	@Override
	public int eval(GameState state) {
		// Note: the rows has been rotate (the lower row belongs to the AI)
		int AIStones = state.stoneCount(6);
		int rivalStones = state.stoneCount(13);
		return AIStones - rivalStones;
	}
}
