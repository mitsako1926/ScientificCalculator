package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import engine.CalculatorEngine;
import engine.HistoryEntity;

public final class CalculatorShowHistoryPanel extends JPanel{

	private CardLayout cardLayout;
	private JPanel container;
	private final CalculatorEngine engine;
    private final DefaultListModel<HistoryEntity> model;
    private final JList<HistoryEntity> list;
    JButton backButton = new JButton("Back");
    
	CalculatorShowHistoryPanel(CalculatorEngine engine){
		
		this.engine = engine;
		
		setPreferredSize(new Dimension(350,450));
		setLayout(new BorderLayout());
		
		model = new DefaultListModel<>();
		
		list = new JList<>(model);
		list.setFont(new Font("Arial", Font.PLAIN, 18));
		list.setBackground(Color.GRAY);
		list.setForeground(Color.WHITE);
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
		
		backButton.setPreferredSize(new Dimension(350,35));
		backButton.setFocusable(false);
		backButton.setBackground(Color.GRAY);
	    backButton.setForeground(Color.WHITE);
	    backButton.setFont(new Font("Arial",Font.BOLD, 14));
	    backButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
	    backButton.addActionListener(e -> {
	    	cardLayout.show(container, "history");
	    });
		
	    
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(backButton,BorderLayout.NORTH);
		
		refreshHistory();
		
		engine.setThemeListener(isDark->{
			if(isDark) {
				list.setBackground(Color.GRAY);
				list.setForeground(Color.WHITE);
				
				backButton.setBackground(Color.GRAY);
			    backButton.setForeground(Color.WHITE);
			    backButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
			}else {
				list.setBackground(Color.WHITE);
				list.setForeground(Color.DARK_GRAY);
				
				backButton.setBackground(Color.WHITE);
			    backButton.setForeground(Color.DARK_GRAY);
			    backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
			}
		});
		
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



