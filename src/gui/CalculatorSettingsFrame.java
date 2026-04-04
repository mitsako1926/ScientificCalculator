package gui;

import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import engine.CalculatorEngine;

public final class CalculatorSettingsFrame extends JFrame{
	
	private final CalculatorSettingsPanel settingsPanel;
	
	public CalculatorSettingsFrame(CalculatorEngine engine) {
		
		settingsPanel = new CalculatorSettingsPanel(engine);
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Settings");
		setAlwaysOnTop(true);
		setIconImage(imageIcon.getImage());
		
		add(settingsPanel);
		
		pack();
		
		Frame calculatorFrame = null;

		for (Frame frame : Frame.getFrames()) {
		    if (frame.isShowing() && frame instanceof CalculatorFrame) {
		        calculatorFrame = frame;
		        break;
		    }
		}
		
		int x = calculatorFrame.getX() + calculatorFrame.getWidth() + 10;
		int y = calculatorFrame.getY() + (calculatorFrame.getHeight() - getHeight()) / 2;
		setLocation(x, y);
		setVisible(true);
	}
	
	
	
	public void refreshHistory() {
        settingsPanel.refreshHistory();
    }
	


}
