package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import engine.CalculatorEngine;
import engine.HistoryEntity;

public class CalculatorSettingsPanel extends JPanel {
	
	private final CalculatorEngine engine;
    private final DefaultListModel<HistoryEntity> model;
    private final JList<HistoryEntity> list;
	
	CalculatorSettingsPanel(CalculatorEngine engine){
		
		this.engine = engine;
		
		setPreferredSize(new Dimension(350,450));
		setLayout(new BorderLayout());
		
		model = new DefaultListModel<>();
		list = new JList<>(model);
		
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		refreshHistory();
	}
	
	
	public void refreshHistory() {
        model.clear();

        for (HistoryEntity e : engine.getHistoryList()) {
            model.addElement(e);
        }
    }
	
	
	
}
