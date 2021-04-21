package myGame;

//This class define a human player in the game

public class HumanPlayer extends Player{
	public static final int SLEEP_TIME = 20;
	public static int choice = -1;
	
	@Override
    public void move(GameState context)
    {
    	//reset the choice from board
    	choice = -1;
    	    	
    	int random = (int )(Math.random() * 6);
    	while(context.illegalMove(random))
    		random = (int )(Math.random() * 6);
    		
    	move = random;
    	//try to get the choice from GUI
    	
    	// The method play() in match class define a timeout of 5 minutes
    	
    	// in this loop we check every 20 milli seconds
    	//if the human player has clicked on a pit of the GUI
    	// if the player hasn't chose a pit, and already passed a 5 minutes
    	// The moving Thread will stay alive
    	while (choice == -1)
    	{
    		Utility.tSleep(SLEEP_TIME);
    	}
    	
    	//transform from the index of the bins to the choice
    	if (choice < 6)
    		move = choice;
    	else
    		move = choice - 6;
    	choice = -1;
    }
}
