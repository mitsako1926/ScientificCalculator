package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import calculations.BasicCalculations;
import engine.CalculatorEngine;

public final class CalculatorPanel extends JPanel{
	
	private final JTextField textDisplay;
	private final JLabel historyLabelUp,historyLabelDown;
	private final JPanel panelDisplay;
	private final JPanel panelButtons;
		
	private final String[] buttons = {
	    "%","≡","C","del",
		"1/x","x²","√","÷",
	    "7","8","9","×",
	    "4","5","6","-",
	    "1","2","3","+",
	    "±","0",".","=",
	};
		
	private final CalculatorEngine engine = new CalculatorEngine();

	
	CalculatorPanel(){
		
		setPreferredSize(new Dimension(400,700));
		setLayout(new BorderLayout());
		
		
		//MAIN DISPLAY OF THE CALCULATIONS AND THE HISTORY
		panelDisplay = new JPanel();
		panelDisplay.setPreferredSize(new Dimension(400,250));
		panelDisplay.setLayout(new BorderLayout());
		
		
		//MAIN DISPLAY OF BUTTONS
		panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(400,450));
		panelButtons.setLayout(new GridLayout(6,4,4,4));
//		panelButtons.setBackground(Color.DARK_GRAY);
		panelButtons.setBackground(Color.BLACK);
		
		//COMPONENTS OF THE DISPLAY OF CALCULATIONS
		textDisplay = new JTextField();
		textDisplay.setPreferredSize(new Dimension(400,150));
		textDisplay.setFont(new Font("Arial", Font.BOLD, 35));
		textDisplay.setText("0");
		textDisplay.setHorizontalAlignment(JTextField.RIGHT);
		textDisplay.setBackground(Color.GRAY);
		textDisplay.setForeground(Color.WHITE);
		textDisplay.setBorder(null);
		textDisplay.setEditable(false);//key binds are going to be supported in the future
		textDisplay.setFocusable(false);
		
		
		historyLabelUp = new JLabel();
		historyLabelUp.setPreferredSize(new Dimension(400,50));
		historyLabelUp.setBackground(Color.GRAY);
		historyLabelUp.setOpaque(true);
		historyLabelUp.setForeground(Color.WHITE);
		historyLabelUp.setHorizontalAlignment(JLabel.RIGHT);
		
		historyLabelDown = new JLabel();
		historyLabelDown.setPreferredSize(new Dimension(400,50));
		historyLabelDown.setBackground(Color.GRAY);
		historyLabelDown.setOpaque(true);
		historyLabelDown.setForeground(Color.WHITE);
		historyLabelDown.setHorizontalAlignment(JLabel.RIGHT);
		
		
		//WE ADD THE COMPONENTS TO THE DISPLAY OF CALCULATIONS
		panelDisplay.add(historyLabelUp,BorderLayout.NORTH);
		panelDisplay.add(historyLabelDown,BorderLayout.CENTER);
		panelDisplay.add(textDisplay,BorderLayout.SOUTH);
		
		engine.setDisplayRefreshListener(this::refreshView);
		
		//COMPONENTS OF THE DISPLAY OF BUTTONS
		
		Map<String, Runnable> actions = new HashMap<>();

		actions.put("C", () -> press("C"));
		actions.put("≡", () -> press("≡"));
		actions.put("+", () -> press("+"));
		actions.put("-", () -> press("-"));
		actions.put("×", () -> press("×"));
		actions.put("÷", () -> press("÷"));
		actions.put("%", () -> press("%"));
		actions.put("=", () -> press("="));
		actions.put("x²", () -> press("x²"));
		actions.put("1/x", () -> press("1/"));
		actions.put("√", () -> press("√"));
		actions.put("±", () -> press("±"));
		actions.put(".", () -> press("."));
		actions.put("del", () -> press("del"));
		

		for(int i=0; i<=9; i++) {
		    String num = String.valueOf(i);
		    actions.put(num, () -> press(num));
		}
		
		
		Arrays.stream(buttons)
			  .map(JButton::new)
			  .forEach(button -> { customizeButton(button,actions); panelButtons.add(button);});
		
		
		//ADDING THE 2 MAIN DISPLAY PANELS TO THE MAIN PANEL OF THE FRAME 
		add(panelDisplay,BorderLayout.NORTH);
		add(panelButtons,BorderLayout.SOUTH);
	}
	
	private void customizeButton(JButton button,Map<String,Runnable> actions) {
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    button.setFont(new Font("Arial", Font.PLAIN, 20));
	    button.addActionListener(e -> actions.get(button.getText()).run());
	}	
	
	private void press(String buttonPressed){
	    
	    engine.press(buttonPressed);

	    textDisplay.setText(engine.getDisplay());
	    historyLabelDown.setText(engine.getHistoryDown());
	    historyLabelUp.setText(engine.getHistoryUp());
	}
	
	
	private void refreshView() {
	    textDisplay.setText(engine.getDisplay());
	    historyLabelDown.setText(engine.getHistoryDown());
	    historyLabelUp.setText(engine.getHistoryUp());
	}
	

}




