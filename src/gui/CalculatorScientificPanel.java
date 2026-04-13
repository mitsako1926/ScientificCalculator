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
	
	private final String[] buttons = {  "π", "e","n!", "x^y","random",
		    							"ln", "log", "10^x","⌈x⌉","⌊x⌋",
		    							"abs","sin", "cos", "tan", "cot",
		    							"deg","asin", "acos", "atan", "acot"
									 };

	private final List<JButton> buttonsList = new ArrayList<>();
	private final CalculatorEngine engine;
	
	public CalculatorScientificPanel(CalculatorEngine engine){
		
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
	    button.setFont(new Font("Dialog", Font.PLAIN, 15));
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
	
	
	
	
	private void press(String buttonPressed) {
	    
		if(buttonPressed==null)return;
		
		switch (buttonPressed) {
	        case "π":
	        	engine.addScientificNumber("π");
	            
	            break;

	        case "e":
	            engine.addScientificNumber("e");
	            break;

	        case "random":
	            engine.addScientificNumber("random");
	            break;

	        case "x^y":
	            engine.addScientificOperator();
	            break;

	        case "⌈x⌉":
	        	engine.scientificFunctionPressed("⌈x⌉");
	            break;

	        case "⌊x⌋":
	            engine.scientificFunctionPressed("⌊x⌋");
	            break;

	        case "deg":
	            engine.toggleDegreeMode();
	            break;
	            
	        case "n!":
	        	engine.scientificFunctionPressed("n!");
	            break;
	            
	        case "ln":
	        	engine.scientificFunctionPressed("ln");
	            break;

	        case "log":
	        	engine.scientificFunctionPressed("log");
	            break;

	        case "10^x":
	        	engine.scientificFunctionPressed("10^x");
	            break;
	            
	        case "abs":
	        	engine.scientificFunctionPressed("abs");
	            break;

	        case "sin":
	        	engine.scientificFunctionPressed("sin");
	            break;

	        case "cos":
	        	engine.scientificFunctionPressed("cos");
	            break;

	        case "tan":
	        	engine.scientificFunctionPressed("tan");
	            break;

	        case "cot":
	        	engine.scientificFunctionPressed("cot");
	            break;

	        case "asin":
	        	engine.scientificFunctionPressed("asin");
	            break;

	        case "acos":
	        	engine.scientificFunctionPressed("acos");
	            break;

	        case "atan":
	        	engine.scientificFunctionPressed("atan");
	            break;

	        case "acot":
	        	engine.scientificFunctionPressed("acot");
	            break;

	        default:
	            break;
	    }
	}
	
	
	
}

