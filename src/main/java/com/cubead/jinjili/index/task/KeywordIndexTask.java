package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.KeywordIndexer;

public class KeywordIndexTask extends IndexTask{
	
	private Indexer indexer;

	public KeywordIndexTask(IndexContext indexContext) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(TaskType.INDEX_KEYWORD);
		indexer = new KeywordIndexer(indexContext.getIndexerManager());
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		KeywordModel keywordModel = (KeywordModel) indexContext.getIndexable();
		
		
		
	}

}
