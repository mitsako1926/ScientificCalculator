package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.File;
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

import engine.CalculatorEngine;

public class CalculatorHistoryPanel extends JPanel{
	
	private final JLabel labelHistory = new JLabel("History");
	private final JLabel labelManageHistory = new JLabel("Manage History");
	private final JLabel labelImportExport = new JLabel("Import / Export");
	
	private final JPanel panelManageHistory = new JPanel();
	private final JPanel panelImportExport = new JPanel();
	private final JPanel panelManageHistoryButtons = new JPanel();
	private final JPanel panelImportExportButtons = new JPanel();
	
	private CardLayout cardLayout;
	private JPanel container;
	
	private final List<JButton> buttonsList = new ArrayList<JButton>();
	
	private final CalculatorEngine engine;
	
	private final String [] arrayButtons = {"Show History","Clear History","Load History","Save History","Save History As Text"};
	
	CalculatorHistoryPanel(CalculatorEngine engine){

		this.engine = engine;
		
		setPreferredSize(new Dimension(250,450));
		setLayout(new BorderLayout());
		
		//LABELS
		customizeLabel(labelHistory);
		labelHistory.setFont(new Font("Arial",Font.BOLD,20));
		labelHistory.setPreferredSize(new Dimension(250,70));

		customizeLabel(labelManageHistory);

		customizeLabel(labelImportExport);

		customizePanel(panelManageHistory);
		
		customizePanel(panelImportExport);
		
		//PANELS TO ONLY HOLD THE BUTTONS		
		panelManageHistoryButtons.setLayout(new GridLayout(1, 2, 20, 50));
		panelManageHistoryButtons.setBackground(Color.GRAY);
		
		panelImportExportButtons.setLayout(new FlowLayout(1,10,10));
		panelImportExportButtons.setBackground(Color.GRAY);
		
		//BUTTONS
		Map<String, Runnable> actions = new HashMap<String, Runnable>();

		actions.put("Show History", () -> press("Show History"));
		actions.put("Clear History", () -> press("Clear History"));
		actions.put("Load History", () -> press("Load History"));
		actions.put("Save History", () -> press("Save History"));
		actions.put("Save History As Text", () -> press("Save History As Text"));
		
		Arrays.stream(arrayButtons).map(JButton::new).filter((b)->b.getText().contains("Show")||b.getText().contains("Clear"))
		.forEach(button -> { customizeButton(button,actions); panelManageHistoryButtons.add(button);});
		
		Arrays.stream(arrayButtons).map(JButton::new).filter((b)->b.getText().contains("Load")||b.getText().contains("Save"))
		.forEach(button -> { customizeButton(button,actions); button.setPreferredSize(new Dimension(145,60)); panelImportExportButtons.add(button);});
		
		//ADDING EVERYTHING TO THE TWO MAIN PANELS
		panelManageHistory.add(labelManageHistory,BorderLayout.NORTH);
		panelManageHistory.add(panelManageHistoryButtons,BorderLayout.CENTER);
		
		panelImportExport.add(labelImportExport,BorderLayout.NORTH);
		panelImportExport.add(panelImportExportButtons,BorderLayout.CENTER);
		
		//ADDING THE TWO MAIN PANELS AND LABEL 
		add(labelHistory,BorderLayout.NORTH);
		add(panelManageHistory,BorderLayout.CENTER);
		add(panelImportExport,BorderLayout.SOUTH);
		
		
		engine.setThemeListener(isDark->{
			if(isDark) changeToDark();
			else changeToLight();
		});
	}
	
	
	//METHOD EXECUTED WHEN PRESSED
	private void press(String button){
	    		
		switch (button) {
	        
			case "Save History As Text": {
			    FileDialog dialog = new FileDialog((Frame) null, "Save History As Text", FileDialog.SAVE);
			    dialog.setVisible(true);
	
			    String directory = dialog.getDirectory();
			    String file = dialog.getFile();
	
			    if (file != null && directory != null) {
	
			        if (!file.endsWith(".txt")) {
			            file += ".txt";
			        }
	
			        String path = new File(directory, file).getAbsolutePath();
			        engine.saveHistoryAsText(path);
			    }
			}
				break;
				
			case "Show History":
	            cardLayout.show(container, "show history");
	            break;
	            
	        case "Clear History":
	            engine.clearHistory();
	            break;
	            
	        case "Load History":{
	        	FileDialog dialog = new FileDialog((Frame) null, "Load History", FileDialog.LOAD);
	            dialog.setVisible(true);

	            String directory = dialog.getDirectory();
	            String file = dialog.getFile();

	            if (file != null) {
	                String path = directory + file;
	                engine.loadHistory(path);
	            }
	            
	        }
	        	break;
	        
	        case "Save History":{
	        	FileDialog dialog = new FileDialog((Frame) null, "Save History", FileDialog.SAVE);
	            dialog.setVisible(true);

	            String directory = dialog.getDirectory();
	            String file = dialog.getFile();

	            if (file != null) {
	            	if (!file.endsWith(".dat")) {
	                    file += ".dat";
	                }
	            	
	            	String path = directory + file;
	                engine.saveHistory(path);
	            }
	            
	        }
	            break;
	            
	    }
		
		
	}
	
	
	
	public void setCardLayout(CardLayout cardLayout, JPanel container) {
	    this.cardLayout = cardLayout;
	    this.container = container;
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setBackground(Color.GRAY);
		label.setForeground(Color.WHITE);
		label.setPreferredSize(new Dimension(250,40));
		label.setFont(new Font("Arial",Font.BOLD,17));
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);
	}


	
	private void customizePanel(JPanel panel) {
		panel.setLayout(new BorderLayout(10,10));
		panel.setPreferredSize(new Dimension(250,230));
		panel.setBackground(Color.GRAY);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	
	
	private void customizeButton(JButton button,Map<String,Runnable> actions){
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Arial", Font.PLAIN, 15));
	    button.addActionListener(e -> actions.get(button.getText()).run());
		button.setPreferredSize(new Dimension(150,75));
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    
	    buttonsList.add(button);
	}
	
	
	
	private void changeToDark() {
		
		styleComponentDark(labelHistory);
		styleComponentDark(labelManageHistory);
		styleComponentDark(labelImportExport);
		styleComponentDark(panelImportExport);
		styleComponentDark(panelManageHistory);
		styleComponentDark(panelManageHistoryButtons);
		styleComponentDark(panelImportExportButtons);
		
		buttonsList.forEach((button)->{
			button.setForeground(Color.WHITE);
			button.setBackground(Color.GRAY);
			button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
		});
	}
	
	
	
	private void changeToLight() {

		styleComponentLight(labelHistory);
		styleComponentLight(labelManageHistory);
		styleComponentLight(labelImportExport);
		styleComponentLight(panelImportExport);
		styleComponentLight(panelManageHistory);
		styleComponentLight(panelManageHistoryButtons);
		styleComponentLight(panelImportExportButtons);
		
		buttonsList.forEach((button)->{
			button.setForeground(Color.DARK_GRAY);
			button.setBackground(Color.WHITE);
			button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
		});
		
	}
	
	
	
	private void styleComponentLight(JComponent comp) {
	    comp.setBackground(Color.WHITE);
	    comp.setForeground(Color.DARK_GRAY);
	}
	
	
	
	private void styleComponentDark(JComponent comp) {
	    comp.setBackground(Color.GRAY);
	    comp.setForeground(Color.WHITE);
	}
	
	
	
}


