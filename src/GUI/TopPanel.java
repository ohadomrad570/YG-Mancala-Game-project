package GUI;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import myGame.MancalaGame;
import myGame.Match;

// This class constitutes the top panel of the game board

public class TopPanel extends JPanel
{
	private JTextField statusText;
	
	public TopPanel()
	{	
		statusText = new JTextField(55);
		statusText.setEditable(false);
		this.setLayout(new FlowLayout());
		add(statusText);
		setVisible(true);
	}
	
	public void updateText() 
	{
		boolean player1Move = Match.player1Move;
		String player1Name = MancalaGame.player1DisplayName;
		String player2Name = MancalaGame.player2DisplayName;
		String text ="";
				
		if(player1Move)
			text = player1Name + "'s Turn";
		else
			text = player2Name + "'s Turn";
		statusText.setText(text);
	}
}

