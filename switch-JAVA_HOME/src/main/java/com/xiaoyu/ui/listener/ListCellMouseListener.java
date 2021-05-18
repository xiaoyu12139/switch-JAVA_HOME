package com.xiaoyu.ui.listener;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.xiaoyu.ui.render.List2CellRender;

public class ListCellMouseListener extends MouseInputAdapter {

	private JList<JRadioButton> list;

	public ListCellMouseListener() {
	}

	public ListCellMouseListener(JList<JRadioButton> list) {
		this.list = list;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		Point p = e.getPoint();
		int index = list.locationToIndex(p);
		if (index >= 0) {
			Component c = getButon(index, p);
			if (c instanceof JRadioButton) {
				JRadioButton rb = (JRadioButton) c;
				rb.doClick();
				list.repaint();
			}else if(c instanceof JButton) {
				JButton b = (JButton) c;
				System.out.println(b);
				List2CellRender r = (List2CellRender)list.getCellRenderer();
				r.press = index;
//				rectRepaint(list, list.getCellBounds(index, index));
//				b.doClick();
				list.repaint();
			}
		}
	}
	
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		Point p = e.getPoint();
		int index = list.locationToIndex(p);
		if (index >= 0) {
			Component c = getButon(index, p);
			if(c instanceof JButton) {
				JButton b = (JButton) c;
				List2CellRender r = (List2CellRender)list.getCellRenderer();
				r.press = -1;
				b.doClick();
				list.repaint();
			}
		}
	}

	private static void rectRepaint(JComponent c, Rectangle rect) {
		Optional.ofNullable(rect).ifPresent(c::repaint);
	}

	public Component getButon(int index, Point p) {
		Component c = list.getCellRenderer().getListCellRendererComponent(list, null, index, false, false);
		Rectangle r = list.getCellBounds(index, index);
		c.setBounds(r);
		p.translate(-r.x, -r.y);
		return SwingUtilities.getDeepestComponentAt(c, p.x, p.y);
	}

}
