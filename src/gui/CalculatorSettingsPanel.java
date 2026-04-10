package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.CalculatorEngine;

public class CalculatorSettingsPanel extends JPanel {
	
	private CardLayout cardLayout;
    private JPanel container,panelButtons;
    
    private final JButton showHistoryButton = new JButton("History");
    private final JButton showGeneralButton = new JButton("General");
    private final JButton showScientificButton = new JButton("Scientific");
    
    private final CalculatorEngine engine;
    
    CalculatorSettingsPanel(CardLayout cardLayout,JPanel container,CalculatorEngine engine) {

    	this.engine = engine;
    	this.cardLayout = cardLayout;
    	this.container = container;
    	
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(100, 450));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
        
        customizeButton(showGeneralButton,"general");
        customizeButton(showHistoryButton,"history");
        customizeButton(showScientificButton,"scientific");
        
        panelButtons = new JPanel(new GridLayout(3, 1, 10, 15));
        panelButtons.setBackground(Color.GRAY);
        
        panelButtons.add(showGeneralButton);
        panelButtons.add(showHistoryButton);
        panelButtons.add(showScientificButton);
        
        add(panelButtons,BorderLayout.NORTH);
    
        engine.setThemeListener((isDark)->{
        	if(isDark) changeToDark();   
        	else changeToLight();
        });
    }
    
	
    private void customizeButton(JButton button,String name) {
    	button.setFocusable(false);
    	button.setBackground(Color.GRAY);
    	button.setPreferredSize(new Dimension(80, 40));
    	button.setForeground(Color.WHITE);
    	button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
    	button.setFont(new Font("Arial", Font.PLAIN, 15));
    	button.addActionListener(e -> {
            cardLayout.show(container, name);
        });
    	
    }
    
    
    private void changeToLight() {
    	setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
		
		panelButtons.setBackground(Color.WHITE);
		
		showGeneralButton.setBackground(Color.WHITE);
		showGeneralButton.setForeground(Color.DARK_GRAY);
		showGeneralButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
		
		showHistoryButton.setBackground(Color.WHITE);
		showHistoryButton.setForeground(Color.DARK_GRAY);
		showHistoryButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
		
		showScientificButton.setBackground(Color.WHITE);
		showScientificButton.setForeground(Color.DARK_GRAY);
		showScientificButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
    }
	
    
    private void changeToDark() {
    	setBackground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
		
		panelButtons.setBackground(Color.GRAY);
		
		customizeButton(showGeneralButton,"general");
        customizeButton(showHistoryButton,"history");
        customizeButton(showScientificButton,"scientific");
    }
	
}

