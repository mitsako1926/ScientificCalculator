package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalculatorHistoryPanel extends JPanel{
	
	private final JLabel labelHistory = new JLabel("History");
	private final JLabel labelManageHistory = new JLabel("Manage History");
	private final JLabel labelImportExport = new JLabel("Import / Export");
	
	private final JPanel panelManageHistory = new JPanel();
	private final JPanel panelImportExport = new JPanel();
	private final JPanel panelManageHistoryButtons = new JPanel();
	private final JPanel panelImportExportButtons = new JPanel();
	
	
	private final String [] arrayButtons = {"Show History","Clear History","Load History","Save History"};
	
	CalculatorHistoryPanel(){
		setPreferredSize(new Dimension(250,450));
		setLayout(new BorderLayout());
		
		customizeLabel(labelHistory);
		labelHistory.setFont(new Font("Arial",Font.BOLD,20));
		labelHistory.setPreferredSize(new Dimension(250,70));

		customizeLabel(labelManageHistory);
		labelManageHistory.setFont(new Font("Arial",Font.BOLD,15));

		customizeLabel(labelImportExport);
		labelImportExport.setFont(new Font("Arial",Font.BOLD,15));

		
		panelManageHistory.setLayout(new BorderLayout());
		panelManageHistory.setPreferredSize(new Dimension(250,175));
		panelManageHistory.setBackground(Color.GRAY);
		panelManageHistory.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panelManageHistoryButtons.setLayout(new GridLayout(1, 2, 20, 0));
		panelManageHistoryButtons.setBackground(Color.GRAY);
		
		panelImportExport.setLayout(new BorderLayout());
		panelImportExport.setPreferredSize(new Dimension(250,190));
		panelImportExport.setBackground(Color.GRAY);
		panelImportExport.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		panelImportExportButtons.setLayout(new GridLayout(1, 2, 20, 0));
		panelImportExportButtons.setBackground(Color.GRAY);
		
		
		Map<String, Runnable> actions = new HashMap<>();

		actions.put("Show History", () -> press("Show History"));
		actions.put("Clear History", () -> press("Clear History"));
		actions.put("Load History", () -> press("Load History"));
		actions.put("Save History", () -> press("Save History"));
		
		Arrays.stream(arrayButtons).map(JButton::new).filter((b)->b.getText().contains("Show")||b.getText().contains("Clear"))
		.forEach(button -> { customizeButton(button,actions); panelManageHistoryButtons.add(button);});
		
		Arrays.stream(arrayButtons).map(JButton::new).filter((b)->b.getText().contains("Load")||b.getText().contains("Save"))
		.forEach(button -> { customizeButton(button,actions); panelImportExportButtons.add(button);});
		
		panelManageHistory.add(labelManageHistory,BorderLayout.NORTH);
		panelManageHistory.add(panelManageHistoryButtons,BorderLayout.CENTER);
		
		panelImportExport.add(labelImportExport,BorderLayout.NORTH);
		panelImportExport.add(panelImportExportButtons,BorderLayout.CENTER);
		
		add(labelHistory,BorderLayout.NORTH);
		add(panelManageHistory,BorderLayout.CENTER);
		add(panelImportExport,BorderLayout.SOUTH);
	}
	
	
	
	private void customizeLabel(JLabel label) {
		label.setBackground(Color.GRAY);
		label.setForeground(Color.WHITE);
		label.setPreferredSize(new Dimension(250,90));
		label.setOpaque(true);
		label.setHorizontalAlignment(JLabel.CENTER);
	}



	private void press(String button){
		//historyEngine.press(button);
	}
	
	private void customizeButton(JButton button,Map<String,Runnable> actions){
		button.setFocusable(false);
	    button.setBackground(Color.GRAY);
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Arial", Font.PLAIN, 13));
	    button.addActionListener(e -> actions.get(button.getText()).run());
		button.setPreferredSize(new Dimension(150,75));
	    button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));

	}
	
}
