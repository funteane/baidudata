package com.cubead.jinjili.download.task;

import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;

public class KeywordDownloadTask extends DownloadTask{

	public KeywordDownloadTask(DownloadContext downloadContext) {
		super(downloadContext);
	}

	@Override
	protected void processDownload(DownloadContext downloadContext)	throws Exception {
		TaskType taskType = downloadContext.getTaskModel().getTaskType();
		logger.info("begion to download  " + taskType.getName());	
		
		ApiContext apiContext = downloadContext.getApiContext();
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		
//		KeywordModel keywordModel = new KeywordModel();
//		BeanUtils.copyProperties(keywordModel, keyword);
//		
//		indexer.index("keywordId", keyword.getKeywordId(), keywordModel, 1.0f, false);
//		
		
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)	throws Exception {
		IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.KEYWORD, downloadContext.getAccountId());
		
		
	}

}
