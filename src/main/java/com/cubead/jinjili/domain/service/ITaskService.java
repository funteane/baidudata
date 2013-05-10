package com.cubead.jinjili.domain.service;

import java.util.List;

import com.cubead.jinjili.common.dao.PageBean;
import com.cubead.jinjili.domain.model.TaskModel;

public interface ITaskService {
	
	List<TaskModel> getAllTasks();
	
	PageBean<TaskModel> getAllTasks(PageBean<TaskModel> pageBean, TaskModel taskModel);

}
