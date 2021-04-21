package GUI;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

// This class manage the GUI

public class GUI extends JFrame{
	// The size of the game screen
	public static final int DEFAULT_WIDTH = 1000;   
	public static final int DEFAULT_HEIGHT = 700;
		
	public OpenPanel openPanel;
	public BoardPanel boardPanel;
	public AIOptionsPanel AIPanel;
	public JPanel cards;
	

	public GUI()
	{
		this.setTitle("Mancala");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		
		openPanel = new OpenPanel(this);
		boardPanel = new BoardPanel();
		AIPanel = new AIOptionsPanel(this);
		
		cards.add(openPanel, "open");
		cards.add(boardPanel, "board");
		cards.add(AIPanel, "AIoptions");
		
        getContentPane().add(cards);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "open");
	       
	}
}