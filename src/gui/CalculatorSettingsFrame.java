package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class CalculatorSettingsFrame extends JFrame{
	
	CalculatorSettingsFrame() {
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Settings");
		this.setIconImage(imageIcon.getImage());
		
		add(new CalculatorSettingsPanel());
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
