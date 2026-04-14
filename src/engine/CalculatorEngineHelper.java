package engine;

import java.text.NumberFormat;

import calculations.AdvancedCalculations;
import calculations.BasicCalculations;

public class CalculatorEngineHelper {

	
	public String reformatDisplay(String text,NumberFormat nf) {
	    
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
	
	
	
	public Double executeFunction(String function,double number,AdvancedCalculations calculatorAdvanced,BasicCalculations calculator) {
			
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
	
	
	
	public String helperHistoryDown(String func, double number, Double result,NumberFormat nf) {
		
		String text = null;
		
		switch(func) {
			case "√":
		        text = "√(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "x²":
		        text = nf.format(number) + "² = " + nf.format(result);
		        break;
		    case "1/":
		        text = "1/(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "n!":
		        text = nf.format(number) + "! = " + nf.format(result);
		        break;
		    case "⌈x⌉":
		        text = "⌈"+nf.format(number) + "⌉ = " + nf.format(result);
		        break;
		    case "⌊x⌋":
		        text = "⌊"+nf.format(number) + "⌋ = " + nf.format(result);
		        break;
		    case "ln":
		        text = "ln(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "log":
		        text = "log(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "10^x":
		        text = "10^(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "abs":
		        text = "|" + nf.format(number) + "| = " + nf.format(result);
		        break;
		    case "sin":
		        text = "sin(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "cos":
		        text = "cos(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "tan":
		        text = "tan(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "cot":
		        text = "cot(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "asin":
		        text = "asin(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "acos":
		        text = "acos(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "atan":
		        text = "atan(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		    case "acot":
		        text = "acot(" + nf.format(number) + ") = " + nf.format(result);
		        break;
		}
		
		return text;
		
	}
	
	
	
	public String helperFunctionText(String func,double number,NumberFormat nf) {
		
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



}
