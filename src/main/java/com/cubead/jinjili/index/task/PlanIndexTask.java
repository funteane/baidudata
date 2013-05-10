package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.PlanIndexer;

public class PlanIndexTask extends IndexTask{
	
	private Indexer indexer;

	public PlanIndexTask(IndexContext indexContext) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(TaskType.INDEX_PLAN);
		indexer = new PlanIndexer(indexContext.getIndexerManager());
		
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		PlanModel planModel = (PlanModel) indexContext.getIndexable();
		
	}

}
