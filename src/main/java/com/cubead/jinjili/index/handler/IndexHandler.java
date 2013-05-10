package com.cubead.jinjili.index.handler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.cubead.jinjili.domain.model.IndexLogModel;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.index.task.IndexTask;

@Deprecated
public class IndexHandler {
	
	private Logger logger = Logger.getLogger(IndexHandler.class);
	
	private final int THREAD_COUNT = 10;
	private final int QUEUE_LENGTH = 10000;
	
	private ExecutorService executorService = Executors.newScheduledThreadPool(THREAD_COUNT);
	
//	private BlockingQueue<Future<TaskModel>> blockingQueue ;
	private  Map<IndexTask, Future<IndexLogModel>> runningTasks ;
	
	List<TaskModel> doneTasks = new ArrayList<TaskModel>();
	List<TaskModel> errorTasks = new ArrayList<TaskModel>();
	
	
	public IndexHandler(){
//		 blockingQueue = new ArrayBlockingQueue<Future<TaskModel>>(QUEUE_LENGTH) ;
		 runningTasks = new HashMap<IndexTask, Future<IndexLogModel>>();
//		 processTasks();
	}
	
	public void addIndexTask(IndexTask indexTask) throws InterruptedException{
		runningTasks.put(indexTask, executorService.submit(indexTask));
		logger.info("加入任务");
//		blockingQueue.put(executorService.submit(indexTask));
	}
	
	public void processTasks(){
		try {
			Set<IndexTask> keys = runningTasks.keySet();
			for (IndexTask indexTask : keys) {
				Future<IndexLogModel> future = runningTasks.get(indexTask);
				logger.info("haha");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
//			try {
//				Future<TaskModel> future = blockingQueue.take();
//				while(future != null){
//				TaskModel taskModel = future.get();
////				if (taskModel.getTaskStatus() == TaskStatus.DONE) {
//					logger.info("update to db");
////				}
//				}
//				
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
	}
	
	
	
	
	

}
