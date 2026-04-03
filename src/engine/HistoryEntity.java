package engine;

import java.text.NumberFormat;
import java.util.Locale;

public final class HistoryEntity {

	private final NumberFormat nf =  NumberFormat.getInstance(Locale.US);
	{nf.setGroupingUsed(true); nf.setMaximumFractionDigits(10);}
	
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
		
		if(operator==null) {
			
			if(function.equals("x²")) return nf.format(firstNumber)+"² =";
			return function+nf.format(firstNumber)+" =";
		
		}else {
			
			if(function==null)return nf.format(firstNumber)+" "+operator+" "+nf.format(secondNumber)+" =";
			else {
				
				if(function.equals("x²"))return nf.format(firstNumber)+" "+operator+" "+nf.format(secondNumber)+function+" =";
				else return nf.format(firstNumber)+" "+operator+" "+function+nf.format(secondNumber)+" =";
			}
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
		return getHistoryDown() + " " + nf.format(result);
		//return getHistoryUp();
	}
	
}
