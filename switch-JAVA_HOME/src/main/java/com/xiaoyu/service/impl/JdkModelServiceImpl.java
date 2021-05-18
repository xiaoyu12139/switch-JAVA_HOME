package com.xiaoyu.service.impl;

import java.util.Set;

import com.xiaoyu.dao.JdkModelDao;
import com.xiaoyu.dao.impl.JdkModelDaoImpl;
import com.xiaoyu.model.JdkModel;
import com.xiaoyu.service.JdkModelService;

public class JdkModelServiceImpl implements JdkModelService{

	private JdkModelDao dao = JdkModelDaoImpl.getInstance();
	
	@Override
	public Set<JdkModel> findAll() {
		return dao.findAll();
	}

	@Override
	public void add(JdkModel model) {
		JdkModel res = dao.findByVersion(model.getVersion());
		if(res != null) {
			dao.update(model);
			return ;
		}
		dao.add(model);
	}

	@Override
	public JdkModel findByVersion(String version) {
		return dao.findByVersion(version);
	}

	@Override
	public void remove(String version) {
		dao.remove(version);
	}
	
	

}
