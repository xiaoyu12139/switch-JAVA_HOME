package com.xiaoyu.service;

import java.util.Set;

import com.xiaoyu.model.JdkModel;

public interface JdkModelService {
	Set<JdkModel> findAll();
	void add(JdkModel model);
	JdkModel findByVersion(String version);
	void remove(String version);
}
