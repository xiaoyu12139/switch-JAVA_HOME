package com.xiaoyu.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo01 {
	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
//		runtime.exec("cmd /c wmic ENVIRONMENT where \"name='JAVA_HOME'\" delete");
		Process exec = runtime.exec("cmd /c setx PATH \"%JAVA_HOME%\\bin;%PATH%\" /m");
		BufferedReader r = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		String temp = null;
		while((temp = r.readLine()) != null) {
			System.out.println(temp);
		}
//		runtime.exec("cmd /c set PATH \"%JAVA_HOME%\\bin;%PATH%\" /m");
	}
}
