package myGame;

//This Class define a state of the board and has the needed functionality

public class GameState{

	public static final int numRegularPits = 6;      // The number of regular pits of each side
	public static final int numAllPits = 14;         // The total number of pits
	
	// the game status is define from the first player point of view
    public static final int GAME_NOT_OVER  = Integer.MIN_VALUE;
    public static final int GAME_OVER_WIN  =  1;
    public static final int GAME_OVER_TIE  =  0;
    public static final int GAME_OVER_LOSE = -1;
    
    protected boolean extraTurn;
    protected int lastMove;

	protected int[] state = { 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0 };
    /*
     * state array define the game state in this format 
     * player one pits: 0-6
     * player two pits: 7-13
    -----------------------------------------
    |    | 12 | 11 | 10 |  9 |  8 |  7 |    |
    | 13 |-----------------------------|  6 |
    |    |  0 |  1 |  2 |  3 |  4 |  5 |    |
    -----------------------------------------
    */

    public GameState(int initialStones) 
    {
    	// The constructor initializing the state
    	// Get: initialStones - the number of stones in each pit at the start of the game
    	if (initialStones < 1) initialStones = 4;
    	for (int i=0; i<6; i++)
    		state[i] = state[i+7] = initialStones;
    	extraTurn = false;
    }
    
    public GameState(int[] state) {
		this.state = state;
    	extraTurn = false;
	}
    
	protected GameState() {extraTurn = false;}
	
    public GameState(GameState source) 
    {
    	this.state = arrayCopy(source.state);
    	extraTurn = false;
    }
    
    public static int[] arrayCopy(int[] source) {
    	// This method return a copy of source array
    	if (source == null)
    		return null;
    	int[] copy = new int[source.length];
    	for (int i = 0; i < source.length; ++i)
    		copy[i] = source[i];
    	return copy;
    }
    
    public int[] getState() {
		return state;
	}
	
    public int status() 
    {
    	// This method return the game status

    	// case 1:
    	//			if their are still stones on both players regulars pits
    	if (stonesCount(7,12) != 0 && stonesCount(0,5) != 0) 
    		return GAME_NOT_OVER;
    	
    	// cases 2-4:
    	//			its a game over
    	//			if for one of the players has stones in his regular pits, they will be moved to his home pit
    	
    	// case 2:	
    	//			if the first player has more stones at his side (including the home pit), he has won
    	else if (stonesCount(7,13) < stonesCount(0,6))
    		return GAME_OVER_WIN;
    	
    	// case 3:
    	//			if the both player has the same number of pits
    	else if (stonesCount(7,13) == stonesCount(0,6))
    		return GAME_OVER_TIE;
    	
    	// case 4:
    	//		otherwise the second player has won
    	else
    		return GAME_OVER_LOSE;
    }
    
    
    public boolean gameOver() {
    	// This method checks if the game is over by calling to the status() method
    	// return true if the game is not over
    	  return status() != GAME_NOT_OVER;
    }
    

    public int[] toArray() {
    	// This method return a deep copy of the state array
    	  return arrayCopy(state);
    }
    
    
    public int[][] toMatrix() 
    {
    	 /*
         This method return a copy of the state as a matrix of integers at this format:
         -------------------------------------------------
         |     |(1,5)|(1,4)|(1,3)|(1,2)|(1,1)|(1,0)|     |
         |(1,6)|-----------------------------------|(0,6)|
         |     |(0,0)|(0,1)|(0,2)|(0,3)|(0,4)|(0,5)|     |
         -------------------------------------------------
         */
    	
      	int[][] matrix = new int[2][7];
      	for (int i = 0; i < 14; ++i)
      		matrix[i/7][i%7] = state[i];
      	return matrix;
    }
    
    public int stoneCount(int pit) {
    	/* This method return the number of stones in a given pit, 
    	 * where the pit numbers are indexed in the following way:
    	-----------------------------------------
     	|    | 12 | 11 | 10 |  9 |  8 |  7 |    |
     	| 13 |-----------------------------|  6 |
     	|    |  0 |  1 |  2 |  3 |  4 |  5 |    |
     	-----------------------------------------
    	Get: the pit index
    	*/
    	  return state[pit];
    }
    

    public int stoneCount(int pit1, int pit2){
    	/* This method return the number of stones in a given pit, 
    	 * where the pit numbers are indexed in the following way:
     	-------------------------------------------------
     	|     |(1,5)|(1,4)|(1,3)|(1,2)|(1,1)|(1,0)|     |
     	|(1,6)|-----------------------------------|(0,6)|
     	|     |(0,0)|(0,1)|(0,2)|(0,3)|(0,4)|(0,5)|     |
     	-------------------------------------------------
    	Get: pit1 the first coordinate, pit2 the second coordinate
    	*/
      	return state[7*pit1 + pit2];
    }
    
    public boolean illegalMove(int pit) {
    	// Get: the chosen pit (the chosen move)
    	// This method checks if the move is illegal.
    	// For efficiency, this method does not take into account state status or turns
    	// return true if the true if the move is illegal, otherwise return false
    	
      	return (state[pit] == 0 || pit < 0 || pit == 6 || pit > 12);
    }
    

    public boolean applyMove(int pit) 
    {
    	/*
    	 * Get: the pit number (the chosen move)
    	 * This method applies the given move to the given state
    	 * and return true if the player gets an additional turn, false otherwise
    	 * */
    	this.lastMove = pit;
    	int stones = state[pit];
        //clear the original bin
        state[pit] = 0;
        	
        for (int i = 0; i < stones; ++i) 
        {
        	int nextPit = (pit+i+1)%14; // the module is for a circular progress
        	// we will put one stone in the next pit only if its not the opponent's home pit
        	if (!(nextPit == 6 && pit > 6) && !(nextPit == 13 && pit < 7))
        		++state[nextPit];
        	else
        		// add an additional iteration - skipped on the opponent's home pit
        		++stones;
        }
        
        int lastPit = (pit+stones)%14;
        boolean lastPitEmpty = state[lastPit] == 1;
        boolean lastPitOnYourSide = pit/7 == lastPit/7;
        
        // if the last stones has been was placed in the current player's home pit
        // he gets an additional turn
        if ((lastPit == 6 || lastPit == 13) && !gameOver()) 
        {
        	extraTurn = true;
        	return true;
        }
        
    	// "eating"
        if (lastPitEmpty && lastPitOnYourSide && lastPit != 6 && lastPit != 13) 
        {
        	int mancalaPit =  mancalaOf(pit);
        	int neighborPit = neighborOf(lastPit);
        	state[mancalaPit] += state[neighborPit] + 1;
        	state[neighborPit] = 0;
        	state[lastPit] = 0;
        }
        
        
        if (gameOver())
            stonesToMancalas();
        
        extraTurn = false;
        return false;
    }
    
    public void rotate() 
    {
    	// This method switches the board's current perspective so that the bottom and top rows switch
    
    	int[] rotatedState = new int[state.length];
    	for (int i = 0; i < state.length; ++i)
    		rotatedState[(i+7)%14] = state[i];
    	state = rotatedState;
    }
    
    private int stonesCount(int pit1, int pit2) {
    	// This method gets a two pits indexes and returns the number of stones between them
    	// The range: [pit1, bin2]
    	int stones = 0;
    	for (int i = pit1; i <= pit2; ++i)
    		stones += state[i];
    	return stones;
    }
    
    public void stonesToMancalas() {
    	
    	// This method move all stones  players's home pits
    	// The stones in player1's regular pits will be move to his home pit and
    	// the stones in player2's regular pits will be move to his home pit 

    	state[6]  += stonesCount(0,5);
    	state[13] += stonesCount(7,12);
    	for (int i = 0; i < 6; ++i) {
    		state[i] = 0;
    		state[i+7] = 0;
      }
    }

    public int neighborOf(int pit) {	
    	/* 
    	 * Get: the pit index
    	-----------------------------------------
     	|    | 12 | 11 | 10 |  9 |  8 |  7 |    |
     	| 13 |-----------------------------|  6 |
     	|    |  0 |  1 |  2 |  3 |  4 |  5 |    |
     	-----------------------------------------
    	This method return the neighbor of the given pit
    	The neighbors pairs: (1,12), (1,11), (2,10), (3,9), (4,8), (5,7)
    	*/
    	
    	if (pit == 13)
    		return pit;
    	else
    		return 12-pit;
    } 
    
    public int mancalaOf(int pit) {
    	// Get: a pit number
    	// This method return the mancala/home pit that the pit is belongs to
    	return pit / 7 * 7 + 6;
    }
    
    private static String toString(int stones) {
    	if (stones < 10)
    		return "  " + stones + " |";
    	else
    		return " " + stones + " |";
    }

    public String toString() {
    	// This method return a string that representing the state
    	
    	StringBuffer buffer = new StringBuffer();
    	//	buffer.append("        6    5    4    3    2    1       \n");
    	buffer.append("+---------------------------------------+\n");	
    	buffer.append("|    |");
    	
    	for (int i = 12; i >= 7; --i) 
    		buffer.append(toString(state[i]));
    	
    	buffer.append("    |\n");
    	buffer.append("|");
    	buffer.append(toString(state[13]));
    	buffer.append("-----------------------------|");
    	buffer.append(toString(state[6]));
    	buffer.append("\n");
    	buffer.append("|    |");
	
    	for (int i = 0; i <= 5; ++i) 
    		buffer.append(toString(state[i]));
	
    	buffer.append("    |\n");
    	buffer.append("+---------------------------------------+\n");
    	buffer.append("        0    1    2    3    4    5       \n");
	
    	return buffer.toString();
    }

	public boolean isExtraTurn() {
		return extraTurn;
	}
	
    public int getLastMove() {
		return lastMove;
	}
}
