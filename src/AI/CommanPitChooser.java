package AI;

import AIEvaluationFunctions.EvaluationFunction;
import myGame.GameState;

// This abstract class implements the functionality that the all pit choosers shared

public abstract class CommanPitChooser implements PitChooser{
	
    public static int MAX_DEPTH = 5;

	protected EvaluationFunction evalFunc;
	
	public CommanPitChooser() { this.evalFunc = null; }
	
	public CommanPitChooser( EvaluationFunction evalFunc) {setEvalFunc(evalFunc); }

	@Override
	public void setEvalFunc(EvaluationFunction evalFunc) { this.evalFunc = evalFunc; }	
	
    public abstract int choosePit(GameState state);
}
