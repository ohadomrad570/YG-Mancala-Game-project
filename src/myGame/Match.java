package myGame;

import AI.PitChooser;
import GUI.TopPanel;

// This class manage the game

public class Match {
	
	public static final int numRegularPits = GameState.numRegularPits; 
	public static final int numAllPits = GameState.numAllPits ; 
	public static int stoneAmountInPit;

	protected static GenericFactory<Player> players;
	    
	protected String player1ClassName;
	protected String player2ClassName;
    
	protected Player player1, player2;
	protected GameState currentState;
	protected int thinkTime;
	protected int player1score, player2score;
    
    public static boolean player1Move;
    public MovingThread movingThread;
    
    protected Player currentPlayer;
	

	static
	{
		players = new GenericFactory<Player>();
		players.insertProduct("AIPlayer", AIPlayer.class);
		players.insertProduct("HumanPlayer", HumanPlayer.class);
		players.insertProduct("RandomPlayer", RandomPlayer.class);
	}
	
	
	public Match(int stoneAmount, String player1ClassName,String player2ClassName, int thinkTime)
	{
		this.thinkTime = thinkTime;

		player1 = players.getNewProduct(player1ClassName);
		player2 = players.getNewProduct(player2ClassName);
		this.player1ClassName = player1ClassName;
		this.player2ClassName = player2ClassName;
		
		stoneAmountInPit = stoneAmount;
		
		player1score = player2score = -1;
		movingThread = null;
		player1Move = true;
		
		currentState = new GameState(stoneAmount);
	 }
	
	public void setPlayer1PitChooser(PitChooser chooser)
	{
		if(player1 instanceof AIPlayer)
			((AIPlayer)player1).setChooser(chooser);	
	}
	
	public void setPlayer2PitChooser(PitChooser chooser)
	{
		if(player2 instanceof AIPlayer)
			((AIPlayer)player2).setChooser(chooser);	
	}

	public int play() {
		// This method manage the game 
		
		player1Move = false;
		currentPlayer = player2;
		boolean playAgain = false;

		while (!currentState.gameOver()) {
			
			if (!playAgain) {
				player1Move = !player1Move;
				currentState.rotate();
			}
			
			else 		
				MancalaGame.boardPanel.textArea.append("** Player moves again! **\n");
			
			MancalaGame.boardPanel.top.updateText();

			
			if (player1Move) 
			{
				currentPlayer = player1;
				MancalaGame.boardPanel.textArea.append("PLAYER 1 TO MOVE:\n");
			}
			
			else 
			{
				currentPlayer = player2;
				MancalaGame.boardPanel.textArea.append("PLAYER 2 TO MOVE:\n");
			}

			Object mutex = new Object();
			long timeout = thinkTime*1000;
			
			if((player1ClassName.equals("HumanPlayer") && player1Move) || (player2ClassName.equals("HumanPlayer") && !player1Move))
			{   
				timeout = 300*1000;  // Gives the human player 300 seconds (5 minutes) to decide a move. 
		                                 // If no move is selected in that amount of time, then a move is randomly selected.
			}
		        
			try {
				synchronized (mutex)
				{
					movingThread = new MovingThread(currentPlayer, new GameState(currentState), mutex, timeout);
					movingThread.start();  // schedule the moving thread
					mutex.wait(timeout);  //go to sleep for the timeout or until the move returns
				    
					Thread.sleep(500);  //sleep for half a second to allow for cleanup
					if (movingThread.isAlive())
					{
						// Human Player has not clicked on any pit
						movingThread.stop();  //kill the moving thread if it is still going	
						MancalaGame.boardPanel.textArea.append("Computer done the turn instead of the player\n");
					}		    
				}
			}
			catch (Exception e) {
				e.printStackTrace();  //nasty, we shouldn't get here
			}

			int move = currentPlayer.getMove();
			//MancalaGame.gui.textArea.append("Choosen pit: " + move + "\n");

			if (currentState.illegalMove(move)) { // the player who chose an illegal move, will have an additional
													// turn until he will choose a legal move 
				if (currentPlayer == player1) {
					MancalaGame.boardPanel.textArea.append("Player 1 made an illegal move.\n");
					playAgain = true;
					continue;
				}
				else {
					MancalaGame.boardPanel.textArea.append("Player 2 made an illegal move.\n");
					playAgain = true;
				    continue; 
				}
			}
		
			MancalaGame.boardPanel.applyMove(move, player1Move, new GameState(currentState)); //update the GUI
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
	
	public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}
	
	public int getPlayer1Score() {
		// This method return the score of player1
		//  Meaningless before the game ends.
		return player1score;
	}

	
	public int getPlayer2Score() {
		// This method return the score of player2
	    //  Meaningless before the game ends.
		return player2score;
	}

	// This class takes care of
	//executing the move in a separate thread so that we can timeout if the player takes too long.
	
	public static class MovingThread extends Thread{
		private Player player ;
		private GameState context;
		private Object mutex ;
		private long timeout;
		private int moveResult;

		public MovingThread(Player player, GameState context, Object mutex, long timeout){
			this.player = player;
			this.context = context;
			this.mutex = mutex;
			this.timeout = timeout;
		}

		public void run(){
			moveResult = 0;
			try {
					synchronized (mutex) {} //this is just to ensure that the player doesn't
				                            //move much before the timer is started
					player.move(context);

					synchronized (mutex) {
						mutex.notify();  //wake up the match if we finished before the time
					}
			    }
			
			    catch (Exception e) {
			    	System.out.println(e.toString());
			    }
			}
		
		public int getMove(){
			return player.getMove();
		}	        
	}
 }
