package calculations;

public class BasicCalculations {

	public BasicCalculations(){
		
	}
	
	public double add(double firstNumber,double secondNumber){
		return firstNumber + secondNumber;
	}
	
	public double subtract(double firstNumber, double secondNumber){
		return firstNumber - secondNumber;
	}
	
	public double multiply(double firstNumber,double  secondNumber){
		return firstNumber * secondNumber;
	}
	
	public double devide(double firstNumber,double  secondNumber){
		return firstNumber / secondNumber;
	}
	
	public double modular(double firstNumber,double secondNumber){
		return firstNumber % secondNumber;
	}
	
	public double squareRoot(double number){
		return Math.sqrt(number);
	}
	
	public double square(double number){
		return Math.pow(number, 2);
	}
	
	public double divideByNumber(double number){
		return  (number!=0) ? 1/number : 0;
	}
	
	public double equals(double firstNumber,double secondNumber){
		return firstNumber / secondNumber;
	}
	
}
