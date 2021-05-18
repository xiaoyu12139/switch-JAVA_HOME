package com.xiaoyu.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.xiaoyu.dao.JdkModelDao;
import com.xiaoyu.model.JdkModel;
import com.xiaoyu.util.StrUtil;

public class JdkModelDaoImpl implements JdkModelDao{

	private Set<JdkModel> set;
	private static JdkModelDaoImpl me = new JdkModelDaoImpl();
	
	{
		set = new TreeSet<JdkModel>(new Comparator<JdkModel>() {

			@Override
			public int compare(JdkModel o1, JdkModel o2) {
				return o1.getVersion().compareTo(o2.getVersion());
			}
		});
		
		reload();
	}
	
	public static JdkModelDaoImpl getInstance() {
		return me;
	}
	
	public void reload() {
		try {
			String temp = null;
			BufferedReader read = getReader();
			set.clear();
			while((temp = read.readLine()) != null) {
				JdkModel json = JSON.parseObject(temp, JdkModel.class);
				set.add(json);
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void store() {
		try {
			Writer w = getWriter(false);
			for(JdkModel temp : set) {
				String json = JSON.toJSONString(temp);
				w.write(json + "\n");
			}
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Writer getWriter(boolean add) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(StrUtil.JDK_INDEX_DIR), add)));
	}
	
	public BufferedReader getReader() throws FileNotFoundException {
		return new BufferedReader(new FileReader(new File(StrUtil.JDK_INDEX_DIR)));
	}
	
	@Override
	public void add(JdkModel model) {
		try {
			Writer w = getWriter(true);
			String json = JSON.toJSONString(model);
			w.write(json + "\n");
			w.close();
			reload();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(JdkModel model) {
		for(Iterator<JdkModel> i = set.iterator(); i.hasNext();) {
			JdkModel next = i.next();
			if(next.getVersion().equals(model.getVersion()))
				i.remove();
		}
		set.add(model);
		store();
	}

	@Override
	public void remove(String version) {
		for(Iterator<JdkModel> i = set.iterator(); i.hasNext();) {
			JdkModel next = i.next();
			if(next.getVersion().equals(version))
				i.remove();
		}
		store();
	}

	@Override
	public JdkModel findByVersion(String version) {
		try {
			BufferedReader r = getReader();
			String json = null;
			while((json = r.readLine()) != null) {
				JdkModel jsonObject = JSON.parseObject(json, JdkModel.class);
				if(version.equals(jsonObject.getVersion()))
					return jsonObject;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<JdkModel> findAll() {
		return this.set;
	}

}
