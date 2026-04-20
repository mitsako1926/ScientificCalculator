package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class CalculatorFrame extends JFrame{
	
	
	
	public CalculatorFrame() {
		
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/calculatorImage.jpg"));
		Image img = imageIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Calculator");
		setIconImage(img);
		
		add(new CalculatorPanel());
		
		pack();
		
		int x = 0;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
		
		setVisible(true);
	}
	
}
