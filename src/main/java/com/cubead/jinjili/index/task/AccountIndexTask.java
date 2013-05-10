package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;

public class AccountIndexTask extends IndexTask{
	
	private Indexer indexer;

	public AccountIndexTask(IndexContext indexContext) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(TaskType.INDEX_ACCOUNT);
		indexer = new AccountIndexer(indexContext.getIndexerManager());
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		AccountModel indexable = (AccountModel) indexContext.getIndexable();
//		indexer.index(field, value, indexable, boost, updated);
	}
	
	
	

}
