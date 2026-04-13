package engine;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

import calculations.AdvancedCalculations;
import calculations.BasicCalculations;
import gui.CalculatorSettingsFrame;

public final class CalculatorEngine{
		
	private final NumberFormat nf =  NumberFormat.getInstance(Locale.US);
	{nf.setGroupingUsed(true); nf.setMaximumFractionDigits(6);}
	
	private final BasicCalculations calculator = new BasicCalculations();
	private final AdvancedCalculations calculatorAdvanced = new AdvancedCalculations();
	private CalculatorSettingsFrame settingsFrame;
	
	private Runnable displayRefreshListener;
	
	private double firstNumber,secondNumber,secondNumberArgument;
	
	private boolean startNewNumber = true;
	
	private String operator;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";
	private String function;
	
	private int fontSize;
	
	private Consumer<Integer> fontListener;
	
	private final List<Consumer<Boolean>> themeListeners = new ArrayList<>();
	
	private int fontVar =5;
	private int decimalVar = 6;
	private boolean dark = true;
	
	private final List<HistoryEntity> historyList = new ArrayList<>();
	
	
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
	
	public int getFontVar() {
		return fontVar;
	}
	
	public void setFontVar(int fontVar) {
		this.fontVar = fontVar;
	}
	
	public int getDecimalVar() {
		return decimalVar;
	}
	
	public void setDecimalVar(int decimalVar) {
		nf.setMaximumFractionDigits(decimalVar);
		this.decimalVar = decimalVar;
	}
	
	public boolean getDark() {
		return dark;
	}
	
	public void setDark(boolean dark) {
		this.dark = dark;
	}
	
	
	
	public void setFontListener(Consumer<Integer> fontListener) {
	    this.fontListener = fontListener;
	}
	
	
	public void setThemeListener(Consumer<Boolean> themeListener) {
	    this.themeListeners.add(themeListener);
	}
	
	
	
	public void setFontSize(int size) {
	    this.fontSize = 3*(size -5);
	    this.fontVar = size;
	    if (fontListener != null) {
	        fontListener.accept(fontSize);
	    }
	}
	
	
	
	
	public void setTheme() {
		
		if (themeListeners != null) {
			for(Consumer<Boolean> listener : themeListeners) {
				listener.accept(this.dark);
			} 
		}
	}
	

	
	public double getDoubleValueFromDisplay() {
		
		if (display.equals("π")) return Math.PI;
	    if (display.equals("e")) return Math.E;
		
		Number number;
		try {
			number = nf.parse(display);
			return number.doubleValue();
		} catch (ParseException e) {
			setErrorState();
			return 0;
		}
	}
	
	
	
	public void saveHistory(String filePath) {
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
	        out.writeObject(historyList);
	    } catch (IOException e) {
	        display = "Error in save history";
	    }
	}
	
	

	public void saveHistoryAsText(String filePath) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
	        for (HistoryEntity entry : historyList.reversed()) {
	            writer.println(entry.toString());
	        }
	    } catch (IOException e) {
	    	display = "Error in save history";
	    }
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void loadHistory(String filePath) {
	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
	        List<HistoryEntity> loaded = (List<HistoryEntity>) in.readObject();
	        
	        historyList.clear();
	        historyList.addAll(loaded);

	        SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());

	    } catch (IOException | ClassNotFoundException e) {
	    	display = "Error in load history";
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
	    
		if (text == null || text.contains("Error") || text.contains("∞")||text.contains("NaN")|| text.equals("π") || text.equals("e")) return "0";
		
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
	

	
	public void clearHistory(){
		if(historyList!=null) {
			historyList.clear();
			historyUp = "";
			refreshMainDisplay();
			SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());
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

		if(display.contains("Error")||display.contains("∞")||display.contains("NaN")) {
			display = num;
		    startNewNumber = false;
		    operator = null;
			return;
		}
		
		String raw = display.replace(",", "").replace("-", "").replace(".", "");
		if (raw.length() >= 15) return;
		
		if (startNewNumber || display.equals("0") || display.equals("-0")) {
		    display = num;
		    startNewNumber = false;
		}else {
			
			if( display.contains("e")||display.contains("π") || (!display.isBlank()&&num.equals("e")||num.equals("π") ) )return;
		    
			display += num;
		    display = reformatDisplay(display);
		}
	}
	

	
	private void appendDot() {
		
		if(display.contains("e")||display.contains("π"))return;
		
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
		
		if((operator != null && startNewNumber)||display.contains("Error")) return;
		
		if(operator != null && !startNewNumber) calculate();
		

		firstNumber = getDoubleValueFromDisplay();
		
	    operator = op;
	    
	    if(function!=null) {
	    	historyUp = historyDown;
	    	function = null;
	    }
	    
	    historyDown = nf.format(firstNumber) + " " + op;
	    
	    if(display.endsWith(".")||display.endsWith(","))display = display.substring(0, display.length() - 1);
	    
	    display+=op;
	    startNewNumber = true;
	}
	
	
	
	private void calculate() {

	    if(operator == null || startNewNumber) return;
	    
		secondNumber = getDoubleValueFromDisplay();
	    
	    double result = 0;

	    if(operator==null)return;
	    
	    switch(operator) {
	        case "+": result = calculator.add(firstNumber, secondNumber); break;
	        case "-": result = calculator.subtract(firstNumber, secondNumber); break;
	        case "×": result = calculator.multiply(firstNumber, secondNumber); break;
	        case "÷": result = calculator.divide(firstNumber, secondNumber); break;
	        case "%": result = calculator.modular(firstNumber, secondNumber); break;
	        case "^": result = calculatorAdvanced.power(firstNumber, secondNumber); break;
	    }
	    
	    if(function==null) {
		    historyList.add(new HistoryEntity(firstNumber,secondNumber,result,operator,historyUp,function,Double.isInfinite(result)||Double.isNaN(result)));
	    }else {
		    historyList.add(new HistoryEntity(firstNumber,secondNumberArgument,result,operator,historyUp,function,Double.isInfinite(result)||Double.isNaN(result)));
	    }
	    SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());
	    
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
		
		if (display.contains("Error")||display.contains("∞")||display.contains("NaN")||display.contains("e")||display.contains("π")) {
		    display = "0";
		    startNewNumber = true;
		    operator = null;
		    return;
		}
		
	    if(startNewNumber && operator!=null) {
	    	
	    	if(display.equals("0"))return;
	    	
	    	display = display.substring(0, display.length() - 1);
	    	display = reformatDisplay(display);
	    	
	    	startNewNumber = false;
	    	operator =null;
	    	historyDown = "";
	    	return;
	    }
	    
	    if(display.length() == 1 || display.equals("-")) {
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

	    if(result == null||result.isInfinite()||result.isNaN()) {
            
			String functionText = "";
			functionText = helperFunctionText(func,functionText,number);
			
	        
	        if (operator != null) {
	            historyDown = nf.format(firstNumber) + " " + operator + " " + functionText + " = Error";
				historyList.add(new HistoryEntity(firstNumber,number,0,operator,historyUp,function,true));
	        } else {
	            historyDown = functionText + " = Error";
				historyList.add(new HistoryEntity(number,0,0,operator,historyUp,function,true));
	        }
			
	        SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());
			setErrorState();

			return;
		}

		display=nf.format(result);
		
		if(operator!=null) {
			calculate();
	    	return;
		}
	    
		if(!historyDown.isBlank()) historyUp = historyDown;
			
		helperHistoryDown(func,number,result);
		
		historyList.add(new HistoryEntity(number,0,result,operator,historyUp,function,false));
		SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());
	    firstNumber = result; 
	}

	
	
	public void scientificFunctionPressed(String scientificFunction) {
		applyFunction(scientificFunction);
		refreshMainDisplay();
	}
	
	
	
	private void helperHistoryDown(String func, double number, Double result) {
		
		switch(func) {
		    case "√":
		        historyDown = "√(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "x²":
		        historyDown = nf.format(number) + "² = " + nf.format(result);
		        break;
		    case "1/":
		        historyDown = "1/(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "n!":
		        historyDown = nf.format(number) + "! = " + nf.format(result);
		        break;
		    case "⌈x⌉":
		        historyDown = "⌈"+nf.format(number) + "⌉ = " + nf.format(result);
		        break;
		    case "⌊x⌋":
		        historyDown = "⌊"+nf.format(number) + "⌋ = " + nf.format(result);
		        break;
		    case "ln":
		        historyDown = "ln(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "log":
		        historyDown = "log(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "10^x":
		        historyDown = "10^(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "abs":
		        historyDown = "|" + nf.format(number) + "| = " + nf.format(result);
		        break;
		    case "sin":
		        historyDown = "sin(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "cos":
		        historyDown = "cos(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "tan":
		        historyDown = "tan(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "cot":
		        historyDown = "cot(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "asin":
		        historyDown = "asin(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "acos":
		        historyDown = "acos(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "atan":
		        historyDown = "atan(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "acot":
		        historyDown = "acot(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		}
		
	}

	private String helperFunctionText(String func,String functionText,double number) {
		
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
		    case "n!":
		        functionText = nf.format(number) + "!";
		        break;
		    case "⌈x⌉":
		        functionText = "⌈"+nf.format(number) + "⌉";
		        break;
		    case "⌊x⌋":
		        functionText = "⌊"+nf.format(number) + "⌋";
		        break;
		    case "ln":
		        functionText = "ln(" + nf.format(number) + ")";
		        break;
		    case "log":
		        functionText = "log(" + nf.format(number) + ")";
		        break;
		    case "10^x":
		        functionText = "10^(" + nf.format(number) + ")";
		        break;
		    case "abs":
		        functionText = "|" + nf.format(number) + "|";
		        break;
		    case "sin":
		        functionText = "sin(" + nf.format(number) + ")";
		        break;
		    case "cos":
		        functionText = "cos(" + nf.format(number) + ")";
		        break;
		    case "tan":
		        functionText = "tan(" + nf.format(number) + ")";
		        break;
		    case "cot":
		        functionText = "cot(" + nf.format(number) + ")";
		        break;
		    case "asin":
		        functionText = "asin(" + nf.format(number) + ")";
		        break;
		    case "acos":
		        functionText = "acos(" + nf.format(number) + ")";
		        break;
		    case "atan":
		        functionText = "atan(" + nf.format(number) + ")";
		        break;
		    case "acot":
		        functionText = "acot(" + nf.format(number) + ")";
		        break;
		}
		
		return functionText;
		
	}
	
	
	
	
	private Double executeFunction(String function,double number) {
		
		switch(function) {
			case "√":if(number<0)return null;
				return calculator.squareRoot(number);
					 
			case "x²":return calculator.square(number);
			
			case "1/":if(number==0)return null;
			 	return calculator.divideByNumber(number);
			case "n!":
			    if (number < 0 || number != Math.floor(number)) return null;
			    return calculatorAdvanced.factorial(number);
			case "⌈x⌉":
			    return calculatorAdvanced.ceiling(number);
			case "⌊x⌋":
			    return calculatorAdvanced.floor(number);
			case "ln":
			    if (number <= 0) return null;
			    return calculatorAdvanced.ln(number);
	
			case "log":
			    if (number <= 0) return null;
			    return calculatorAdvanced.log(number);
	
			case "10^x":
			    return calculatorAdvanced.tenPower(number);
	
			case "abs":
			    return calculatorAdvanced.abs(number);
	
			case "sin":
			    return calculatorAdvanced.sin(number);
	
			case "cos":
			    return calculatorAdvanced.cos(number);
	
			case "tan": {
			    double angle = number;
			    if (calculatorAdvanced.isDegreeMode()) {
			        angle = Math.toRadians(angle);
			    }

			    if (Math.abs(Math.cos(angle)) < 1e-10) return null;
			    return calculatorAdvanced.tan(number);
			}

			case "cot": {
			    double angle = number;
			    if (calculatorAdvanced.isDegreeMode()) {
			        angle = Math.toRadians(angle);
			    }

			    if (Math.abs(Math.sin(angle)) < 1e-10) return null;
			    return calculatorAdvanced.cot(number);
			}
	
			case "asin":
			    if (number < -1 || number > 1) return null;
			    return calculatorAdvanced.asin(number);
	
			case "acos":
			    if (number < -1 || number > 1) return null;
			    return calculatorAdvanced.acos(number);
	
			case "atan":
			    return calculatorAdvanced.atan(number);
	
			case "acot":
			    if (number == 0) return null;
			    return calculatorAdvanced.acot(number);
				
			default:return null;
		}
	}

	
	
	public void toggleDegreeMode() {
		calculatorAdvanced.toggleDegreeMode();
		refreshMainDisplay();
	}
	
	
	
	public void addScientificNumber(String number) {
		if(number.equals("random")) {
			String random = nf.format(calculatorAdvanced.random());
			appendNumber(random);
			refreshMainDisplay();
			return;
		}
		appendNumber(number);
		refreshMainDisplay();
	}
	
	
	
	public void addScientificOperator() {
		setOperator("^");
		refreshMainDisplay();
	}
	
}

