package AICompare;

import java.util.List;

import AI.AlphaBeta;
import AI.PitChooser;

// This class is used to compare between the different Evaluations functions
// used for creating the Excel's graphs

import AIEvaluationFunctions.EvalFunctionsFactory;
import AIEvaluationFunctions.EvaluationFunction;

public abstract class ComparingPlayers {
	protected EvalFunctionsFactory funcsFactory;
	
	static protected final int NUM_FUNC = 7;
	static protected final int NUM_STONES = 4;
	
	protected AIMatch match; 
	protected float[][] results; // each cell represent the result of one game
	
	public static void main(String [] args)
	{
		
		ComparingPlayers compareFuncs = new CompringAIAgainstAI();
		compareFuncs.compareEvalFuncs();
		compareFuncs.printResults();
		System.out.println("**************************************************************");
		ComparingPlayers compareAIRand = new ComparingAIAgaintRandom();
		compareAIRand.compareEvalFuncs();
		compareAIRand.printResults();
		
	}
	
	public ComparingPlayers()
	{
		this.funcsFactory = new EvalFunctionsFactory();
	}
	
	// This method compering between the function and initialize the results matrix
	public abstract void compareEvalFuncs();
	
	
	protected void zeroingResults()
	{
		// This method initialize the results matrix to zeros
		
		for(int i = 0 ; i < this.results.length; i++)
			for(int j = 0 ; j < this.results[0].length; j++)
				results[i][j]= (float) 0.0;
	}
	
	protected void setPoitns(int winner, int i, int j)
	{
		// This method get a game results and the indexes of the players
		// if the player i has won -> results[i][j] = 1
		// if the player i has lost -> keep the value at results[i][j] to be 0
		// if its a tie -> results[i][j] = 0.5
		
		if(winner == 1 && match.getPlayer1Score() != -1)
			results[i][j] = 1; // i function won j function
		
		else if(winner == 0)
		{
			results[i][j] = (float) 0.5; 			
		}
	}
	
	public void printResults()
	{
		// This method prints the results
		
		String func;
		float sum = (float) 0.0;
		
		for(int i = 0; i < this.results.length; i++)
		{
			func = funcsFactory.functions.get(i);
			System.out.print(func + getSpaces(func, getLongestStringSize(funcsFactory.functions)) + ": ");
			
			for(int j = 0 ; j < this.results[0].length; j++)
			{
				if(i == j)
					System.out.format("%.2f , ", results[i][j]);
				else
					System.out.format("%.2f , ", results[i][j]);

				sum += results[i][j];
			}
			System.out.print("grade: " + sum);
			System.out.println("\n");
			sum = (float) 0.0;
		}
	}
	
	protected PitChooser getPitChooser(String func)
	{
		PitChooser chooserObj = new AlphaBeta();
		EvaluationFunction funcObj = funcsFactory.getNewProduct(func);
		chooserObj.setEvalFunc(funcObj);
		return chooserObj;
	}
	

	// This two functions are used in the print method
	
	protected static String getSpaces(String s, int len)
	{
		String spaces = "";
		for(int i = 0 ; i< len-s.length(); i++)
			spaces += ' ';
		return spaces;
	}
	
	protected static int getLongestStringSize(List<String >list)
	{
		int maxLen = 0;
		int curLen;
		for(int i = 0; i < list.size(); i++)
		{
			curLen = list.get(i).length();
				if( curLen > maxLen)
					maxLen = curLen;
		}
			
		return maxLen;
	}
}
