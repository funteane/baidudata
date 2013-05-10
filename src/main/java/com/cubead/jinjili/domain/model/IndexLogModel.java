package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Date;

public class IndexLogModel implements Serializable{

	private static final long serialVersionUID = 4888105464522972990L;
	
	private TaskStatus taskStatus;
	
	private TaskType taskType;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date indexDate;
	
	private String accountId;		//所在账户
	
	private String reason;  			//记录失败原因等

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
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

	public Date getIndexDate() {
		return indexDate;
	}

	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
	

}
