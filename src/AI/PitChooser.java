package AI;

import AIEvaluationFunctions.EvaluationFunction;
import myGame.GameState;

// This interface describe the Functionality of a tree parser algorithm 

public interface PitChooser {

	public int choosePit(GameState state);
	public void setEvalFunc(EvaluationFunction evalFunc);
}
