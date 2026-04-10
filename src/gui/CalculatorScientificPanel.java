package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	private final List<JButton> buttonsList = new ArrayList<>();
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
		
		engine.setThemeListener(isDark->{
			if(isDark) changeToDark();
			else changeToLight();
		});
	}
	
	
	
	private void press(String buttonPressed){
		switch(buttonPressed) {
		
		}
		
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setBackground(Color.GRAY);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial",Font.BOLD,20));
		label.setPreferredSize(new Dimension(350,70));
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
	    
	    buttonsList.add(button);
	}
	
	
	
	
	private void changeToDark(){
		setBackground(Color.GRAY);
		
		customizeLabel(labelScientific);
		
		buttonsList.forEach(button->{
			button.setBackground(Color.GRAY);
			button.setForeground(Color.WHITE);
			button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
		});
		
		panelButtons.setBackground(Color.BLACK);
	}
	
	
	private void changeToLight(){
		setBackground(Color.WHITE);
		
		labelScientific.setBackground(Color.WHITE);
		labelScientific.setForeground(Color.DARK_GRAY);
		
		buttonsList.forEach(button->{
			button.setBackground(Color.WHITE);
			button.setForeground(Color.DARK_GRAY);
			button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
		});
		
		panelButtons.setBackground(Color.WHITE);
	}
	
	
	
}

