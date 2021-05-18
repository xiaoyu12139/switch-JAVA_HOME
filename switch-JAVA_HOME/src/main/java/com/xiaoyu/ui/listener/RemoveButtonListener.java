package com.xiaoyu.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ListModel;

import com.xiaoyu.model.JdkModel;
import com.xiaoyu.service.JdkModelService;
import com.xiaoyu.service.impl.JdkModelServiceImpl;
import com.xiaoyu.ui.panel.MainPanel;

public class RemoveButtonListener implements ActionListener{
	
	private JRadioButton rb;
	private JdkModelService service = new JdkModelServiceImpl();
	private JList<JRadioButton> list;
	private  ButtonGroup g;

	public RemoveButtonListener(JRadioButton rb, JList<JRadioButton> list, ButtonGroup g) {
		this.rb = rb;
		this.list = list;
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("remove button");
		String ver = rb.getText();
		service.remove(ver);
		MainPanel.text.setText("jdk:");
		loadModel();
	}
	
	public void loadModel() {
		DefaultListModel<JRadioButton> model = (DefaultListModel<JRadioButton>)list.getModel();
		Set<JdkModel> set = service.findAll();
		int index = 0;
		model.clear();
		for(JdkModel temp : set) {
			JRadioButton rb = new JRadioButton(temp.getVersion());
			g.add(rb);
			rb.addActionListener(new JRadioButtonListener());
			model.add(index, rb);
		}
		list.setModel(model);
		list.repaint();
	}

}
