package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.CalculatorEngine;

public final class CalculatorSettingsFrame extends JFrame{
	
	
	private final CalculatorShowHistoryPanel showHistoryPanel;
	private final CalculatorEngine engine;
	
	
	public CalculatorSettingsFrame(CalculatorEngine engine1) {
		
		//MAIN PANEL 
		ImageIcon imageIcon = new ImageIcon("calculatorImage.jpg");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Settings");
		setAlwaysOnTop(true);
		setIconImage(imageIcon.getImage());
		
		this.engine = engine1;
		
		CardLayout cardLayout = new CardLayout();
		JPanel content = new JPanel(cardLayout);
		
		
		//RIGHT PANELS
		CalculatorGeneralPanel generalPanel = new CalculatorGeneralPanel(engine);
		generalPanel.loadSettings();
		
		CalculatorHistoryPanel historyPanel = new CalculatorHistoryPanel(engine);
		historyPanel.setCardLayout(cardLayout, content);
		
		CalculatorScientificPanel scientificPanel = new CalculatorScientificPanel(engine);
		
		showHistoryPanel = new CalculatorShowHistoryPanel(engine);
		showHistoryPanel.setCardLayout(cardLayout, content);
		

		content.add(generalPanel, "general");
		content.add(historyPanel, "history");
		content.add(showHistoryPanel,"show history");
		content.add(scientificPanel, "scientific");

		//SIDEBAR
		CalculatorSettingsPanel sidebar = new CalculatorSettingsPanel(cardLayout, content,engine);

		
		//ADD THE SIDEBAR AND THE CONTENT PANEL TO THE MAIN PANEL
		add(sidebar, BorderLayout.WEST);
		add(content, BorderLayout.CENTER);
		
		
		
		pack();
		
		//FIND THE OPEN FRAME AND ITS COORDINATES
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
		
	    engine.setTheme();

	}
	
	
	
	public void refreshHistory() {
		showHistoryPanel.refreshHistory();
    }


}


