package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.UnitIndexer;

public class UnitIndexTask extends IndexTask{
	
	private Indexer indexer;

	public UnitIndexTask(IndexContext indexContext) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(TaskType.INDEX_UNIT);
		indexer = new UnitIndexer(indexContext.getIndexerManager());
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		UnitModel unitModel = (UnitModel) indexContext.getIndexable();
	}

}
