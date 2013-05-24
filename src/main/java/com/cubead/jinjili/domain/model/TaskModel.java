package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOWNLOAD_TASK")
public class TaskModel implements Serializable{

	private static final long serialVersionUID = -6464712923459288986L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "TASK_STATUS")
	private TaskStatus taskStatus;
	
	@Column(name = "TASK_TYPE")
	private TaskType taskType;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "FILEID_DATE")
	private Date fileIdDate;
	
	@Column(name = "INDEX_DATE")
	private Date indexDate;
	
	@Column(name = "FILE_ID")
	private String fileId;				//下载文件ID
	
	@Column(name = "FINISH_DATE")
	private Date finishDate;
	
	@Column(name = "ACCOUNT_ID")
	private String accountId;		//所在账户
	
	@Column(name = "REASON")
	private String reason;  			//记录失败原因等
	
	@Column(name = "DATA_START_DATE")
	private Date dataStartDate;
	
	@Column(name = "DATA_END_DATE")
	private Date dataEndDate;
	
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

	public Date getFileIdDate() {
		return fileIdDate;
	}

	public void setFileIdDate(Date fileIdDate) {
		this.fileIdDate = fileIdDate;
	}
	
	
	
	public Date getIndexDate() {
		return indexDate;
	}

	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	
	

	public Date getDataStartDate() {
		return dataStartDate;
	}

	public void setDataStartDate(Date dataStartDate) {
		this.dataStartDate = dataStartDate;
	}
	
	

	public Date getDataEndDate() {
		return dataEndDate;
	}

	public void setDataEndDate(Date dataEndDate) {
		this.dataEndDate = dataEndDate;
	}

	@Override
	public String toString() {
		return "TaskModel [id=" + id + ", taskStatus=" + taskStatus
				+ ", taskType=" + taskType + ", createDate=" + createDate
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", fileIdDate=" + fileIdDate + ", indexDate=" + indexDate
				+ ", fileId=" + fileId + ", finishDate=" + finishDate
				+ ", accountId=" + accountId + ", reason=" + reason + "]";
	}
	
	
	
	
	

}
