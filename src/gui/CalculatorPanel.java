package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalculatorPanel extends JPanel{
	
	private JTextField textDisplay;
	private JLabel historyLabel;
	private JPanel panelDisplay;
	private JPanel panelButtons;

	
	CalculatorPanel(){
		setPreferredSize(new Dimension(400,700));
		setLayout(new BorderLayout());
		
		//MAIN DISPLAY OF THE CALCULATIONS AND THE HISTORY
		panelDisplay = new JPanel();
		panelDisplay.setPreferredSize(new Dimension(400,250));
		panelDisplay.setBackground(Color.BLACK);
		panelDisplay.setLayout(new BorderLayout());
		
		//MAIN DISPLAY OF BUTTONS
		panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(400,450));
		panelButtons.setBackground(Color.blue);
		
		//COMPONENTS OF THE DISPLAY OF CALCULATIONS
		textDisplay = new JTextField();
		textDisplay.setPreferredSize(new Dimension(400,150));
		textDisplay.setBackground(Color.red);
		textDisplay.setText("AFTO EINAI TO JText");
		
		historyLabel = new JLabel();
		historyLabel.setPreferredSize(new Dimension(400,100));
		historyLabel.setText("AFTO EINAI TO JLABEL");
		
		panelDisplay.add(historyLabel,BorderLayout.NORTH);
		panelDisplay.add(textDisplay,BorderLayout.SOUTH);
		
		//ADDING THE 2 MAIN DISPLAY PANELS TO THE MAIN PANEL OF THE FRAME 
		add(panelDisplay,BorderLayout.NORTH);
		add(panelButtons,BorderLayout.SOUTH);
	}
}




