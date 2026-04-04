package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import engine.CalculatorEngine;
import engine.HistoryEntity;

public final class CalculatorSettingsFrame extends JFrame{
	
	private final CalculatorSettingsPanel settingsPanel;
	private final CalculatorHistoryPanel historyPanel;
	
	public CalculatorSettingsFrame(CalculatorEngine engine) {
		
		settingsPanel = new CalculatorSettingsPanel(engine);
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		
		historyPanel = new CalculatorHistoryPanel(engine);
		
		settingsPanel.setCardLayout(cardLayout, container);
		historyPanel.setCardLayout(cardLayout, container);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Settings");
		setAlwaysOnTop(true);
		setIconImage(imageIcon.getImage());
		
		container.add(settingsPanel, "settings");
		container.add(historyPanel, "history");

		add(container);
		
		pack();
		
		Frame calculatorFrame = null;

		for (Frame frame : Frame.getFrames()) {
		    if (frame.isShowing() && frame instanceof CalculatorFrame) {
		        calculatorFrame = frame;
		        break;
		    }
		}
		
		if(calculatorFrame!=null) {
			int x = calculatorFrame.getX() + calculatorFrame.getWidth() + 10;
			int y = calculatorFrame.getY() + (calculatorFrame.getHeight() - getHeight()) / 2;
			setLocation(x, y);
		}else setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	
	
	public void refreshHistory() {
        historyPanel.refreshHistory();
    }
	


}


