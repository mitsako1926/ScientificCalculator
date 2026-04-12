package calculations;

public final class AdvancedCalculations {

	private boolean isDegreeMode = true;

	
	public double factorial(double x) {	    
		
		int n = (int) x;
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public double ln(double x) {
        return Math.log(x);
    }

    public double log(double x) {
        return Math.log10(x);
    }

    public double tenPower(double x) {
        return Math.pow(10, x);
    }

    public double abs(double x) {
        return Math.abs(x);
    }

    public double sin(double x) {
        if (isDegreeMode) x = Math.toRadians(x);
        return Math.sin(x);
    }

    public double cos(double x) {
        if (isDegreeMode) x = Math.toRadians(x);
        return Math.cos(x);
    }

    public double tan(double x) {
        if (isDegreeMode) x = Math.toRadians(x);
        return Math.tan(x);
    }
    
    public double cot(double x) {
        if (isDegreeMode) x = Math.toRadians(x);
        return 1.0 / Math.tan(x);
    }

    public double asin(double x) {
        double result = Math.asin(x);
        return isDegreeMode ? Math.toDegrees(result) : result;
    }

    public double acos(double x) {
        double result = Math.acos(x);
        return isDegreeMode ? Math.toDegrees(result) : result;
    }

    public double atan(double x) {
        double result = Math.atan(x);
        return isDegreeMode ? Math.toDegrees(result) : result;
    }

    public double acot(double x) {
        double result = Math.atan(1.0 / x);
        return isDegreeMode ? Math.toDegrees(result) : result;
    }
    
    public int random() {
        return (int) (Math.random() * 1000);
    }
	
    public void toggleDegreeMode() {
        isDegreeMode = !isDegreeMode;
    }
	
    public boolean isDegreeMode() {
        return isDegreeMode;
    }
}
