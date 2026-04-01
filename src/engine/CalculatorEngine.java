package engine;


import java.text.DecimalFormat;
import calculations.BasicCalculations;

public final class CalculatorEngine{
	
	
	private DecimalFormat df = new DecimalFormat("###.#######################");
	
	private BasicCalculations calculator = new BasicCalculations();
	
	private double firstNumber,secondNumber;
	private boolean startNewNumber = true;
	private String operator;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";

	
//  PROBLEMS:
//	SIGN OPERATOR
//	
//	
//	
//	
//	
//	
	
	public String getDisplay() {
	    return display;
	}

	
	public String getHistoryDown() {
	    return historyDown;
	}

	
	public String getHistoryUp() {
	    return historyUp;
	}
	
	
	public void press(String buttonPressed) {

	    if(buttonPressed.matches("[0-9]")) {
	        appendNumber(buttonPressed);
	        return;
	    }

	    switch(buttonPressed) {

	        case ".":
	            appendDot();
	            break;

	        case "+":
	        case "-":
	        case "×":
	        case "÷":
	        case "%":
	            setOperator(buttonPressed);
	            break;

	        case "=":
	            calculate();
	            break;

	        case "C":
	            clear();
	            break;

	        case "del":
	            delete();
	            break;

	        case "±":
	            toggleSign();
	            break;

	        case "√":
	        case "x²":
	        case "1/x":
	            applyFunction(buttonPressed);
	            break;
	    }
	}
	
	
	private void appendNumber(String num) {

	    if(startNewNumber) {
	        
	    	display = num;
	        startNewNumber = false;
	    } 
	    else display += num;

	}
	

	private void appendDot() {

	    if(startNewNumber) {
	        display = "0.";
	        startNewNumber = false;
	        return;
	    }

	    if(!display.contains(".")) {
	        display += ".";
	    }
	}
	
	
	private void clear() {
	    display = "0";
	    firstNumber = 0;
	    secondNumber = 0;
	    operator = null;
	    historyUp = "";
	    historyDown = "";
	    startNewNumber = true;
	}
	
	
	private void setOperator(String op) {

	    if(operator != null && !startNewNumber) {
	        calculate();
	    }

	    firstNumber = Double.parseDouble(display);
	    operator = op;
	    historyDown = df.format(firstNumber) + " " + op;
	    display+=op;
	    startNewNumber = true;
	}
	
	
	private void calculate() {

	    if(operator == null) {
	        return;
	    }

	    secondNumber = Double.parseDouble(display);

	    double result = 0;

	    switch(operator) {
	        case "+": result = calculator.add(firstNumber, secondNumber); break;
	        case "-": result = calculator.subtract(firstNumber, secondNumber); break;
	        case "×": result = calculator.multiply(firstNumber, secondNumber); break;
	        case "÷": result = calculator.devide(firstNumber, secondNumber); break;
	        case "%": result = calculator.modular(firstNumber, secondNumber); break;
	    }

	    historyUp = historyDown + " " + df.format(secondNumber) + " = "+df.format(result);
	    display = df.format(result);
	    historyDown = "";
	    firstNumber = result;
	    operator = null;
	    startNewNumber = true;
	}
	
	
	private void delete() {

	    if(startNewNumber) return;

	    if(display.length() == 1 || display.equals("-")) {
	        display = "0";
	        startNewNumber = true;
	        return;
	    }

	    display = display.substring(0, display.length() - 1);
	}
	
	
	private void toggleSign() {

	    if(display.equals("0")) return;

	    if(display.startsWith("-")) {
	        display = display.substring(1);
	    } else {
	        display = "-" + display;
	    }
	}
	
	
	private void applyFunction(String func) {

	    double number = Double.parseDouble(display);
	    double result = 0;

	    switch(func) {

	        case "√":
	            if(number < 0) {
	                display = "Error";
	                startNewNumber = true;
	                return;
	            }
	            result = calculator.squareRoot(number);
	            if(!historyDown.isBlank())historyUp = historyDown;
	            historyDown = "√(" + df.format(number) + ") = " + df.format(result);
	            break;

	        case "x²":
	            result = calculator.square(number);
	            if(!historyDown.isBlank())historyUp = historyDown;
	            historyDown = df.format(number) + "² = "+ df.format(result);
	            break;

	        case "1/x":
	            if(number == 0) {
	                display = "Error";
	                startNewNumber = true;
	                return;
	            }
	            result = calculator.divideByNumber(number);
	            if(!historyDown.isBlank())historyUp = historyDown;
	            historyDown = "1/(" + df.format(number) + ") = "+ df.format(result);
	            break;

	        default:
	            return;
	    }

	    display = df.format(result);
	    firstNumber = result;
	    startNewNumber = true;
	}

}

