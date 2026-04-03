package engine;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import calculations.BasicCalculations;

public final class CalculatorEngine{
		
	private NumberFormat nf =  NumberFormat.getInstance(Locale.US);

	private BasicCalculations calculator = new BasicCalculations();
	
	private double firstNumber,secondNumber;
	private boolean startNewNumber = true;
	private String operator;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";

	
//  PROBLEMS:
//  na mporw na patisw 0.001
//  IMPROVEMENTS:
//  Thelw na ftiaksw to kodika ths applyFunction , calculate kai ths delete 
	
	
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
			return -1;
		}
		
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
	    } else if(!startNewNumber&&(display.equals("0")||display.equals("-0"))){
	    	display = num;
	    	startNewNumber = false;
	    }else  {
	    	display += num;
	    	display= nf.format(getDoubleValueFromDisplay());
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

	    if(nf.format(result).equals("∞")||nf.format(result).equals("-∞")) {
	    	historyUp = historyDown + " " + nf.format(secondNumber) + " = Error";
	    	display = "Error";
	    	historyDown = "";
		    firstNumber = 0;
		    operator = null;
		    startNewNumber = true;
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
	    
		double number;			
		number = getDoubleValueFromDisplay();
	    display = nf.format(number);
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
	

//thelw na brw alo pio clean tropo pou na kanei to idio pragma 
	private void applyFunction(String func) {

		if(display.equals("Error")||startNewNumber) return;
		
		if(operator!=null) {
		   
			double number;
		    double result = 0;
			number = getDoubleValueFromDisplay();
		    		    
		    switch(func) {

		        case "√":
		            if(number < 0) {
		                display = "Error";
		                startNewNumber = true;
		                firstNumber = 0;
		        	    secondNumber = 0;
		        	    operator = null;
		                historyDown = "√(" + nf.format(number) + ") = Error";
		                return;
		            }
		            result = calculator.squareRoot(number);
		            display=nf.format(result);
		            break;

		        case "x²":
		            result = calculator.square(number);
		            display=nf.format(result);
		            break;

		        case "1/x":
		            if(number == 0) {
		                display = "Error";
		                startNewNumber = true;
		                firstNumber = 0;
		        	    secondNumber = 0;
		        	    operator = null;
		        	    historyDown = "1/(" + nf.format(number) + ") = Error";
		                return;
		            }
		            result = calculator.divideByNumber(number);
		            display=nf.format(result);
		            break;

		        default:
		            return;
		    
		    }
		    calculate();
		    return;
		}
		
		
		double number;
	    double result = 0;
		number = getDoubleValueFromDisplay();
	    
	    switch(func) {

	        case "√":
	            if(number < 0) {
	                display = "Error";
	                startNewNumber = true;
	                firstNumber = 0;
	        	    secondNumber = 0;
	        	    operator = null;
	                historyDown = "√(" + nf.format(number) + ") = Error";
	                return;
	            }
	            result = calculator.squareRoot(number);
	            if(!historyDown.isBlank())historyUp = historyDown;
	            historyDown = "√(" + nf.format(number) + ") = " + nf.format(result);
	            break;

	        case "x²":
	            result = calculator.square(number);
	            if(!historyDown.isBlank())historyUp = historyDown;
	            historyDown = nf.format(number) + "² = "+ nf.format(result);
	            break;

	        case "1/x":
	            if(number == 0) {
	                display = "Error";
	                startNewNumber = true;
	                firstNumber = 0;
	        	    secondNumber = 0;
	        	    operator = null;
	        	    historyDown = "1/(" + nf.format(number) + ") = Error";
	                return;
	            }
	            result = calculator.divideByNumber(number);
	           
	            if(!historyDown.isBlank())historyUp = historyDown;
	           
	            historyDown = "1/(" + nf.format(number) + ") = "+ nf.format(result);
	            break;

	        default:
	            return;
	    }

	    display = nf.format(result);
	    firstNumber = result;
	}

	
}

