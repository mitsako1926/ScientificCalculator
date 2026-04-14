package engine;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public final class HistoryEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final NumberFormat nf =  NumberFormat.getInstance(Locale.US);
	
	{	
		nf.setGroupingUsed(true);
		nf.setMaximumFractionDigits(10);
	}
	
	private final double firstNumber;
	private final double secondNumber;
	private final double result;
	
	private final String operator;
	private final String historyUp;
	private final String function;
	
	private final boolean error;

	
	HistoryEntity(double firstNumber,double secondNumber,double result,String operator,String historyUp, String function, boolean error){
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.result = result;
		this.operator = operator;
		this.historyUp = historyUp;
		this.function = function;
		this.error = error;
	}
	
	
	public double getFirstNumber() {
		return firstNumber;
	}
	
	public double getSecondNumber() {
		return secondNumber;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public double getResult() {
		return result;
	}
	
	public String getHistoryDown() {
		
		if(operator==null) return textFunctionNoOperator();
		else {
			
			if(function==null)return nf.format(firstNumber)+" "+operator+" "+nf.format(secondNumber)+" =";
			else return textFunctionWithOperator();

		}
		
	}

	public String getHistoryUp() {
		return historyUp;
	}
	
	public String getFunction() {
		return function;
	}
	
	public boolean isError() {
	    return error;
	}

	public String toString() {
		return getHistoryDown() + " " + (error ? "Error" : nf.format(result));
	}
	
	
	
	private String textFunctionWithOperator() {
		
		switch (function) {

		    case "x²":
		        return nf.format(firstNumber) + " " + operator + " " + nf.format(secondNumber) + "² =";
	
		    case "n!":
		        return nf.format(firstNumber) + " " + operator + " " + nf.format(secondNumber) + "! =";
	
		    case "√":
		        return nf.format(firstNumber) + " " + operator + " √(" + nf.format(secondNumber) + ") =";
	
		    case "1/":
		        return nf.format(firstNumber) + " " + operator + " 1/(" + nf.format(secondNumber) + ") =";
	
		    case "⌈x⌉":
		    	return nf.format(firstNumber) + " " + operator + " ⌈" + nf.format(secondNumber) + "⌉ =";
		    
		    case "⌊x⌋":
		    	return nf.format(firstNumber) + " " + operator + " ⌊(" + nf.format(secondNumber) + "⌋ =";
		    
		    case "ln":
		        return nf.format(firstNumber) + " " + operator + " ln(" + nf.format(secondNumber) + ") =";
	
		    case "log":
		        return nf.format(firstNumber) + " " + operator + " log(" + nf.format(secondNumber) + ") =";
	
		    case "10^x":
		        return nf.format(firstNumber) + " " + operator + " 10^(" + nf.format(secondNumber) + ") =";
	
		    case "abs":
		        return nf.format(firstNumber) + " " + operator + " |" + nf.format(secondNumber) + "| =";
	
		    case "sin":
		        return nf.format(firstNumber) + " " + operator + " sin(" + nf.format(secondNumber) + ") =";
	
		    case "cos":
		        return nf.format(firstNumber) + " " + operator + " cos(" + nf.format(secondNumber) + ") =";
	
		    case "tan":
		        return nf.format(firstNumber) + " " + operator + " tan(" + nf.format(secondNumber) + ") =";
	
		    case "cot":
		        return nf.format(firstNumber) + " " + operator + " cot(" + nf.format(secondNumber) + ") =";
	
		    case "asin":
		        return nf.format(firstNumber) + " " + operator + " asin(" + nf.format(secondNumber) + ") =";
	
		    case "acos":
		        return nf.format(firstNumber) + " " + operator + " acos(" + nf.format(secondNumber) + ") =";
	
		    case "atan":
		        return nf.format(firstNumber) + " " + operator + " atan(" + nf.format(secondNumber) + ") =";
	
		    case "acot":
		        return nf.format(firstNumber) + " " + operator + " acot(" + nf.format(secondNumber) + ") =";
	
		    default:
		        return nf.format(firstNumber) + " " + operator + " " + function + nf.format(secondNumber) + " =";
		}
		
	}
	
	
	
	private String textFunctionNoOperator() {
		
		switch (function) {

		    case "√":
		        return "√(" + nf.format(firstNumber) + ") =";
	
		    case "1/":
		        return "1/(" + nf.format(firstNumber) + ") =";
	
		    case "x²":
		        return nf.format(firstNumber) + "² =";
	
		    case "n!":
		        return nf.format(firstNumber) + "! =";
	
		    case "⌈x⌉":
		    	return  " ⌈" + nf.format(firstNumber) + "⌉ =";
		    
		    case "⌊x⌋":
		    	return " ⌊" + nf.format(firstNumber) + "⌋ =";
		        
		    case "ln":
		        return "ln(" + nf.format(firstNumber) + ") =";
	
		    case "log":
		        return "log(" + nf.format(firstNumber) + ") =";
	
		    case "10^x":
		        return "10^(" + nf.format(firstNumber) + ") =";
	
		    case "abs":
		        return "|" + nf.format(firstNumber) + "| =";
	
		    case "sin":
		        return "sin(" + nf.format(firstNumber) + ") =";
	
		    case "cos":
		        return "cos(" + nf.format(firstNumber) + ") =";
	
		    case "tan":
		        return "tan(" + nf.format(firstNumber) + ") =";
	
		    case "cot":
		        return "cot(" + nf.format(firstNumber) + ") =";
	
		    case "asin":
		        return "asin(" + nf.format(firstNumber) + ") =";
	
		    case "acos":
		        return "acos(" + nf.format(firstNumber) + ") =";
	
		    case "atan":
		        return "atan(" + nf.format(firstNumber) + ") =";
	
		    case "acot":
		        return "acot(" + nf.format(firstNumber) + ") =";
	
		    default:
		        return function + nf.format(firstNumber) + " =";
		}
		
	}
	
	
	
}
