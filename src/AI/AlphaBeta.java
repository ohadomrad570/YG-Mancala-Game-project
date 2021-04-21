package AI;

import AIEvaluationFunctions.EvaluationFunction;
import myGame.GameState;

// MiniMax with Alpha-Beta Purning

public class AlphaBeta extends CommanPitChooser{
	
	public AlphaBeta(EvaluationFunction evalFunc)
	{
		super(evalFunc);
	}
    
    public AlphaBeta()
    {
    	super();
    }
    
    @Override
    public int choosePit(GameState state) {
		
		/*I implement all the algorithm using IDS
		 * - Iterative deepening depth-first search - inorder to find the best move.
		 * The step starts from 1 and increments by step 1.
		 * The max Iterative Depth is MAX_DEPTH which means it calculates MAX_DEPTH moves ahead.
	     */
		
		int i = 1;	
		int move = 0;
        
		while(i <= MAX_DEPTH){
			MoveValue newMove = maxAction(state, i);
			move = newMove.getPit();
			i++;
		}
		
		return move;
	}
    
    // I wrote those two wrapper functions for ease to use
	
    //Return best move for max player
    public MoveValue maxAction(GameState state, int maxDepth) {
        	return maxAction(state, 0, maxDepth, -10000, 10000);
    	}
    
    //return best move for min player
    public MoveValue minAction(GameState state, int maxDepth) {
        	return minAction(state, 0, maxDepth, -10000, 10000);
    }
 
    //return best move for max player
    public MoveValue maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta) {
    	// Get: a state, the current depth, the max depth,
    	// alpha - the current best move for the max player (use by the min player)
    	// beta - the current best move for the min player (use by the max player)
    	// this way both player can pruning brunches of the tree
    	
    	MoveValue newMove = new MoveValue(-10001, 1); // The current "root" (a node in the tree)
    	if(state.gameOver() || currentDepth == maxDepth){
    		newMove.setPit(14);
        	newMove.setValue(this.evalFunc.eval(state));
        	return newMove;
        }
    	int v = -10000;
    	for(int pit = 5; pit >= 0; pit--){ // for each node has maximum 6 sons - 6 pits - 6 move options
    		if(!state.illegalMove(pit)){ // if the move is legal
    			GameState copy = new GameState(state);   // create a copy of the current state
    			boolean extraTurn = copy.applyMove(pit);  // apply the move
        			
    			int beforeV = v;
    			if(!extraTurn)
    				v = Math.max(v, minAction(copy, currentDepth + 1, maxDepth, alpha, beta).getValue());
    			else
    				v = Math.max(v, maxAction(copy, currentDepth + 1, maxDepth, alpha, beta).getValue());
        			
    			if(beforeV < v){ // if the we found a better option - update the node value and the chosen pit
    				newMove.setValue(v);
    				newMove.setPit(pit);
    			}
    			
    			alpha = Math.max(alpha, v);
    			if(v > beta)			// if we found a value that bigger than beta we know that
    			                   	    //the min player will not choose this brunch
    				return newMove;
    			}	
    	}   	
    	return newMove;
    }
    
    //return best move for min player
    public MoveValue minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta){
    	// Get: a state, the current depth, the max depth,
    	// alpha - the current best move for the max player (use by the min player)
    	// beta - the current best move for the min player (use by the max player)
    	// this way both player can pruning brunches of the tree
    	
    	MoveValue newMove = new MoveValue(10001, 1);
    	if(state.gameOver() || currentDepth == maxDepth){
    		newMove.setPit(14);
    		newMove.setValue(this.evalFunc.eval(state));
    		return newMove;
    	}
    	
    	int v = 10000;
    	for(int pit = 12; pit > 6; pit--){
    		if(!state.illegalMove(pit)){
    			GameState copy = new GameState(state);
    			boolean extraTurn = copy.applyMove(pit);
    			int beforeV = v;
    			if(!extraTurn)
    				v = Math.min(v, maxAction(copy, currentDepth + 1, maxDepth, alpha, beta).getValue());
    			else
    				v = Math.min(v, minAction(copy, currentDepth + 1, maxDepth, alpha, beta).getValue());
        			
    			if(beforeV > v){
    				newMove.setValue(v);
    				newMove.setPit(pit);
    			}
    			
    			beta = Math.min(beta, v);
    			if(v < alpha)
    				return newMove;
    			
    		}
    	}
        	return newMove;
    }
}
