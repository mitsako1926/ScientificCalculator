package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import engine.CalculatorEngine;


public final class CalculatorGeneralPanel extends JPanel{
	
	private final JLabel labelGeneral = new JLabel("General");
	private final JLabel labelFont = new JLabel("Font Size");
	private final JLabel labelDecimal = new JLabel("Decimal Precision");
	private final JLabel labelTheme = new JLabel("Theme");
	
	private final JButton resetButton = new JButton("Reset Settings");
	private final JButton applyButton = new JButton("Apply Changes");

	private final JPanel panelButtons = new JPanel();
	private final JPanel panelSettings = new JPanel();
	private final JPanel panelFont = new JPanel();
	private final JPanel panelDecimal = new JPanel();
	private final JPanel panelTheme = new JPanel();
	
	private final JSlider fontSlider = new JSlider(0, 10, 5);
	private final JComboBox<String> decimalComboBox = new JComboBox<>(
	                            new String[]{"2", "4", "6", "8", "10"} );
	
	private final JRadioButton darkRadioButton = new JRadioButton("Dark");
	private final JRadioButton lightRadioButton = new JRadioButton("Light");
	private final ButtonGroup themeGroup = new ButtonGroup();
	
		
	private final CalculatorEngine engine;
	
	CalculatorGeneralPanel(CalculatorEngine engine){
			
		this.engine = engine;
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(350,450));

		//ALL LABELS
		customizeLabel(labelGeneral);
		labelGeneral.setFont(new Font("Arial",Font.BOLD,20));
		labelGeneral.setPreferredSize(new Dimension(350,70));
		
		customizeLabel(labelFont);
		
		customizeLabel(labelDecimal);
		
		customizeLabel(labelTheme);
		
		//BUTTONS FOR BOTTOM PANEL
		customizeButton(applyButton);
		customizeButton(resetButton);
		
		
		//BOTTOM PANEL
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER,60,15));
		panelButtons.setBackground(Color.GRAY);
		panelButtons.setPreferredSize(new Dimension(350,80));
		
		panelButtons.add(applyButton);
		panelButtons.add(resetButton);
		
		//CENTER PANEL 
		panelSettings.setLayout(new GridLayout(3, 1));
		panelSettings.setBackground(Color.GRAY);
		panelSettings.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
		
		//3 PANELS IN THE CENTER PANEL 
		
		//SET FONT PANEL
		customizePanel(panelFont);
		panelFont.add(labelFont,BorderLayout.NORTH);
		
		customizeSlider(fontSlider);
		panelFont.add(fontSlider, BorderLayout.CENTER);
		
		//SET DECIMAL PRECISION PANEL
		customizePanel(panelDecimal);		
		customizeComboBox(decimalComboBox);

		JPanel comboWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
		comboWrapper.setBackground(Color.GRAY);
		comboWrapper.add(decimalComboBox);
		
		panelDecimal.add(labelDecimal,BorderLayout.NORTH);
		panelDecimal.add(comboWrapper, BorderLayout.CENTER);
		
		//SET THEME PANEL
		customizePanel(panelTheme);
		panelTheme.setLayout(new GridLayout(3, 1, 5, 5));
		panelTheme.add(labelTheme);
		
		themeGroup.add(darkRadioButton);
		themeGroup.add(lightRadioButton);


		customizeRadioButton(darkRadioButton);
		darkRadioButton.setSelected(true);

		customizeRadioButton(lightRadioButton);

		panelTheme.add(darkRadioButton);
		panelTheme.add(lightRadioButton);
		

		//ADD ALL THE PANELS TO THE CENTER PANEL
		panelSettings.add(panelFont);
		panelSettings.add(panelDecimal);
		panelSettings.add(panelTheme);
		
		
		add(labelGeneral,BorderLayout.NORTH);
		add(panelSettings,BorderLayout.CENTER);
		add(panelButtons,BorderLayout.SOUTH);
	}
	
	
	
	void loadSettings() {
		fontSlider.setValue(engine.getFontVar());
	    decimalComboBox.setSelectedItem(String.valueOf(engine.getDecimalVar()));

	    if (engine.getDark())darkRadioButton.setSelected(true);
	    else lightRadioButton.setSelected(true);
	    
	}

	

	
	
	private void customizeLabel(JLabel label) {
		label.setBackground(Color.GRAY);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial",Font.BOLD,17));
		label.setPreferredSize(new Dimension(350,30));
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);
	}
	
	
	private void customizeRadioButton(JRadioButton radioButton) {
		radioButton.setHorizontalAlignment(JLabel.CENTER);
		radioButton.setBackground(Color.GRAY);
		radioButton.setFocusable(false);
		radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
		radioButton.setForeground(Color.WHITE);
	}
	
	
	private void customizeSlider(JSlider slider) {
	    slider.setBackground(Color.GRAY);
	    slider.setForeground(Color.WHITE);
	    slider.setFocusable(false);
	    slider.setMajorTickSpacing(2);
	    slider.setMinorTickSpacing(1);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    
	}

	
	private void customizeComboBox(JComboBox<String> comboBox) {
	    comboBox.setFocusable(false);
	    comboBox.setBackground(Color.WHITE);
	    comboBox.setForeground(Color.BLACK);
	    comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
	    comboBox.setPreferredSize(new Dimension(100,30));
	    comboBox.setSelectedItem("6");
	}
	
	
	private void customizePanel(JPanel panel) {
		panel.setLayout(new BorderLayout(5,5));
		panel.setBackground(Color.GRAY);
	}
	
	
	private void customizeButton(JButton button){
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setPreferredSize(new Dimension(115,50));
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    button.addActionListener(new myActionListener());
	}
	

//PROBLEMS: 
//IMPROVEMENTS: FIND BUGS



	class myActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object pressed = e.getSource();
			if(pressed==resetButton) {
				decimalComboBox.setSelectedItem("6");
				engine.setDecimalVar(6);
				
				fontSlider.setValue(5);
				engine.setFontSize(5);
				
				darkRadioButton.setSelected(true);
				engine.setDark(true);
				engine.setTheme();
				
			}else if(pressed==applyButton) {
				
				if(darkRadioButton.isSelected()) engine.setDark(true);
				else if(lightRadioButton.isSelected())engine.setDark(false);
				
				engine.setTheme();
				
				int value = fontSlider.getValue();
				engine.setFontSize(value);

				int decimal = Integer.parseInt((String)decimalComboBox.getSelectedItem());
				engine.setDecimalVar(decimal);
			}
		}
		
	}
	
	
	
}

