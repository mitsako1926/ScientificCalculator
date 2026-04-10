package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.CalculatorEngine;

public final class CalculatorSettingsFrame extends JFrame{
	
	private final CalculatorHistoryPanel historyPanel;
	private final CalculatorShowHistoryPanel showHistoryPanel;
	private final CalculatorEngine engine;
	
	public CalculatorSettingsFrame(CalculatorEngine engine1) {
		
		this.engine = engine1;
		
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		
		CardLayout cardLayout = new CardLayout();
		JPanel content = new JPanel(cardLayout);
		
		
		//right panels
		CalculatorGeneralPanel generalPanel = new CalculatorGeneralPanel(engine);
		generalPanel.loadSettings();
		historyPanel = new CalculatorHistoryPanel(engine);
		historyPanel.setCardLayout(cardLayout, content);
		showHistoryPanel = new CalculatorShowHistoryPanel(engine);
		showHistoryPanel.setCardLayout(cardLayout, content);
		CalculatorScientificPanel scientificPanel = new CalculatorScientificPanel(engine);

		content.add(generalPanel, "general");
		content.add(historyPanel, "history");
		content.add(showHistoryPanel,"show history");
		content.add(scientificPanel, "scientific");

		//sidebar
		CalculatorSettingsPanel sidebar = new CalculatorSettingsPanel(cardLayout, content,engine);

		setLayout(new BorderLayout());
		add(sidebar, BorderLayout.WEST);
		add(content, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Settings");
		setAlwaysOnTop(true);
		setIconImage(imageIcon.getImage());
		
		
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
		showHistoryPanel.refreshHistory();
    }


}


