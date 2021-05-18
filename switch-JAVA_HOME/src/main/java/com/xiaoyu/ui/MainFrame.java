package com.xiaoyu.ui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import com.xiaoyu.ui.panel.MainPanel;

public class MainFrame extends JFrame{
	
	private MainPanel mainPanel;
	
	public MainFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		mainPanel = new MainPanel();
		
		getContentPane().add(mainPanel);
		
		pack();
		setSize(200, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void run() {
		new MainFrame();
	}
}
