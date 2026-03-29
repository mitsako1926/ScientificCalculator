package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorPanel extends JPanel{
	
	private JTextField textDisplay;
	private JLabel historyLabel;
	private JPanel panelDisplay;
	private JPanel panelButtons;
	
	private String[] buttons = {
	    "%","CE","C","del",
		"1/x","x²","√","÷",
	    "7","8","9","×",
	    "4","5","6","-",
	    "1","2","3","+",
	    "±","0",".","=",
	};
	
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
		panelButtons.setLayout(new GridLayout(6,4,3,3));
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
		
		historyLabel = new JLabel();
		historyLabel.setPreferredSize(new Dimension(400,100));
		historyLabel.setBackground(Color.GRAY);
		historyLabel.setOpaque(true);
		historyLabel.setForeground(Color.WHITE);
		
		
		//WE ADD THE COMPONENTS TO THE DISPLAY OF CALCULATIONS
		panelDisplay.add(historyLabel,BorderLayout.NORTH);
		panelDisplay.add(textDisplay,BorderLayout.SOUTH);
		
		
		//COMPONENTS OF THE DISPLAY OF BUTTONS
		
		Map<String, Runnable> actions = new HashMap<>();

		actions.put("C", () -> clearAll());
		actions.put("CE", () -> clearEntry());
		actions.put("+", () -> setOperator("+"));
		actions.put("-", () -> setOperator("-"));
		actions.put("×", () -> setOperator("×"));
		actions.put("÷", () -> setOperator("÷"));
		actions.put("%", ()->setOperator("%"));
		actions.put("=", () -> calculate());
		actions.put("x²", () -> applyFunction("x²"));
		actions.put("1/x", () -> applyFunction("1/x"));
		actions.put("√", ()->applyFunction("√"));
		actions.put("±", ()->signOperator("±"));
		actions.put(".", () -> appendNumber("."));
		actions.put("del", ()->deleteOperator("del"));
		

		for(int i=0; i<=9; i++) {
		    int num = i;
		    actions.put(String.valueOf(i), () -> appendNumber(num));
		}
		
		
		for(String text : buttons) {
		    JButton button = new JButton(text);
		    
		    button.setFocusable(false);
		    button.setBackground(Color.GRAY);
		    button.setForeground(Color.WHITE);
		    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true)); // true = rounded
		    button.setFont(new Font("Arial", Font.PLAIN, 20));
		    button.addActionListener(e -> {
		        if(actions.containsKey(text)) {
		            actions.get(text).run();
		        }
		    });
		    
		    panelButtons.add(button);
		}
		
		
		
		
		//ADDING THE 2 MAIN DISPLAY PANELS TO THE MAIN PANEL OF THE FRAME 
		add(panelDisplay,BorderLayout.NORTH);
		add(panelButtons,BorderLayout.SOUTH);
	}
	
	private Object deleteOperator(String string) {
		return null;
	}

	private Object signOperator(String string) {
		return null;
	}
	
	private void appendNumber(int num) {
	    if(textDisplay.getText().equals("0")) 
	    	textDisplay.setText(String.valueOf(num));
	    else 
	        textDisplay.setText(textDisplay.getText() + num);
	    
	}

	private void appendNumber(String dot) { 
	    if(textDisplay.getText().equals("0")) {
	        textDisplay.setText("0" + dot);
	    } else if(!textDisplay.getText().contains(".")) {
	        textDisplay.setText(textDisplay.getText() + dot);
	    }
	}

	private void setOperator(String op) {
	    
	}

	private void calculate() {
	    double secondNumber = Double.parseDouble(textDisplay.getText());
	    double result = 0;

	    
	}

	private void applyFunction(String func) {
	    
	}

	private void clearAll() {
	    textDisplay.setText("0");
	}

	private void clearEntry() {
	    textDisplay.setText("0");
	}
	
}




