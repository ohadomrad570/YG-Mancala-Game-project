package GUI;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import GUI.BackGroundPanel;
import myGame.MancalaGame;
import myGame.Match;

public class OpenPanel extends BackGroundPanel{

	public static final int DEFAULT_WIDTH = GUI.DEFAULT_WIDTH;   
	public static final int DEFAULT_HEIGHT = GUI.DEFAULT_HEIGHT;
	private JButton HumanVSHuman;
	private JButton HumanVSAI;
	private static int stonesAmount;
	private GUI gui;
	
	public OpenPanel(GUI gui)
	{
		super("Images\\BackGroundScreen.png");
		this.gui = gui;
		this.setLayout(null);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		HumanVSHuman = new JButton(new ImageIcon("Images\\1VS1.png"));
		HumanVSAI = new JButton(new ImageIcon("Images\\1VSAI.png"));
			
		HumanVSHuman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HumanVSHuman();
			}
		});
		
		HumanVSAI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HumanVSAI();
			}
		});
		
		HumanVSHuman.setBounds(250, 300, 230, 180);
		HumanVSAI.setBounds(550, 300, 230, 180);
		
		this.add(HumanVSHuman);
		this.add(HumanVSAI);
		setVisible(false);		
	}
	
	private void getNumPits()
	{
		// Get from the client the number of stones in each pit 
		String initPits = JOptionPane.showInputDialog("Enter the number of stones initially in the pits: ", "4");

	    try {
	    	stonesAmount = Integer.parseInt(initPits);
	    	}
	    catch (Exception e) {
	    	stonesAmount = 4;
	    	}
	    
	    if(stonesAmount <= 0)
	    	stonesAmount = 4;
	}
	
	private void setBoardInfo(String player1ClassName, String player2ClassName)
	{
		MancalaGame.match = new Match(stonesAmount, player1ClassName, player2ClassName, MancalaGame.TIME_LIMIT);
		gui.boardPanel.setNumStonesInRegularPits(stonesAmount);
	}
	
	private  void HumanVSHuman()
	{
		getNumPits();
		MancalaGame.player1DisplayName = "Player1";
		MancalaGame.player2DisplayName = "Player2";
		
		setBoardInfo("HumanPlayer", "HumanPlayer");
		CardLayout cardLayout = (CardLayout) gui.cards.getLayout();
		cardLayout.show(gui.cards, "board");
		synchronized(MancalaGame.lock) {
			MancalaGame.lock.notify();
		}
	}
	
	public void HumanVSAI()
	{
		getNumPits();
	
		MancalaGame.player1DisplayName = "Human Player";
		MancalaGame.player2DisplayName = "AI Player";
		
		setBoardInfo("HumanPlayer", "AIPlayer");
		
		CardLayout cardLayout = (CardLayout) gui.cards.getLayout();
		cardLayout.show(gui.cards, "AIoptions");
		
		synchronized(MancalaGame.lock) {
			MancalaGame.lock.notify();
		}
	}
}
