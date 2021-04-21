package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import AI.AlphaBeta;
import AI.PitChooser;
import AIEvaluationFunctions.EvalFunctionsFactory;
import AIEvaluationFunctions.EvaluationFunction;
import myGame.MancalaGame;

// This class constitutes the user's AI opponent options on the GUI
public class AIOptionsPanel extends BackGroundPanel{
	
	public static final int DEFAULT_WIDTH = GUI.DEFAULT_WIDTH;   
	public static final int DEFAULT_HEIGHT = GUI.DEFAULT_HEIGHT;
	
	
	private EvalFunctionsFactory funcsFactory;
	
	private JComboBox funcsCombo;
	
	public AIOptionsPanel(GUI gui)
	{
		super("Images\\AIOptions.png");
		this.setLayout(null);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		funcsFactory = new EvalFunctionsFactory();
        
        JComboBox funcsCombo = new JComboBox(funcsFactory.functions.toArray());
        
        JButton submit = new JButton(new ImageIcon("Images\\SUBMIT.png"));
        
        submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String func = (String)funcsCombo.getSelectedItem();
				PitChooser chooserObj = new AlphaBeta();
				EvaluationFunction funcObj = funcsFactory.getNewProduct(func);
				chooserObj.setEvalFunc(funcObj);		
				MancalaGame.match.setPlayer2PitChooser(chooserObj);
				
				CardLayout cardLayout = (CardLayout) gui.cards.getLayout();
				cardLayout.show(gui.cards, "board");
				synchronized(MancalaGame.lock) {
					MancalaGame.lock.notify();
				}
			}
		});
        
        funcsCombo.setBounds(250, 300, 220, 75);
        
        funcsCombo.setForeground(Color.BLACK);
        funcsCombo.setBackground(Color.LIGHT_GRAY);
        
        funcsCombo.setFont(new Font("Ariel", Font.BOLD, 20));

        submit.setBounds(550, 300, 230, 120);

		
        this.add(funcsCombo);
        this.add(submit);
        this.setVisible(false);
	}	
}
