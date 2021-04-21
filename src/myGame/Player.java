package myGame;

// This class define a player in the game

public abstract class Player {
    protected int move;  //stores the current best move for the player
    
   public Player() {
        	move = 0;
    }

    public abstract void move(GameState context);

	public int getMove() {
	return move;
    }
}
