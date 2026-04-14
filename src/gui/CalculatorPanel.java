package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.CalculatorEngine;

public final class CalculatorPanel extends JPanel{
	
	private final JTextField textDisplay;
	
	private final JLabel historyLabelUp,historyLabelDown;
	private final JPanel panelDisplay,panelButtons;
	
	private List<JButton> buttonList = new ArrayList<>();
		
	private final float baseFontSize,baseFontSizeHistoryUp,baseFontSizeHistoryDown;
	
	private final String[] buttons = {
	    "%","≡","C","del",
		"1/x","x²","√","÷",
	    "7","8","9","×",
	    "4","5","6","-",
	    "1","2","3","+",
	    "±","0",".","=",
	};
		
	private final CalculatorEngine engine = new CalculatorEngine();

	
	CalculatorPanel(){
		
		setPreferredSize(new Dimension(400,700));
		setLayout(new BorderLayout());
	
		
		//MAIN DISPLAY OF THE CALCULATIONS AND THE HISTORY
		panelDisplay = new JPanel();
		panelDisplay.setPreferredSize(new Dimension(400,250));
		panelDisplay.setLayout(new BorderLayout());
		
		
		//MAIN DISPLAY OF BUTTONS
		panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(400,450));
		panelButtons.setLayout(new GridLayout(6,4,4,4));
		panelButtons.setBackground(Color.BLACK);
		
		
		//COMPONENTS OF THE DISPLAY OF CALCULATIONS
		textDisplay = new JTextField();
		customizeTextField(textDisplay);
		
		baseFontSize = textDisplay.getFont().getSize2D();
		
		historyLabelUp = new JLabel();
		customizeLabel(historyLabelUp);
		
		baseFontSizeHistoryUp = historyLabelUp.getFont().getSize2D();
		
		historyLabelDown = new JLabel();
		customizeLabel(historyLabelDown);
		
		baseFontSizeHistoryDown = historyLabelDown.getFont().getSize2D();
		
		
		//WE ADD THE COMPONENTS TO THE DISPLAY OF CALCULATIONS
		panelDisplay.add(historyLabelUp,BorderLayout.NORTH);
		panelDisplay.add(historyLabelDown,BorderLayout.CENTER);
		panelDisplay.add(textDisplay,BorderLayout.SOUTH);
		
		
		//FOR REFREASHING THE WHOLE PANEL
		engine.setDisplayRefreshListener(this::refreshView);
		
		
		//COMPONENTS OF THE DISPLAY OF BUTTONS
		
		Map<String, Runnable> actions = new HashMap<>();

		actions.put("C", () -> press("C"));
		actions.put("≡", () -> press("≡"));
		actions.put("+", () -> press("+"));
		actions.put("-", () -> press("-"));
		actions.put("×", () -> press("×"));
		actions.put("÷", () -> press("÷"));
		actions.put("%", () -> press("%"));
		actions.put("=", () -> press("="));
		actions.put("x²", () -> press("x²"));
		actions.put("1/x", () -> press("1/"));
		actions.put("√", () -> press("√"));
		actions.put("±", () -> press("±"));
		actions.put(".", () -> press("."));
		actions.put("del", () -> press("del"));
		

		for(int i=0; i<=9; i++) {
		    String num = String.valueOf(i);
		    actions.put(num, () -> press(num));
		}
		
		
		Arrays.stream(buttons)
			  .map(JButton::new)
			  .forEach(button -> { customizeButton(button,actions); panelButtons.add(button);});
		
		
		//ADDING THE 2 MAIN DISPLAY PANELS TO THE MAIN PANEL OF THE FRAME 
		add(panelDisplay,BorderLayout.NORTH);
		add(panelButtons,BorderLayout.SOUTH);
		
		
		//FOR THE FONT CHANGE
		engine.setFontListener(offset -> {
			    float newSize = baseFontSize + offset;
			    textDisplay.setFont(textDisplay.getFont().deriveFont(newSize));
			    
			    float newSizeHistoryDown = baseFontSizeHistoryDown + offset;
			    if(newSizeHistoryDown<=3)newSizeHistoryDown=6;
			    historyLabelDown.setFont(historyLabelDown.getFont().deriveFont(newSizeHistoryDown));

			    float newSizeHistoryUp = baseFontSizeHistoryUp + offset;
			    if(newSizeHistoryUp<=3)newSizeHistoryUp=6;
			    historyLabelUp.setFont(historyLabelUp.getFont().deriveFont(newSizeHistoryUp));
			    
		});
		
		
		//FOR THE THEME CHANGE
		engine.setThemeListener(isDark -> {
			
			if(isDark) {
				
				styleComponentDark(historyLabelDown);
				styleComponentDark(historyLabelUp);
				styleComponentDark(textDisplay);
				panelButtons.setBackground(Color.black);
				
				buttonList.forEach((button)->{
					button.setBackground(Color.GRAY);
				    button.setForeground(Color.WHITE);
				    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
				    
				});
				

			}else {
				
				styleComponentLight(historyLabelDown);
				styleComponentLight(historyLabelUp);
				styleComponentLight(textDisplay);
				styleComponentLight(panelButtons);
								
				buttonList.forEach((button)->{
					button.setBackground(Color.WHITE);
				    button.setForeground(Color.DARK_GRAY);
				    button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
				});
								
			}
			
		});
		
		
	}
	
	
	
	private void styleComponentDark(JComponent comp) {
	    comp.setBackground(Color.GRAY);
	    comp.setForeground(Color.WHITE);
	}
	
	
	
	private void styleComponentLight(JComponent comp) {
	    comp.setBackground(Color.WHITE);
	    comp.setForeground(Color.DARK_GRAY);
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setPreferredSize(new Dimension(400,50));
		label.setBackground(Color.GRAY);
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setFont(new Font("Dialog", Font.BOLD, 13));
	}
	
	
	
	private void customizeTextField(JTextField textField) {
		textField.setPreferredSize(new Dimension(400,150));
		textField.setFont(new Font("Dialog", Font.BOLD, 35));
		textField.setText("0");
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setBackground(Color.GRAY);
		textField.setForeground(Color.WHITE);
		textField.setBorder(null);
		textField.setEditable(false);//KEY BINDS WILL BE AVAILABLE IN A FUTURE VERSION
		textField.setFocusable(false);
	}
	
	
	
	private void customizeButton(JButton button,Map<String,Runnable> actions) {
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    button.setFont(new Font("Arial", Font.PLAIN, 20));
	    button.addActionListener(e -> actions.get(button.getText()).run());
	    
	    buttonList.add(button);
	}	
	
	
	
	private void press(String buttonPressed){
	    
	    engine.press(buttonPressed);

	    textDisplay.setText(engine.getDisplay());
	    historyLabelDown.setText(engine.getHistoryDown());
	    historyLabelUp.setText(engine.getHistoryUp());
	}
	
	
	
	private void refreshView() {
	    textDisplay.setText(engine.getDisplay());
	    historyLabelDown.setText(engine.getHistoryDown());
	    historyLabelUp.setText(engine.getHistoryUp());
	}
	

	

}




