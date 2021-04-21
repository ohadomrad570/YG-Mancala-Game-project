package AICompare;

import myGame.GameState;
import myGame.Match;

// This class constitutes a match between two AI Players (Or Random Players)

public class AIMatch extends Match{

	public AIMatch(int StoneAmount,String player1ClassName,String player2ClassName, int thinkTime) {
		super(StoneAmount, player1ClassName ,player2ClassName , thinkTime);
	}
	
	@Override
	public int play()
	{
		// This method manage the game
		// Identical to Match's play method without delays
		
		player1Move = false;
		currentPlayer = player2;
		boolean playAgain = false;
		while (!currentState.gameOver()) {
			
			if (!playAgain) {
				player1Move = !player1Move;
				currentState.rotate();
			}
			
			if (player1Move) 
				currentPlayer = player1;
			else 
				currentPlayer = player2;
			
			currentPlayer.move(currentState);    
			int move = currentPlayer.getMove();
			
			if (currentState.illegalMove(move)) { //the player chose an illegal move, lose immediately
				if (currentPlayer == player1)
					return 2;
				else
					return 1;
			}
			
			playAgain = currentState.applyMove(move);  //effect the move on the board

		}// end while loop

		int status = currentState.status();

		if (currentPlayer == player1) {
			player1score = currentState.stoneCount(6);
			player2score = currentState.stoneCount(13);
			}
				
		else {
			player1score = currentState.stoneCount(13);
			player2score = currentState.stoneCount(6);
			}
		
		switch (status) {
		case GameState.GAME_OVER_WIN: 
			if (currentPlayer == player1) return 1;
			else return 2;
			case GameState.GAME_OVER_LOSE:
				if (currentPlayer == player1) return 2;
				else return 1;
				case GameState.GAME_OVER_TIE:
					return 0;
					default: return 0;
		}
	}
}
