package engine;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import calculations.BasicCalculations;
import gui.CalculatorSettingsFrame;

public final class CalculatorEngine{
		
	private final NumberFormat nf =  NumberFormat.getInstance(Locale.US);
	{nf.setGroupingUsed(true); nf.setMaximumFractionDigits(10);}
	
	private final BasicCalculations calculator = new BasicCalculations();
	private CalculatorSettingsFrame settingsFrame;
	
	private Runnable displayRefreshListener;
	
	private double firstNumber,secondNumber,secondNumberArgument;
	private boolean startNewNumber = true;
	private String operator;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";
	private String function;
	
	private final List<HistoryEntity> historyList = new ArrayList<>();
	
//  PROBLEMS:
//  find more bugs
//  history up is kinda bad
//  IMPROVEMENTS:
	
	public List<HistoryEntity> getHistoryList(){
		return new ArrayList<>(historyList);
	}
	
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
	    function =null;
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
	
	
	
	private void refreshSettingsPanelHistory() {
		if (settingsFrame != null && settingsFrame.isDisplayable()) {
			settingsFrame.refreshHistory();
		}
	}
	
	public void setDisplayRefreshListener(Runnable displayRefreshListener) {
	    this.displayRefreshListener = displayRefreshListener;
	}
	
	private void refreshMainDisplay() {
	    if (displayRefreshListener != null) {
	        displayRefreshListener.run();
	    }
	}
	
	public void loadFromHistory(HistoryEntity entry) {
		
		if(entry==null)return;
		
		if(!entry.isError()) {
			firstNumber = entry.getResult();
			display = nf.format(entry.getResult());
		}else {
			setErrorState();
			historyDown = entry.getHistoryDown();
			historyUp = entry.getHistoryUp();
			refreshMainDisplay();
			return;
		}
		
		historyDown = entry.getHistoryDown();
		historyUp = entry.getHistoryUp();
		
		operator = null;
		function = null;
		startNewNumber = false;
		secondNumber = 0;
		
		refreshMainDisplay();
	}
	
	
	
	public void setDisplayRefreshListener(){
		
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
	        case "≡":
	        	settings();
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
	        case "1/":
	            applyFunction(buttonPressed);
	            break;
	    }
	}
	
	
	private void settings() {
		if (settingsFrame == null || !settingsFrame.isDisplayable()) settingsFrame = new CalculatorSettingsFrame(this);
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
	    function =null;
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
	    
	    if(function==null) {
		    historyList.add(new HistoryEntity(firstNumber,secondNumber,result,operator,historyUp,function,Double.isInfinite(result)||Double.isNaN(result)));
	    }else {
		    historyList.add(new HistoryEntity(firstNumber,secondNumberArgument,result,operator,historyUp,function,Double.isInfinite(result)||Double.isNaN(result)));
	    }
	    refreshSettingsPanelHistory();
	    
	    if(Double.isInfinite(result)|| Double.isNaN(result)) {
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
	    function = null;
	}
	

	
	private void delete() {
		
	    if(startNewNumber && operator!=null) {
	    	
	    	if(display.equals("0"))return;
	    	
	    	display = display.substring(0, display.length() - 1);
	    	display = reformatDisplay(display);
	    	
	    	startNewNumber = false;
	    	operator =null;
	    	historyDown = "";
	    	return;
	    }
	    
	    if((display.length() == 1 || display.equals("-") )||display.equals("Error")) {
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

	    if(display.equals("0")||display.equals("Error")) return;

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
		function = func;

		double number = getDoubleValueFromDisplay();
		secondNumberArgument = number;
	    Double result = executeFunction(func,number);
		
		if(result == null) {
            
			String functionText = "";

	        switch (func) {
	            case "√":
	                functionText = "√(" + nf.format(number) + ")";
	                break;
	            case "1/":
	                functionText = "1/(" + nf.format(number) + ")";
	                break;
	            case "x²":
	                functionText = nf.format(number) + "²";
	                break;
	        }
	        
	        if (operator != null) {
	            historyDown = nf.format(firstNumber) + " " + operator + " " + functionText + " = Error";
				historyList.add(new HistoryEntity(firstNumber,number,0,operator,historyUp,function,true));
	        } else {
	            historyDown = functionText + " = Error";
				historyList.add(new HistoryEntity(number,0,0,operator,historyUp,function,true));
	        }
			
			refreshSettingsPanelHistory();
			setErrorState();

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
		case "1/":historyDown = "1/(" + nf.format(number) + ") = "+ nf.format(result);
			break;
		}
		
		historyList.add(new HistoryEntity(number,0,result,operator,historyUp,function,false));
		refreshSettingsPanelHistory();
	    firstNumber = result; 
	    
	}

	
	
	private Double executeFunction(String function,double number) {
		switch(function) {
		case "√":if(number<0)return null;
				 return calculator.squareRoot(number);
				 
		case "x²":return calculator.square(number);
		
		case "1/":if(number==0)return null;
		 		   return calculator.divideByNumber(number);
		default:return null;
		}
	}
	
	
	
	
}

