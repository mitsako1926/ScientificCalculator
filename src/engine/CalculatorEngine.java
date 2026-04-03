package engine;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import calculations.BasicCalculations;

public final class CalculatorEngine{
		
	private final NumberFormat nf =  NumberFormat.getInstance(Locale.US);
	{nf.setGroupingUsed(true); nf.setMaximumFractionDigits(10);}
	
	private final BasicCalculations calculator = new BasicCalculations();
	
	private double firstNumber,secondNumber;
	private boolean startNewNumber = true;
	private String operator;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";

	
//  PROBLEMS:
//  find more bugs
//  IMPROVEMENTS:
	
	
	public String getDisplay() {
	    return display;
	}

	
	public String getHistoryDown() {
	    return historyDown;
	}

	
	public String getHistoryUp() {
	    return historyUp;
	}
	
	
	
	public double getDoubleValueFromDisplay() {
		Number number;
		try {
			number = nf.parse(display);
			return number.doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			setErrorState();
			return 0;
		}
	}
	
	
	
	private void setErrorState() {
		display = "Error";
        startNewNumber = true;
        firstNumber = 0;
	    secondNumber = 0;
	    operator = null;
	}
	
	
	
	private String reformatDisplay(String text) {
	    String clean = text.replace(",", "");

	    if (clean.isEmpty() || clean.equals("-")) return "0";

	    if (!clean.contains(".")) return nf.format(Double.parseDouble(clean));

	    boolean negative = clean.startsWith("-");
	    if (negative) clean = clean.substring(1);

	    String[] parts = clean.split("\\.", 2);
	    String integerPart = parts[0];
	    String fractionalPart = parts[1];

	    if (integerPart.isEmpty()) integerPart = "0";

	    String formattedInteger = nf.format(Long.parseLong(integerPart));

	    return (negative ? "-" : "") + formattedInteger + "." + fractionalPart;
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
	    } else if(display.equals("0")||display.equals("-0")){
	    	display = num;
	    	startNewNumber = false;
	    }else  {
	    	display += num;
	    	display = reformatDisplay(display);
	    }
	}
	

	
	private void appendDot() {
		
		if(startNewNumber||display.equals("NaN")) {
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

		if((operator != null && startNewNumber)||display.equals("Error")) return;
		
		if(operator != null && !startNewNumber) calculate();

		firstNumber = getDoubleValueFromDisplay();
		
	    operator = op;
	    historyDown = nf.format(firstNumber) + " " + op;
	    
	    if(display.endsWith(".")||display.endsWith(","))display = display.substring(0, display.length() - 1);
	    
	    display+=op;
	    startNewNumber = true;
	}
	
	
	
	private void calculate() {

	    if(operator == null || startNewNumber) return;
	    
		secondNumber = getDoubleValueFromDisplay();
	    
	    double result = 0;

	    switch(operator) {
	        case "+": result = calculator.add(firstNumber, secondNumber); break;
	        case "-": result = calculator.subtract(firstNumber, secondNumber); break;
	        case "×": result = calculator.multiply(firstNumber, secondNumber); break;
	        case "÷": result = calculator.devide(firstNumber, secondNumber); break;
	        case "%": result = calculator.modular(firstNumber, secondNumber); break;
	    }
	    
	    if(Double.isInfinite(result)) {
	    	historyUp = historyDown + " " + nf.format(secondNumber) + " = Error";
	    	historyDown = "";
	    	setErrorState();
		    return;
	    }
	    
	    historyUp = historyDown + " " + nf.format(secondNumber) + " = "+nf.format(result);
	    display = nf.format(result);
	    historyDown = "";
	    firstNumber = result;
	    operator = null;
	}
	

	
	private void delete() {
		
	    if(startNewNumber && operator!=null) {
	    	
	    	if(display.equals("0"))return;
	    	
	    	display = display.substring(0, display.length() - 1);
	    	
	    	double number;			
			number = getDoubleValueFromDisplay();
			
	    	display = nf.format(number);
	    	startNewNumber = false;
	    	operator =null;
	    	historyDown = "";
	    	return;
	    }
	    
	    if( (display.length() == 1 || display.equals("-") )||display.equals("Error")||display.equals("NaN")) {
	    	display = "0";
	        startNewNumber = true;
	        return;
	    }

	    display = display.substring(0, display.length() - 1);
	    display = reformatDisplay(display);
	    
	    if (display.equals("0")) {
	        startNewNumber = true;
	    }
	}
	
	
	
	private void toggleSign() {

	    if(display.equals("0")||display.equals("Error")||display.equals("NaN")) return;

	    if(operator!=null&&startNewNumber) {
	    	
	    	if(display.startsWith("-")) {
	    		display = display.substring(1);
	    		historyDown = historyDown.substring(1);
	    		
	    	}else {
	    		display = "-" + display;
	    		historyDown = "-"+historyDown;
	    	}
	    	
	    	firstNumber = -firstNumber;
	    	return;
	    }
	    
	    if(display.startsWith("-")) display = display.substring(1);
	    else display = "-" + display;
	    
	}
	

	
	private void applyFunction(String func) {

		if(display.equals("Error")||startNewNumber) return;
		
		double number = getDoubleValueFromDisplay();
	    Double result = executeFunction(func,number);
		
		if(result == null) {
			setErrorState();
            
			switch(func) {
			case "√":historyDown = "√(" + nf.format(number) + ") = Error";
	        	break;
	        case "1/x":historyDown = "1/(" + nf.format(number) + ") = Error";
	        	break;
			}
			return;
		}
		
		display=nf.format(result);
		
		if(operator!=null) {
			calculate();
	    	return;
		}
	    
		if(!historyDown.isBlank())historyUp = historyDown;
		
		switch(func) {
		case "√":historyDown = "√(" + nf.format(number) + ") = " + nf.format(result);
			break;
		case "x²":historyDown = nf.format(number) + "² = "+ nf.format(result);
			break;
		case "1/x":historyDown = "1/(" + nf.format(number) + ") = "+ nf.format(result);
			break;
		}
		
	    firstNumber = result;       
	}

	
	
	private Double executeFunction(String function,double number) {
		switch(function) {
		case "√":if(number<0)return null;
				 return calculator.squareRoot(number);
				 
		case "x²":return calculator.square(number);
		
		case "1/x":if(number==0)return null;
		 		   return calculator.divideByNumber(number);
		default:return null;
		}
	}
	
	
	
	
}

