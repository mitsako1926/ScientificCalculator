package engine;

import javax.swing.SwingUtilities;
import gui.CalculatorFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CalculatorFrame());
	}
}
