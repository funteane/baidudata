package com.cubead.jinjili.job.manual;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.download.handler.DownloadHandler;
import com.cubead.jinjili.download.task.DownloadContext;
import com.cubead.jinjili.download.task.DownloadTask;
import com.cubead.jinjili.download.task.IncrementAccountDownloadTask;
import com.cubead.jinjili.download.task.RoiDownloadTask;
import com.cubead.jinjili.download.task.WholeAccountDownloadTask;
import com.cubead.jinjili.job.AbstractJob;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

@Service
public class DownloadSingleManualJob extends AbstractJob{
	

	@Autowired
	private DownloadHandler downloadHandler;
	@Autowired
	private ICommonService commonService;
	
	private BaiduAccount baiduAccount;
	
	private TaskModel taskModel;
	
	private Date startDate;
	
	private Date endDate;
	

	@Override
	public void execute() {
		taskModel.setTaskStatus(TaskStatus.TODO);
		ApiContext apiContext = new ApiContext(baiduAccount.getUserName(), baiduAccount.getPassword(), Network.BAIDU ,Method.POST, baiduAccount.getToken());
		DownloadContext downloadContext = new DownloadContext(taskModel, apiContext);
		downloadContext.setApiContext(apiContext);
		downloadContext.setTaskModel(taskModel);
		downloadContext.setStartDate(startDate);
		downloadContext.setEndDate(endDate);
		
		if (taskModel.getTaskType() == TaskType.DOWNLOAD_INCREMENTACCOUNT) {
			DownloadTask downloadTask = new IncrementAccountDownloadTask(downloadContext);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);
		}else if (taskModel.getTaskType() == TaskType.DOWNLOAD_WHOLEACCOUNT) {
			DownloadTask downloadTask = new WholeAccountDownloadTask(downloadContext);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);			
		}else if (taskModel.getTaskType() == TaskType.DOWNLOAD_ACCOUNTROI) {
			DownloadTask downloadTask = new RoiDownloadTask(downloadContext, TaskType.DOWNLOAD_ACCOUNTROI);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);
		}else if (taskModel.getTaskType() == TaskType.DOWNLOAD_PLANROI) {
			DownloadTask downloadTask = new RoiDownloadTask(downloadContext, TaskType.DOWNLOAD_PLANROI);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);
		}else if (taskModel.getTaskType() == TaskType.DOWNLOAD_UNITROI) {
			DownloadTask downloadTask = new RoiDownloadTask(downloadContext, TaskType.DOWNLOAD_UNITROI);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);
		}else if (taskModel.getTaskType() == TaskType.DOWNLOAD_KEYWORDROI) {
			DownloadTask downloadTask = new RoiDownloadTask(downloadContext, TaskType.DOWNLOAD_KEYWORDROI);
			commonService.saveOrupdate(taskModel);
			downloadHandler.addTask(downloadTask);
		}
		
		downloadHandler.download();
		
	}


	public BaiduAccount getBaiduAccount() {
		return baiduAccount;
	}


	public void setBaiduAccount(BaiduAccount baiduAccount) {
		this.baiduAccount = baiduAccount;
	}


	public TaskModel getTaskModel() {
		return taskModel;
	}


	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
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
	
	
	

}
