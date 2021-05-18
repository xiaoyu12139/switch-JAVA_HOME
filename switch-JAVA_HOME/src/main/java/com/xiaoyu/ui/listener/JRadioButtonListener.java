package com.xiaoyu.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JRadioButton;

import com.xiaoyu.model.JdkModel;
import com.xiaoyu.service.JdkModelService;
import com.xiaoyu.service.impl.JdkModelServiceImpl;
import com.xiaoyu.ui.panel.MainPanel;

public class JRadioButtonListener implements ActionListener{
	
	private JdkModelService service = new JdkModelServiceImpl();

	/*path中配置%JAVA_HOME\bin%,先得到系统级别和用户级别的path,
	;分隔，判断是否有%JAVA_HOME%，无就添加JAVA_HOME在PATH最前面*/
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Runtime runtime = Runtime.getRuntime();
//			if(!findHOME()) {
//				runtime.exec("cmd /c wmic ENVIRONMENT where \"name='JAVA_HOME'\" delete");
//				runtime.exec("cmd /c setx PATH %JAVA_HOME%\\bin;%PATH% /m");
//				runtime.exec("cmd /c setx PATH %JAVA_HOME%\\bin;%PATH%");
//			}
			JRadioButton rb = (JRadioButton)e.getSource();
			runtime.exec("cmd /c setx JAVA_HOME " 
			+ service.findByVersion(rb.getText()).getPath() + " /m");
			runtime.exec("cmd /c setx JAVA_HOME " 
					+ service.findByVersion(rb.getText()).getPath());
			JdkModel path = service.findByVersion(rb.getText());
			MainPanel.text.setText("jdk:" + path.getPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private boolean findHOME() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process exec = runtime.exec("cmd /c echo %PATH%");
			BufferedReader read = new BufferedReader(new InputStreamReader(
					exec.getInputStream()));
			String target = "";
			String temp = null;
			while((temp = read.readLine()) != null) {
				target += temp;
			}
			String[] split = target.split(";");
			String res = "";
			for(String tmp : split) {
				boolean flag = true;
				File file = new File(tmp);
				if(file.exists()) {
					for(File f : file.listFiles()) {
						if(f.getName().equals("java.exe")){
							flag = false;
							break;
						}
					}
				}else if(file.getAbsoluteFile().equals("%JAVA_HOME\\bin%")) {
					return true;
				}
				if(flag) res += tmp + ";";
			}
			System.out.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
