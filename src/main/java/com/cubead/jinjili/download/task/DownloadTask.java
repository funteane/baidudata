package com.cubead.jinjili.download.task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.util.Tools;
import com.cubead.searchapi.client.utils.HttpClientManager;
import com.cubead.searchapi.exceptions.CubeadBusinessException;

public abstract class DownloadTask implements Callable<TaskModel>{
	
	protected static Logger logger = Logger.getLogger(DownloadTask.class);
	
	private TaskModel taskModel;
	
	private DownloadContext downloadContext;
	
	private Indexer indexer;
	
	
	
	public DownloadTask(DownloadContext downloadContext) {
		this.downloadContext = downloadContext;
		this.taskModel = downloadContext.getTaskModel();
	}

	protected abstract void processDownload(DownloadContext downloadContext) throws Exception;
	
	protected abstract void processIndex(DownloadContext downloadContext) throws Exception;
	
	protected String requestFileId(DownloadContext downloadContext){
		return null;
	}
	
	@Override
	public TaskModel call() throws Exception {
//		taskModel.setStartDate(Calendar.getInstance().getTime());
		if (taskModel.getTaskType() == TaskType.DOWNLOAD_ACCOUNTROI 
				|| taskModel.getTaskType() == TaskType.DOWNLOAD_KEYWORDROI
				|| taskModel.getTaskType() == TaskType.DOWNLOAD_PLANROI
				|| taskModel.getTaskType() == TaskType.DOWNLOAD_UNITROI
				|| taskModel.getTaskType() == TaskType.DOWNLOAD_WHOLEACCOUNT
				|| taskModel.getTaskType() == TaskType.DOWNLOAD_INCREMENTACCOUNT) {

			//文件类数据下载
			if (taskModel.getTaskStatus() == TaskStatus.TODO) { //获取文件ID
				taskModel.setStartDate(Calendar.getInstance().getTime());
				logger.info("从百度获取下载文件ID " + taskModel.getTaskType().getName());
				//从百度获取下载文件ID
				String fileId = requestFileId(downloadContext);
				if (Tools.empty(fileId)) {
					logger.info("获取失败");
					taskModel.setTaskStatus(TaskStatus.FAIL);
					taskModel.setEndDate(Calendar.getInstance().getTime());
					taskModel.setReason("Get whole account download file id failed.");
					
				}else {
					logger.info("获取成功 " + fileId + " " + taskModel.getTaskType().getName());
					taskModel.setFileId(fileId);
					taskModel.setTaskStatus(TaskStatus.IDDONE);
					taskModel.setFileIdDate(Calendar.getInstance().getTime());
				}
				downloadContext.setTaskModel(taskModel);
			}else if (taskModel.getTaskStatus() == TaskStatus.IDDONE) {
				
				try {
					processDownload(downloadContext);
					logger.info("处理下载............." +  taskModel.getTaskType().getName());
					taskModel.setTaskStatus(TaskStatus.DOWNLODED);
					taskModel.setEndDate(Calendar.getInstance().getTime());
					downloadContext.setTaskModel(taskModel);
					
				} catch (CubeadBusinessException e) {
					if(taskModel.getFileIdDate() == null	|| (new Date().getTime() - taskModel.getFileIdDate().getTime()) > 7200000){
						taskModel.setTaskStatus(TaskStatus.FAIL);
					}
					Thread.sleep(100000);
					logger.error("an error happen while downloading whole account.", e);
					e.printStackTrace();
					taskModel.setReason("Get whole account download file failed.");
					downloadContext.setTaskModel(taskModel);
				}catch (Exception e) {
					logger.error(e.getMessage(), e);
					taskModel.setTaskStatus(TaskStatus.FAIL);
					taskModel.setEndDate(Calendar.getInstance().getTime());
					taskModel.setReason("Get whole account download file id failed.");
					downloadContext.setTaskModel(taskModel);
				}
			}else if (taskModel.getTaskStatus() == TaskStatus.DOWNLODED) {
				logger.info("now begin to index this task");
				processIndex(downloadContext);
				taskModel.setTaskStatus(TaskStatus.INDEXING);
				taskModel.setIndexDate(Calendar.getInstance().getTime());
			}else if (taskModel.getTaskStatus() == TaskStatus.INDEXING) {
				taskModel.setTaskStatus(TaskStatus.DONE);
				taskModel.setFinishDate(Calendar.getInstance().getTime());
				downloadContext.setTaskModel(taskModel);
			}else{
				//not update status, next try
				// the file in baidu is not generated over, here we try again.
			}
			
		}else {
			//非文件类下载
			
		}
		
		
		
		return taskModel;
	}
	
	
	
	protected static Date getDownloadTime(String fileName) throws ParseException {
		String[] names = fileName.split("-");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date result = df.parse(names[1] + "-" + names[2]);
		return result;
	}
	
	public static void downloadFile(String filename, String url)	throws Exception {

		//log.info("downloading " + url + "...");

		File file = new File(filename);

		if (!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		
		HttpGet httpGet = null;

		try {
			HttpClient client = HttpClientManager.getInstance();
			httpGet = new HttpGet(url);

			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			// 判断返回的状态是不是200
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				if (entity != null) {
					BufferedInputStream bis = new BufferedInputStream(entity.getContent(), 8092);
					// 保存到本地文件
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 8092);

					try {
						int i = 0;
						while ((i = bis.read()) != -1) {
							bos.write(i);
						}
					} finally {
						if (bis != null) {
							bis.close();
						}
						if (bos != null) {
							bos.close();
						}
					}
				} else {
					// never happen except for head request
					httpGet.abort();
					logger.error("error:no content");
					throw new RuntimeException("error:no content");
				}
			} else {
				httpGet.abort();
				logger.error("error: httpResponse status "+ statusCode + "!!!");
				throw new RuntimeException("error: httpResponse status "+ statusCode + "!!!");
			}

		} catch (Exception e) {

			// 保证出现异常的情况下，链接能正常释放
			if (httpGet != null && !httpGet.isAborted())
				httpGet.abort();

			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getClass().getName() + ":"	+ e.getMessage(), e);
		}

	}
	

	public TaskModel getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
	}

	public DownloadContext getDownloadContext() {
		return downloadContext;
	}

	public void setDownloadContext(DownloadContext downloadContext) {
		this.downloadContext = downloadContext;
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}
	
	
	
	
}
