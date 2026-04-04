package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import engine.CalculatorEngine;
import engine.HistoryEntity;

public final class CalculatorHistoryPanel extends JPanel{

	private CardLayout cardLayout;
	private JPanel container;
	private final CalculatorEngine engine;
    private final DefaultListModel<HistoryEntity> model;
    private final JList<HistoryEntity> list;
	
	CalculatorHistoryPanel(CalculatorEngine engine){
		
		this.engine = engine;
		
		setPreferredSize(new Dimension(350,450));
		setLayout(new BorderLayout());
		
		model = new DefaultListModel<>();
		list = new JList<>(model);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            HistoryEntity selected = list.getSelectedValue();
		            if (selected != null) {
		                engine.loadFromHistory(selected);
		            }
		        }
		    }
		});
		
		JButton backButton = new JButton("Back");
	    backButton.addActionListener(e -> {
	    	cardLayout.show(container, "settings");
	    });
		
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(backButton,BorderLayout.NORTH);
		refreshHistory();
	}
	
	
	public void refreshHistory() {
        model.clear();
        List<HistoryEntity> historyEntries = engine.getHistoryList();
        
        for (int i = historyEntries.size() - 1; i >= 0; i--) {
            model.addElement(historyEntries.get(i));
        }
    }
	
	
	public void setCardLayout(CardLayout cardLayout, JPanel container) {
	    this.cardLayout = cardLayout;
	    this.container = container;
	}
	
	
}

