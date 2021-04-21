package AIEvaluationFunctions;

import java.util.Arrays;
import java.util.List;

import myGame.GenericFactory;
// This class constitutes the functions factory
// in that we can get a product at time of O(1)

public class EvalFunctionsFactory extends GenericFactory<EvaluationFunction>{
	
	
	public List<String> functions;
	
	public EvalFunctionsFactory()
	{
		String [] array = {"Max My Points","Min Opponent Points","Points Diff","Max My Playing Stones",
				"Stones Diff","Max Repeat Turns", "Mixed" };
		
		functions = Arrays.asList(array);
		
		insertProduct("Max My Points", MaxMyPoints.class);
		insertProduct("Min Opponent Points", MinOpponentPoints.class);
		insertProduct("Points Diff",PointsDiff.class);
		insertProduct("Max My Playing Stones", MaxMyPlayingStones.class);
		insertProduct("Stones Diff", StonesDiff.class);
		insertProduct("Max Repeat Turns", MaxRepeatTurns.class);
		insertProduct("Mixed", Mixed.class);
	}

}
