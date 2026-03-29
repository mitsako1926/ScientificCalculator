package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class CalculatorFrame extends JFrame{
	
	
	
	public CalculatorFrame() {
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Calculator");
		this.setIconImage(imageIcon.getImage());
		
		add(new CalculatorPanel());
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
