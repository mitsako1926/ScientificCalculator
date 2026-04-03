package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class CalculatorFrame extends JFrame{
	
	
	
	public CalculatorFrame() {
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Calculator");
		setIconImage(imageIcon.getImage());
		
		add(new CalculatorPanel());
		
		pack();
		
		int x = 0;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);
	}
	
}
