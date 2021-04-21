package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import myGame.GameState;
import myGame.MancalaGame;
import myGame.Utility;

//This class manage the board game GUI
//_______________________________________
//|      |       B's side        |      |
//| Home | [11][10][9][8][7][6]  | Home |
//|  B   | [0] [1] [2][3][4][5]  |  A   |
//|______|_______A's side________|______|


public class BoardPanel extends JPanel{

	public static final int numRegularPits = GameState.numRegularPits; 
	public static final int numAllPits = GameState.numAllPits ; 
	
	
	// The size of the game screen
	public static final int DEFAULT_WIDTH = GUI.DEFAULT_WIDTH;   
	public static final int DEFAULT_HEIGHT = GUI.DEFAULT_HEIGHT;
	
	// The size of a player's home pit
	public static final int PLAYER_PIT_WIDTH = 100;
	public static final int PLAYER_PIT_HEIGHT = 400;
	
	// The size of a regular pit
	public static final int REGULAR_PIT_WIDTH = 60;
	public static final int REGULAR_PIT_HEIGHT = 145;

	private RegularPit [] pits;
	private HomePit aHomePit;
	private HomePit bHomePit;
	
	public TopPanel top;
	public JTextArea textArea;
	public JScrollPane scrollPanel;
	public String logs;
	
	private JPanel mainPanel;     // the panel of the game board
	
	public BoardPanel()
	{
		initGUI();
		setVisible(true);
	}
	
	private void initGUI()
	{
		// This method initialize the board 
			
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		createComponents();
		
		GridLayout middleLayout = new GridLayout(2,numRegularPits);
		middleLayout.setHgap(17);
		
		JPanel middlePits = new JPanel();	
		middlePits.setLayout(middleLayout);
		
		int size =  2*numRegularPits;
		for (int i = size-1; i > numRegularPits-1; i--) 
			middlePits.add(pits[i]);
			
		for (int i = 0; i < size/2; i++) 		
			middlePits.add(pits[i]);
		
		this.mainPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
	    
		top.setPreferredSize(new Dimension(800, 50));
		mainPanel.add(top, BorderLayout.NORTH);	
		
		bHomePit.setPreferredSize(new Dimension(150, 500));
		mainPanel.add(bHomePit, BorderLayout.WEST);
		
		middlePits.setPreferredSize(new Dimension(500, 500));
		mainPanel.add(middlePits, BorderLayout.CENTER);
		
		aHomePit.setPreferredSize(new Dimension(150, 500));
		mainPanel.add(aHomePit, BorderLayout.EAST);
		
		scrollPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH-20, 100));
		mainPanel.add(scrollPanel, BorderLayout.SOUTH);  
		this.add(mainPanel);
		this.setVisible(false);
	}
	
	public void setNumStonesInRegularPits(int stonesAmount)
	{
		for (int i = 0; i < pits.length; i++) 
			pits[i].setNumStones(stonesAmount);	
	}
	
	private void createComponents()
	{
		// This method creates the board Components

		BoardFormat format = new BoardFormat();
		aHomePit = new HomePit(format, "Player1");
		bHomePit = new HomePit(format, "Player2");
		
		pits = new RegularPit[2*numRegularPits];
		
		for (int i = 0; i < pits.length; i++) {
			if (i < numRegularPits) 
				pits[i] = new RegularPit(true, i, format);
			else
				pits[i] = new RegularPit( false, i, format);
			pits[i].createListener();
		}
		
		top = new TopPanel();
		
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		textArea.setOpaque(false);
		logs ="";
		textArea.setText(logs);
		scrollPanel = new JScrollPane(textArea);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
	}
	
	public void applyMove(int pit, boolean player1, GameState context)
	{
		// This method apply the move and update the GUI 
		// Get: 
		//		1) pit - the pit number [0,5]
		//		2) player1 - is it player1 move?
		//		3) context - the current game state
		// The point of view:
		//	_____________________________________
		//	|      |      P2's side      |      |
		//	| Home | [5][4][3][2][1][0]  | Home |
		//	|  P2  | [0][1][2][3][4][5]  |  P1  |
		//	|______|______P1's side______|______|
		
		int [] state = context.getState();
    	//clear the original bin
		int stones = state[pit];
    	state[pit] = 0;
    	
    	//clear the bin in GUI
    	if (player1)
    		pits[pit].takeAll();
	    else
	    	pits[pit+6].takeAll();
	    	
	    //sleep to make the move smoother
	    Utility.tSleep(MancalaGame.SLEEP_TIME);
	    	
	    int stoneTemp = stones;
	    for (int i = 0; i < stoneTemp; ++i) {
	    	int nextPit = (pit+i+1)%14;
	    	if (!(nextPit == 6 && pit > 6) && !(nextPit == 13 && pit < 7))
	    		++state[nextPit];
	    	else
	    		++stoneTemp; //skip on the rival's home pit
	    }
	    	
	    //scatter the stones in the consequent bins
	 

	    for (int i = 0; i < stones; i++) {
	    	int nextPit = (pit + i + 1) % 14;
	    	if (!(nextPit == 6 && pit > 6) && !(nextPit == 13 && pit < 7)) {
	    		if (nextPit == 6 && player1) //this is the only case we can put stone in the mancala
	    			aHomePit.addStone();
	    		else if (nextPit == 6 && !player1)
	    			bHomePit.addStone();
	    		
	    		if (player1 && nextPit < 6)
	    			pits[nextPit].addStone();
	    		else if (player1 && nextPit > 6)
	    			pits[nextPit - 1].addStone();
	    		
	    		else if (!player1 && nextPit < 6)
	    			pits[nextPit + 6].addStone();
	    		else if (!player1 && nextPit > 6)
	    			pits[nextPit - 7].addStone();
	    	}
	    	else
	    		++stones;
	    	//sleep for a little bit
	    	Utility.tSleep(MancalaGame.SLEEP_TIME);
	    }
	    
	    int lastPit = (pit+stones)%14;
	    	
	    //if free turn
	    if ((lastPit == 6 || lastPit == 13) && !context.gameOver())
	    	return;
	    	
	    boolean lastPitEmpty = state[lastPit] == 1;
	    boolean lastPitOnYourSide = pit/7 == lastPit/7;
	   
	    if (lastPitEmpty && lastPitOnYourSide && lastPit != 6 && lastPit != 13) {	
	    	int mancalaPit =  context.mancalaOf(pit);
	    	int neighborPit = context.neighborOf(lastPit);
	    		
	    	//If mancala happens, we also update the GUI
	    	int newNeighborPit, newLastBin;
	    	
	    	if (player1) {
	    		newLastBin = lastPit; //should be in our side
	    		newNeighborPit = neighborPit - 1;
	    		pits[newLastBin].takeAll();
	    		Utility.tSleep(MancalaGame.SLEEP_TIME);
	    		pits[newNeighborPit].takeAll();
	    		Utility.tSleep(MancalaGame.SLEEP_TIME);
	    		aHomePit.addStones(state[neighborPit] + 1);
	    	}
	    	
	    	else {
	    		newLastBin = lastPit + 6;
	    		newNeighborPit = neighborPit - 7;
	    			
	    		pits[newLastBin].takeAll();
	    		Utility.tSleep(MancalaGame.SLEEP_TIME);
	    		pits[newNeighborPit].takeAll();
	    		Utility.tSleep(MancalaGame.SLEEP_TIME);
	    		bHomePit.addStones(state[neighborPit] + 1);
	    	}
	    	
	    	state[mancalaPit] += state[neighborPit] + 1;
	    	state[neighborPit] = 0;
	    	state[lastPit] = 0;
	    }
	    
	    if (context.gameOver()) {
	      	context.stonesToMancalas();
	        //we also update the gui if this happens
	        for (int i = 0; i < 6; i++) {
	        	aHomePit.addStones(pits[i].getNumStones());
	        	pits[i].takeAll();
	        }
	       
	        for (int i = 6; i < 12; i++) {
	        	bHomePit.addStones(pits[i].getNumStones());
	        	pits[i].takeAll();
	        }
		}
	}
}


