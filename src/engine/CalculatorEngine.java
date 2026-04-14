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
	{
		nf.setGroupingUsed(true); nf.setMaximumFractionDigits(6);
	}
	
	private final BasicCalculations calculator = new BasicCalculations();
	private final AdvancedCalculations calculatorAdvanced = new AdvancedCalculations();
	private final CalculatorEngineHelper engineHelper = new CalculatorEngineHelper();
	private CalculatorSettingsFrame settingsFrame;
	
	private Runnable displayRefreshListener;
	
	private double firstNumber,secondNumber,secondNumberArgument;
	
	private boolean startNewNumber = true;
	private boolean dark = true;
	
	private int fontVar = 5;
	private int decimalVar = 6;
	private int fontSize;
	
	private String operator,function;
	private String display = "0";
	private String historyUp = "";
	private String historyDown = "";	
	
	private Consumer<Integer> fontListener;
	
	private final List<Consumer<Boolean>> themeListeners = new ArrayList<Consumer<Boolean>>();
	private final List<HistoryEntity> historyList = new ArrayList<HistoryEntity>();
	
	
	
	//GETTER METHODS
	
	
	
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
	
	public boolean getDark() {
		return dark;
	}
	
	
	
	//OTHER HELPFUL METHODS
	
	
	
	public void setDecimalVar(int decimalVar) {
		nf.setMaximumFractionDigits(decimalVar);
		this.decimalVar = decimalVar;
		
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
	
	
	
	private void setErrorState() {
		display = "Error";
        startNewNumber = true;
        firstNumber = 0;
	    secondNumber = 0;
	    operator = null;
	    
	}
	
	
	
	private void refreshSettingsPanelHistory() {
		if (settingsFrame != null && settingsFrame.isDisplayable()) 
			settingsFrame.refreshHistory();
		
	}
	
	
	
	public void setDisplayRefreshListener(Runnable displayRefreshListener) {
	    this.displayRefreshListener = displayRefreshListener;
	    
	}
	
	
	
	private void refreshMainDisplay() {
	    
		if (displayRefreshListener != null) 
	        displayRefreshListener.run();
	    
	}
	
	
	
	//HISTORY METHODS
	
	
	
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
	
	
	
	//PRESS METHOD THAT EVERY BUTTON (IN THE MAIN FRAME) INVOKES
	
	
	
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
	
	
	
	//ALL BUTTON METHODS
	
	
	
	private void settings() {
		if (settingsFrame == null || !settingsFrame.isDisplayable()) 
			settingsFrame = new CalculatorSettingsFrame(this);
		
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
		    display = engineHelper.reformatDisplay(display,nf);
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
	    firstNumber = secondNumber = 0;
	    operator = function = null;
	    historyUp = historyDown = "";
	    startNewNumber = true;
	    
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
	    	display = engineHelper.reformatDisplay(display,nf);
	    	
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
	    display = engineHelper.reformatDisplay(display,nf);
	    
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
	    Double result = engineHelper.executeFunction(func,number,calculatorAdvanced,calculator);

	    if(result == null||result.isInfinite()||result.isNaN()) {
            
			String functionText = "";
			
			functionText = engineHelper.helperFunctionText(func,number,nf);
	        
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
			
		historyDown = engineHelper.helperHistoryDown(func,number,result,nf);
		
		historyList.add(new HistoryEntity(number,0,result,operator,historyUp,function,false));
		SwingUtilities.invokeLater(() -> refreshSettingsPanelHistory());
	    firstNumber = result; 
	}

	
	
	public void scientificFunctionPressed(String scientificFunction) {
		applyFunction(scientificFunction);
		refreshMainDisplay();
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

