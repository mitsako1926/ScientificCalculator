package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

public final class CalculatorPanel extends JPanel{
	
	private JTextField textDisplay;
	private JLabel historyLabelUp,historyLabelDown;
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
	
	private BasicCalculations calculator = new BasicCalculations();
	
	private double firstNumber,secondNumber;
	private String operator,history;
	
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
		
		
		//COMPONENTS OF THE DISPLAY OF BUTTONS
		
		Map<String, Runnable> actions = new HashMap<>();

		actions.put("C", () -> clearAll());
		actions.put("CE", () -> settings());
		actions.put("+", () -> setOperator("+"));
		actions.put("-", () -> setOperator("-"));
		actions.put("×", () -> setOperator("×"));
		actions.put("÷", () -> setOperator("÷"));
		actions.put("%", () -> setOperator("%"));
		actions.put("=", () -> calculate());
		actions.put("x²", () -> applyFunction("x²"));
		actions.put("1/x", () -> applyFunction("1/x"));
		actions.put("√", () -> applyFunction("√"));
		actions.put("±", () -> signOperator());
		actions.put(".", () -> appendNumber("."));
		actions.put("del", () -> deleteOperator());
		

		for(int i=0; i<=9; i++) {
		    int num = i;
		    actions.put(String.valueOf(i), () -> appendNumber(num));
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
	
	private void clearAll() {
	    textDisplay.setText("0");
	    operator =null;
	    historyLabelUp.setText("");
	    historyLabelDown.setText("");
	}

	private void settings() {
	    textDisplay.setText("0");
	    
	}
	
	private void deleteOperator() {
		
		if(!textDisplay.getText().equals("0")) {
			
			StringBuilder sb = new StringBuilder(textDisplay.getText());
			textDisplay.setText(sb.deleteCharAt(sb.length()-1).toString());
			
			if(textDisplay.getText().isBlank()) textDisplay.setText("0");
			
		}
		firstNumber = Double.parseDouble(textDisplay.getText());
		
	}

	private void signOperator() {
		if(!textDisplay.getText().equals("0")) {
			
			if(textDisplay.getText().contains("-")) {
				
				StringBuilder sb =new StringBuilder(textDisplay.getText());
				int index = sb.indexOf("-"); 
				
				if(index != -1){
				    textDisplay.setText(sb.deleteCharAt(index).toString());
				}
				
			}else textDisplay.setText("-" + textDisplay.getText());
		}
		
		if(operator==null) 
			firstNumber = Double.parseDouble(textDisplay.getText());
		else
			secondNumber = Double.parseDouble(textDisplay.getText());
		
	}
	
	
	private void appendNumber(int num) {
	    
		if(operator==null) {
	    	
			if(textDisplay.getText().equals("0")) 
	    		textDisplay.setText(String.valueOf(num));
	    	else 
	        	textDisplay.setText(textDisplay.getText() + num);
	    	firstNumber = Double.parseDouble(textDisplay.getText());
	    	
	    }else {

	    	if(textDisplay.getText().contains(operator)||textDisplay.getText().equals("0")) 
	    		textDisplay.setText(String.valueOf(num));
	    	else 
	        	textDisplay.setText(textDisplay.getText() + num);
	    	secondNumber = Double.parseDouble(textDisplay.getText());
	    	
	    }
	}

	
	private void appendNumber(String dot) { 
		
	    if(textDisplay.getText().equals("0")) {
	        textDisplay.setText("0" + dot);
	    } else if(!textDisplay.getText().contains(".")) {
	        textDisplay.setText(textDisplay.getText() + dot);
	    }
	}

	
	private void setOperator(String op) {
		operator = op;
		
		if(textDisplay.getText().charAt(textDisplay.getText().length() - 1)=='.') textDisplay.setText(textDisplay.getText() +"0");
		
		textDisplay.setText(textDisplay.getText() +" " +op);
		historyLabelDown.setText(textDisplay.getText());
		
	}

	
	private void calculate() {
		double result=0;
		
		switch(operator) {
			case "+":	result = calculator.add(firstNumber, secondNumber);
				break;
			case "-":	result = calculator.subtract(firstNumber, secondNumber);
				break;
			case "×":	result = calculator.multiply(firstNumber, secondNumber);
				break;
			case "÷":	result = calculator.devide(firstNumber, secondNumber);
				break;
			case "%":	result = calculator.modular(firstNumber, secondNumber);
				break;
		}
		
		textDisplay.setText(result+"");
		operator = null;
		firstNumber = Double.parseDouble(textDisplay.getText());
		
		if(history!=null && history.contains("=")) {
			historyLabelUp.setText(history);
		}
		historyLabelDown.setText(historyLabelDown.getText() +" "+secondNumber+ " = " +textDisplay.getText());
		history = historyLabelDown.getText(); 
	}

	
	private void applyFunction(String func) {
	    historyLabelUp.setText(historyLabelDown.getText());
	    OptionalDouble result;

	    switch(func) {

	        case "√":
	            result = OptionalDouble.of(calculator.squareRoot(firstNumber));
	            historyLabelDown.setText("√" + firstNumber + " = ");
	            break;

	        case "x²":
	            result = OptionalDouble.of(calculator.square(firstNumber));
	            historyLabelDown.setText(firstNumber + "² = ");
	            break;

	        case "1/x":
	            result = calculator.divideByNumber(firstNumber);

	            if(result.isEmpty()) {
	                historyLabelDown.setText("1/" + firstNumber + " = ?");
	                textDisplay.setText("Cannot divide by zero");
	                firstNumber = 0;
	                return;
	            }

	            historyLabelDown.setText("1/" + firstNumber + " = ");
	            break;
	        default:
	            return;
	    }

	    double value = result.getAsDouble();

	    historyLabelDown.setText(historyLabelDown.getText() + value);
	    textDisplay.setText(String.valueOf(value));
	    firstNumber = value;
	}

}




