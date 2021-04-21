package myGame;

import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import GUI.BoardPanel;
import GUI.GUI;

//This class is the main class that run the application

public class MancalaGame
{
	 // Time limit each player has to decide on a move (default: 3 secs)
    public static final int TIME_LIMIT  = 3;
    
    //Pausing time when moving stones among pits
    
    public static final int SLEEP_TIME = 400;
    
    public static Object lock = new Object();
	public static GUI gui = null;
	public static BoardPanel boardPanel;
	public static Match match = null;
	public static String player1DisplayName = "";
	public static String player2DisplayName = "";
		
	public static void main(String [] args)
	{	
		initGame();
	}
	
	public static void initGame()
	{
		// This method Initializing the game
		
		synchronized (lock) {
			
		// Run the GUI on the diffrent Thread
	
		 SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					gui = new GUI();
					CardLayout cardLayout = (CardLayout) gui.cards.getLayout();
					cardLayout.show(gui.cards, "open");
					boardPanel = gui.boardPanel;
				}
			});
		 
		 try {
			 // Wait until the match object will be initialize
			lock.wait();}
		 catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		game();
	}
	
	public static void game()
	{	
		// This method manage the game using the play() method
		
		int winner = match.play();
		String text = null;
    	switch (winner) 
    	{
    	case 1: 	
    		text = player1DisplayName+" has won the match " + match.getPlayer1Score() + " to " +
    					match.getPlayer2Score();
    		boardPanel.textArea.append(text + "\n");
    		break;
    	case 2: 
    		text =player2DisplayName+" has won the match " + match.getPlayer2Score() + " to " +
    	 				match.getPlayer1Score();
    		boardPanel.textArea.append( text+ "\n");	
	    break;
    	case 0:
				text = player1DisplayName+" and "+player2DisplayName+" tie at " +
  			       match.getPlayer1Score() + " each.";
    		boardPanel.textArea.append(text + "\n");
    	}
    	
    	endGame(text);
	}
	
	public static void endGame(String text)
	{
		// This method handle the case when the game ended
		int option = JOptionPane.showConfirmDialog(gui,  text + "\nDo you wish to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		gui.dispose();
		
		if (option == JOptionPane.NO_OPTION)
			System.exit(0);

		else
			initGame();
	}
}

