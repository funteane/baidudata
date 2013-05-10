package com.cubead.jinjili.download.task;

import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.data.vo.Plan;

public class PlanDownloadTask extends DownloadTask{

	public PlanDownloadTask(DownloadContext downloadContext) {
		super(downloadContext);
		downloadContext.getTaskModel().setTaskType(TaskType.DOWNLOAD_PLAN);
	}

	@Override
	protected void processDownload(DownloadContext downloadContext) throws Exception {
		TaskType taskType = downloadContext.getTaskModel().getTaskType();
		logger.info("begion to download  " + taskType.getName());	
		
		ApiContext apiContext = downloadContext.getApiContext();
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		
		Plan[] plans = searchApiClient.getPlanManager().getAllPlan();
		
		
//		logger.info(accountModel);
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)	throws Exception {
		
	}

}
