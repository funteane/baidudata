package com.cubead.jinjili.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.dao.ICommonDAO;
import com.cubead.jinjili.common.dao.PageBean;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.util.Tools;

@Service("taskService")
public class TaskServiceImpl implements ITaskService{
	
	@Autowired
	private ICommonDAO commonDAO;

	@Override
	public List<TaskModel> getAllTasks() {
		String hql = "FROM TaskModel ORDER BY id DESC";
		return commonDAO.find(hql);
	}
	
	@Override
	public PageBean<TaskModel> getAllTasks(PageBean<TaskModel> pageBean, TaskModel taskModel) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> paramValues = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer("FROM TaskModel  WHERE 1 = 1");
		hql.append("ORDER BY id DESC");
		
		String[] names = new String[paramNames.size()];
		Object[] values = new Object[paramValues.size()];
		Tools.list2Array(paramNames, names);
		Tools.list2Array(paramValues, values);
		
		commonDAO.find(hql.toString(), pageBean, names, values);
		
		return pageBean;
	}

}
