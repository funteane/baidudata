package com.cubead.jinjili.index.task;

import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.RoiIndexer;

public class RoiIndexTask extends IndexTask{
	
	private Indexer indexer;

	public RoiIndexTask(IndexContext indexContext, TaskType taskType) {
		super(indexContext);
		indexContext.getIndexLogModel().setTaskType(taskType);
		indexer = new RoiIndexer(indexContext.getIndexerManager());
	}

	@Override
	protected void index(IndexContext indexContext) throws Exception {
		RoiModel roiModel = (RoiModel) indexContext.getIndexable();
		roiModel.setRoiType(RoiType.PLAN);
		logger.info(roiModel);
		indexer.index("accountId", roiModel.getAccountId(), roiModel, 1.0f, false);
		
	}

}
