package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.CalculatorEngine;

public class CalculatorSettingsPanel extends JPanel {
	
	private CardLayout cardLayout;
    private JPanel container;

    CalculatorSettingsPanel(CalculatorEngine engine) {
    	
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(350, 450));

        JButton showHistoryButton = new JButton("Show History");
        showHistoryButton.setFocusable(false);
        showHistoryButton.addActionListener(e -> {
            cardLayout.show(container, "history");
        });
        
        add(showHistoryButton,BorderLayout.CENTER);
    
    }

    public void setCardLayout(CardLayout cardLayout, JPanel container) {
        this.cardLayout = cardLayout;
        this.container = container;
    }
	
	
	
	
}

