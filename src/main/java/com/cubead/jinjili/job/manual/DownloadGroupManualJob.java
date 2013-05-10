package com.cubead.jinjili.job.manual;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.download.handler.DownloadHandler;
import com.cubead.jinjili.download.task.DownloadContext;
import com.cubead.jinjili.download.task.DownloadTask;
import com.cubead.jinjili.download.task.IncrementAccountDownloadTask;
import com.cubead.jinjili.download.task.RoiDownloadTask;
import com.cubead.jinjili.download.task.WholeAccountDownloadTask;
import com.cubead.jinjili.job.AbstractJob;
import com.cubead.jinjili.util.Constants;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

//@Service
public class DownloadGroupManualJob extends AbstractJob{
	
	@Autowired
	private IBaiduAccountService baiduAccountService;
	@Autowired
	private DownloadHandler downloadHandler;
	@Autowired
	private ICommonService commonService;
	
	private Date startDate;
	
	private Date endDate;
	

	@Override
	public void execute() {
		if (!enable) {
			return;
		}
		
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
		
		for (BaiduAccount baiduAccount : accounts) {
			ApiContext apiContext = new ApiContext(baiduAccount.getUserName(), baiduAccount.getPassword(), Network.BAIDU ,Method.POST, baiduAccount.getToken());
			//处理ROI
			TaskModel accountRoiTaskLog = new TaskModel();
			accountRoiTaskLog.setTaskStatus(TaskStatus.TODO);
			DownloadContext accountRoiDownloadContext = new DownloadContext(accountRoiTaskLog, apiContext);
			accountRoiDownloadContext.setStartDate(startDate);
			accountRoiDownloadContext.setEndDate(endDate);
			accountRoiDownloadContext.setAccountId(baiduAccount.getAccountId());
			DownloadTask accountRoiDownloadTask = new RoiDownloadTask(accountRoiDownloadContext, TaskType.DOWNLOAD_ACCOUNTROI);
			commonService.saveOrupdate(accountRoiTaskLog);
			downloadHandler.addTask(accountRoiDownloadTask);
			
			TaskModel planRoiTaskLog = new TaskModel();
			planRoiTaskLog.setTaskStatus(TaskStatus.TODO);
			DownloadContext planRoiDownloadContext = new DownloadContext(planRoiTaskLog, apiContext);
			planRoiDownloadContext.setStartDate(startDate);
			planRoiDownloadContext.setEndDate(endDate);
			planRoiDownloadContext.setAccountId(baiduAccount.getAccountId());
			DownloadTask planRoiDownloadTask = new RoiDownloadTask(planRoiDownloadContext, TaskType.DOWNLOAD_PLANROI);
			commonService.saveOrupdate(planRoiTaskLog);
			downloadHandler.addTask(planRoiDownloadTask);
			
			TaskModel unitRoiTaskLog = new TaskModel();
			unitRoiTaskLog.setTaskStatus(TaskStatus.TODO);
			DownloadContext unitRoiDownloadContext = new DownloadContext(unitRoiTaskLog, apiContext);
			unitRoiDownloadContext.setStartDate(startDate);
			unitRoiDownloadContext.setEndDate(endDate);
			unitRoiDownloadContext.setAccountId(baiduAccount.getAccountId());
			DownloadTask unitRoiDownloadTask = new RoiDownloadTask(unitRoiDownloadContext, TaskType.DOWNLOAD_UNITROI);
			commonService.saveOrupdate(unitRoiTaskLog);
			downloadHandler.addTask(unitRoiDownloadTask);
			
			TaskModel keywordRoiTaskLog = new TaskModel();
			keywordRoiTaskLog.setTaskStatus(TaskStatus.TODO);
			DownloadContext keywordRoiDownloadContext = new DownloadContext(keywordRoiTaskLog, apiContext);
			keywordRoiDownloadContext.setStartDate(startDate);
			keywordRoiDownloadContext.setEndDate(endDate);
			keywordRoiDownloadContext.setAccountId(baiduAccount.getAccountId());
			DownloadTask keywordRoiDownloadTask = new RoiDownloadTask(keywordRoiDownloadContext, TaskType.DOWNLOAD_KEYWORDROI);
			commonService.saveOrupdate(keywordRoiTaskLog);
			downloadHandler.addTask(keywordRoiDownloadTask);
//			
			//处理正账户下载
			TaskModel wholeAccountTaskLog = new TaskModel();
			wholeAccountTaskLog.setTaskStatus(TaskStatus.TODO);
			DownloadContext wholeAccountDownloadContext = new DownloadContext(wholeAccountTaskLog, apiContext);
			wholeAccountDownloadContext.setStartDate(startDate);
			wholeAccountDownloadContext.setEndDate(endDate);
			wholeAccountDownloadContext.setAccountId(baiduAccount.getAccountId());
			
			DownloadTask wholeAccountDownloadTask;
			if (Constants.WHOLE_ACCOUNT_FIRST_DOWNLOAD.equals(baiduAccount.getFirstDownload())) {
				wholeAccountDownloadTask = new WholeAccountDownloadTask(wholeAccountDownloadContext);
				baiduAccount.setFirstDownload("0");
				commonService.saveOrupdate(baiduAccount);
			}else {
				wholeAccountDownloadTask = new IncrementAccountDownloadTask(wholeAccountDownloadContext);
			}
			
			commonService.saveOrupdate(wholeAccountTaskLog);
			downloadHandler.addTask(wholeAccountDownloadTask);
			
		}

		downloadHandler.download();
		
		
		
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
