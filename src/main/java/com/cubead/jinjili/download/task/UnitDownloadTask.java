package com.cubead.jinjili.download.task;

import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;

public class UnitDownloadTask extends DownloadTask{

	public UnitDownloadTask(DownloadContext downloadContext) {
		super(downloadContext);
	}

	@Override
	protected void processDownload(DownloadContext downloadContext)
			throws Exception {
		TaskType taskType = downloadContext.getTaskModel().getTaskType();
		logger.info("begion to download  " + taskType.getName());	
		
		ApiContext apiContext = downloadContext.getApiContext();
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
