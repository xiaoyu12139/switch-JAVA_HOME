package com.xiaoyu.ui.render;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;

import com.xiaoyu.ui.listener.RemoveButtonListener;

import lombok.Data;

@Data
public class List2CellRender extends JPanel implements ListCellRenderer<JRadioButton> {

	private DefaultListModel<JRadioButton> model;
	private Box box;
	private List<JButton> buttons;
	public int press = -1;
	private JList<JRadioButton> jList;
	private  ButtonGroup g;

	public List2CellRender() {
		setOpaque(true);
	}

	public List2CellRender(DefaultListModel<JRadioButton> model, JList<JRadioButton> jList, ButtonGroup g) {
		this();
		this.model = model;
		this.jList = jList;
		this.g = g;
		buttons = new ArrayList<JButton>(model.capacity());
		setLayout(new BorderLayout());
		box = Box.createHorizontalBox();
		add(box);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends JRadioButton> list, JRadioButton value, int index,
			boolean isSelected, boolean cellHasFocus) {
		box.removeAll();
		box.add(Box.createHorizontalStrut(20));
		box.add(model.elementAt(index));
		box.add(Box.createHorizontalGlue());
		flushButtons();
		if(buttons.size() <= index) return this;
		resetButtonStatus(buttons.get(index));
		if (press == index) {
			buttons.get(index).getModel().setSelected(true);
			buttons.get(index).getModel().setArmed(true);
			buttons.get(index).getModel().setPressed(true);
		}
		box.add(buttons.get(index));
		return this;
	}

	private static void resetButtonStatus(AbstractButton button) {
		ButtonModel model = button.getModel();
		model.setRollover(false);
		model.setArmed(false);
		model.setPressed(false);
		model.setSelected(false);
	}
	
	private void flushButtons() {
		for(int i = 0; i < model.size(); i++) {
			JButton button = new JButton("remove");
			button.addActionListener(new RemoveButtonListener(model.elementAt(i), jList, g));
			buttons.add(button);
		}
	}

}
