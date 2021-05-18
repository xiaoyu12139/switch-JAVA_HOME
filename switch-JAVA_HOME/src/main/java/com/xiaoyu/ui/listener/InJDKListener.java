package com.xiaoyu.ui.listener;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import com.xiaoyu.model.JdkModel;
import com.xiaoyu.service.JdkModelService;
import com.xiaoyu.service.impl.JdkModelServiceImpl;
import com.xiaoyu.ui.panel.MainPanel;

public class InJDKListener implements ActionListener{
	
	private MainPanel p;
	private JdkModelService service = new JdkModelServiceImpl();
	public static int process = -1;//0开始未结束，-1无状态，1开始已经结束
	
	public InJDKListener(MainPanel p) {
		this.p = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int res = chooser.showOpenDialog(p);
			if(res != JFileChooser.APPROVE_OPTION) return ;
			new Thread(() -> {
				while(true) {
					if(InJDKListener.process == 0) {
						p.text.setText("正在扫描jdk");
					}
					if(InJDKListener.process == 1) {
						p.text.setText("扫描结束，点击进行jdk切换");
						break;
					}
				}
			}).start();
			File je = scanJDK(chooser.getSelectedFile());
			process = 1;
			if(je == null)
				return ;
			Runtime runtime = Runtime.getRuntime();
			Process exec = runtime.exec("cmd /c " + je.getAbsolutePath() + " -version");
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
			service.add(new JdkModel(target, je.getParentFile().getParentFile().getAbsolutePath()));
			p.loadModel();
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public File scanJDK(File root) {
		process = 0;
		Queue<File> queue = new LinkedList<>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			File poll = queue.poll();
			for(File temp : poll.listFiles()) {
				if(temp.isDirectory()) {
					queue.offer(temp);
					continue;
				}
				if(temp.getName().equals("java.exe")) 
					return temp;
			}
		}
		return null;
	}

}
