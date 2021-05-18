package com.xiaoyu.ui.panel;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.xiaoyu.model.JdkModel;
import com.xiaoyu.service.JdkModelService;
import com.xiaoyu.service.impl.JdkModelServiceImpl;
import com.xiaoyu.ui.listener.InJDKListener;
import com.xiaoyu.ui.listener.JRadioButtonListener;
import com.xiaoyu.ui.listener.ListCellMouseListener;
import com.xiaoyu.ui.render.List2CellRender;

public class MainPanel extends JPanel{
	
	private JdkModelService service = new JdkModelServiceImpl();
	private JScrollPane sp;
	private JList<JRadioButton> list;
	private DefaultListModel<JRadioButton> model;
	private JButton inJDK;
	private ButtonGroup g;
	public static JTextField text;
	
	public MainPanel() {
		super(new BorderLayout());
		sp = new JScrollPane();
		model = new DefaultListModel<>();
		g = new ButtonGroup();
		text = new JTextField();
		text.setEditable(false);
		getCurrentJDK();
		list = new JList<JRadioButton>(model) {

			@Override
			public void updateUI() {
				super.updateUI();
				addMouseListener(new ListCellMouseListener(this));
				setCellRenderer(new List2CellRender(model, this, g));
			}
			
		};
//		list.setModel(model);
		inJDK = new JButton("新增");
		inJDK.addActionListener(new InJDKListener(this));
		inJDK.setToolTipText("选择路径前，确保系统的path中存在\"%JAVA_HOME%\\bin\"，"
				+ "\n而不是某个JDK的具体路径");
		sp.setViewportView(list);
		
		add(text, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		add(inJDK, BorderLayout.SOUTH);
		loadModel();
		confirm();
	}
	
	private void getCurrentJDK() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process exec = runtime.exec("cmd /c java -version");
			BufferedReader read = new BufferedReader(
					new InputStreamReader(exec.getErrorStream()));
			String target = "";
			String temp = null;
			while((temp = read.readLine()) != null) {
				target += temp;
			}
			Pattern regex = Pattern.compile("\"(.*)\"");
			Matcher m = regex.matcher(target);
			target = "";
			if(m.find()) {
				target = "jdk " + m.group(1);
			}
			JdkModel path = service.findByVersion(target);
			if(path == null) {
				text.setText("当前系统版本：" + target);
			}else {
				text.setText("jdk:" + path.getPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//检验列表中的jdk路径是否还存在
	private void confirm() {
		Set<JdkModel> set = service.findAll();
		for(JdkModel temp : set) {
			String path = temp.getPath() + "\\bin\\java.exe";
			if(!new File(path).exists()) {
				System.out.println(temp.getVersion() + " 路径可能不存在。");
			}
		}
	}

	public void loadModel() {
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
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.getContentPane().add(new MainPanel());
		f.setSize(200, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
}
