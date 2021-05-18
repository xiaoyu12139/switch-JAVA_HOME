package com.xiaoyu.dao;

import java.util.Set;

import com.xiaoyu.model.JdkModel;

public interface JdkModelDao {
	void add(JdkModel model);
	void update(JdkModel model);
	void remove(String version);
	JdkModel findByVersion(String version);
	Set<JdkModel> findAll();
}
