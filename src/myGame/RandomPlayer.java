package myGame;

//This class define a random player in the game

public class RandomPlayer extends Player{

	@Override
	public void move(GameState context) {
    	
    	int random = (int )(Math.random() * 6);
    	while(context.illegalMove(random))
    		random = (int )(Math.random() * 6);		
    	move = random;	
	}
}
