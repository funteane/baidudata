package com.cubead.jinjili.download.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.download.task.DownloadTask;
import com.cubead.searchapi.client.data.ApiContext;

@Service
public class DownloadHandler {
	
	private Logger logger = Logger.getLogger(DownloadHandler.class);
	
	@Autowired
	private ICommonService commonService;
	

	private final int THREAD_COUNT = 10;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
	
	private  Map<DownloadTask, Future<TaskModel>> runningTasks = new HashMap<DownloadTask, Future<TaskModel>>();
	private List< DownloadTask> todoTasks = new ArrayList<DownloadTask>();
	
	List<TaskModel> doneTasks = new ArrayList<TaskModel>();
	List<TaskModel> errorTasks = new ArrayList<TaskModel>();
	
	private ApiContext apiContext;
	
	
	public DownloadHandler(){
		
	}
	
	public DownloadHandler(ApiContext apiContext){
		this.apiContext = apiContext;
	}
	
	
	
	public ApiContext getApiContext() {
		return apiContext;
	}



	public void setApiContext(ApiContext apiContext) {
		this.apiContext = apiContext;
	}



	public void download(){
		for(DownloadTask downloadTask : todoTasks){
			Future<TaskModel> result =  executorService.submit(downloadTask);
			runningTasks.put(downloadTask, result);
		}
		logger.info("一共有" + runningTasks.size() + "个项目需要执行");
		logger.info("线程池任务个数0：" +  ((ThreadPoolExecutor) executorService).getTaskCount());
		processTasks(runningTasks);
	}

	
	
	private void processTasks(Map<DownloadTask, Future<TaskModel>> runningTasks ) {
		if (runningTasks.size() == 0) {
			return;
		}
		logger.info("线程池任务个数1：" +  ((ThreadPoolExecutor) executorService).getTaskCount());
		
		Map<DownloadTask, Future<TaskModel>> againTasks = new HashMap<DownloadTask, Future<TaskModel>>();
		Set<DownloadTask> keySet = runningTasks.keySet();
		for(DownloadTask taskKey : keySet){
			try {
				TaskModel taskModel = runningTasks.get(taskKey).get();
				logger.info("///////////////////");
				logger.info(taskModel);
				if (taskModel.getTaskStatus() == TaskStatus.FAIL) {
					errorTasks.add(taskModel);
					logger.info("need manual operate update db");
					
					commonService.saveOrupdate(taskModel);
				}else if (taskModel.getTaskStatus() == TaskStatus.IDDONE) {
					logger.info("need operate again");
					Future<TaskModel> result =  executorService.submit(taskKey);
					logger.info("线程池任务个数iddone：" +  ((ThreadPoolExecutor) executorService).getTaskCount());
					againTasks.put(taskKey, result);
					commonService.saveOrupdate(taskModel);
				}else if (taskModel.getTaskStatus() == TaskStatus.DOWNLODED) {
					logger.info("begin to indexing");
					Future<TaskModel> result =  executorService.submit(taskKey);
					againTasks.put(taskKey, result);
					commonService.saveOrupdate(taskModel);
				}else if (taskModel.getTaskStatus() == TaskStatus.INDEXING) {
//					doneTasks.add(taskModel);
					logger.info("begin to finishing");
//					Future<TaskModel> result =  executorService.submit(taskKey);
//					againTasks.put(taskKey, result);
					commonService.saveOrupdate(taskModel);
					Future<TaskModel> result =  executorService.submit(taskKey);
					againTasks.put(taskKey, result);
				}else if (taskModel.getTaskStatus() == TaskStatus.DONE) {
					doneTasks.add(taskModel);
					logger.info("finished");
//					Future<TaskModel> result =  executorService.submit(taskKey);
//					againTasks.put(taskKey, result);
					commonService.saveOrupdate(taskModel);					
				}else{
					logger.info("not finished");
//					unDoneTasks.put(taskKey, runningTasks.get(taskKey));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		logger.info("重启完成任务数 " + againTasks.size());
		
		processTasks(againTasks);
		
	}
	
	
	
	public void addTask(DownloadTask downloadTask){
		todoTasks.add(downloadTask);
	}
	
	public void removeTask(DownloadTask downloadTask){
		todoTasks.remove(downloadTask);
	}

}
