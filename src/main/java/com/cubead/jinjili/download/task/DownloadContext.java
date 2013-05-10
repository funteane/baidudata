package com.cubead.jinjili.download.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.searchapi.client.data.ApiContext;

public class DownloadContext {

	private TaskModel taskModel;

	private ApiContext apiContext;
	
	private Date startDate;
	
	private Date endDate;
	
	private String accountId = "123456";
	
	private Map<String, String> filePathMap ; //下载文件路径
	
	private Indexable indexable;  //非文件下载，单个从百度查询返回

	public DownloadContext(TaskModel taskModel, ApiContext apiContext) {
		this.taskModel = taskModel;
		this.apiContext = apiContext;
		this.filePathMap = new HashMap<String, String>();
	}

	public TaskModel getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
	}

	public ApiContext getApiContext() {
		return apiContext;
	}

	public void setApiContext(ApiContext apiContext) {
		this.apiContext = apiContext;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Map<String, String> getFilePathMap() {
		return filePathMap;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Indexable getIndexable() {
		return indexable;
	}

	public void setIndexable(Indexable indexable) {
		this.indexable = indexable;
	}
	
	

	
	

}
