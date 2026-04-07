package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.CalculatorEngine;


public final class CalculatorScientificPanel extends JPanel{
	
	
	private final JLabel labelScientific = new JLabel("Scientific");
	private final JPanel panelButtons = new JPanel();
	
	private final String[] buttons = {  "π", "e","n!", "x^y","rand",
		    							"ln", "log", "10^x","(",")",
		    							"abs","sin", "cos", "tan", "cot",
		    							"deg","asin", "acos", "atan", "acot"
									 };
	
	private CardLayout cardLayout;
	private JPanel container;
	
	private final CalculatorEngine engine;
	
	CalculatorScientificPanel(CalculatorEngine engine){
		
		this.engine = engine;
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(350,450));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
		
		//BUTTONS
		Arrays.stream(buttons).map(JButton::new)
		.forEach((button)->customizeButton(button));
		
		//LABEL
		customizeLabel(labelScientific);
		labelScientific.setFont(new Font("Arial",Font.BOLD,20));
		labelScientific.setPreferredSize(new Dimension(350,70));
		
		//PANEL FOR BUTTONS
		panelButtons.setLayout(new GridLayout(4,4,3,3));
		panelButtons.setPreferredSize(new Dimension(350,300));
		panelButtons.setBackground(Color.BLACK);
		
		add(panelButtons,BorderLayout.SOUTH);
		add(labelScientific,BorderLayout.NORTH);
	}
	
	
	
	private void press(String buttonPressed){
		switch(buttonPressed) {
		
		}
		
	}
	
	
	
	public void setCardLayout(CardLayout cardLayout, JPanel container) {
	    this.cardLayout = cardLayout;
	    this.container = container;
	}
	
	
	private void customizeLabel(JLabel label) {
		label.setBackground(Color.GRAY);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial",Font.BOLD,15));
		label.setPreferredSize(new Dimension(350,30));
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);
	}
	

	
	private void customizeButton(JButton button){
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Arial", Font.PLAIN, 15));
		button.setPreferredSize(new Dimension(100,50));
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    button.addActionListener(e->press(button.getText()));
	    panelButtons.add(button);
	}
	
}

