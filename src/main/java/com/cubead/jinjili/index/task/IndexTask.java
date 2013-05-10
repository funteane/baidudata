package com.cubead.jinjili.index.task;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.cubead.jinjili.domain.model.IndexLogModel;

public abstract class IndexTask implements Callable<IndexLogModel>{
	
	protected static Logger logger = Logger.getLogger(IndexTask.class);
	
	private IndexLogModel indexLogModel;
	
	private IndexContext indexContext;
	
	public IndexTask(IndexContext indexContext) {
		this.indexContext = indexContext;
		this.indexLogModel = indexContext.getIndexLogModel();
	}
	
	
	protected abstract void index(IndexContext indexContext) throws Exception;

	
	@Override
	public IndexLogModel call() throws Exception {
		try {
			logger.info("需要索引的任务是 " + indexLogModel.getTaskType().getName());
			index(indexContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		logger.warn("-------------------------------");
//		if (taskModel.getTaskStatus() == TaskStatus.DOWNLODED) {
//			taskModel.setIndexDate(Calendar.getInstance().getTime());
//			logger.info("需要索引的任务是 " + indexLogModel.getTaskType().getName());
			//从百度获取下载文件ID
//			index(indexContext);
//			taskModel.setTaskStatus(TaskStatus.DONE);
//			indexContext.setTaskModel(taskModel);
			
//		}else {
//			logger.warn("非索引任务");
//		}
		
		
		return indexLogModel;
	}
	
	
	

}
