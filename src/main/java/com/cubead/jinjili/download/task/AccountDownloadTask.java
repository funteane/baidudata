package com.cubead.jinjili.download.task;

import org.apache.commons.beanutils.BeanUtils;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.data.vo.Account;

public class AccountDownloadTask extends DownloadTask{

	public AccountDownloadTask(DownloadContext downloadContext) {
		super(downloadContext);
		downloadContext.getTaskModel().setTaskType(TaskType.DOWNLOAD_ACCOUNT);
	}

	@Override
	protected void processDownload(DownloadContext downloadContext)	throws Exception {
		TaskType taskType = downloadContext.getTaskModel().getTaskType();
		logger.info("begion to download  " + taskType.getName());	
		
		ApiContext apiContext = downloadContext.getApiContext();
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		
		Account account = searchApiClient.getAccountManager().getAccountInfo();
		
		AccountModel accountModel = new AccountModel();
		BeanUtils.copyProperties(accountModel, account);
		accountModel.setAccountId(account.getUserId());
		
		downloadContext.setIndexable(accountModel);

		logger.info(accountModel);
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)	throws Exception {
		AccountModel accountModel = (AccountModel) downloadContext.getIndexable();
		IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.ACCOUNT, downloadContext.getAccountId());
		Indexer indexer = new AccountIndexer(indexerManager);
		indexer.index("accountId", accountModel.getAccountId(), accountModel, 1.0f, true);
	}

}
