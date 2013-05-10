package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.QualityModel;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.QualityIndexer;

public class QualityIndexTask extends IndexTask{
	
	private Indexer indexer;

	public QualityIndexTask(IndexContext indexContext) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(TaskType.INDEX_QUALITY);
		indexer = new QualityIndexer(indexContext.getIndexerManager());
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		QualityModel qualityModel = (QualityModel) indexContext.getIndexable();
	}

}
