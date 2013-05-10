package download;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.download.handler.DownloadHandler;
import com.cubead.jinjili.download.task.DownloadContext;
import com.cubead.jinjili.download.task.DownloadTask;
import com.cubead.jinjili.download.task.IncrementAccountDownloadTask;
import com.cubead.jinjili.download.task.RoiDownloadTask;
import com.cubead.jinjili.index.handler.IndexHandler;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class DownloadTest {
	
	DownloadHandler downloadHandler;
	
	ApiContext apiContext = null;
	
	IndexHandler indexHandler;
	
	
	@Before
	public void init(){
		
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		
	}
	
	@Test
	public void testTask(){
		downloadHandler = new DownloadHandler();
		downloadHandler.setApiContext(apiContext);
		
		indexHandler = new IndexHandler();
		

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();
		
		calendar.add(Calendar.DATE, -1);
		Date startDate = calendar.getTime();
		
		TaskModel taskModel0 = new TaskModel();
		taskModel0.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext0 = new DownloadContext(taskModel0, apiContext);
		downloadContext0.setStartDate(startDate);
		downloadContext0.setEndDate(endDate);
		
		TaskModel taskModel1 = new TaskModel();
		taskModel1.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext1 = new DownloadContext(taskModel1, apiContext);
		downloadContext1.setStartDate(startDate);
		downloadContext1.setEndDate(endDate);
		TaskModel taskModel2 = new TaskModel();
		taskModel2.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext2 = new DownloadContext(taskModel2, apiContext);
		downloadContext2.setStartDate(startDate);
		downloadContext2.setEndDate(endDate);
		TaskModel taskModel3 = new TaskModel();
		taskModel3.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext3 = new DownloadContext(taskModel3, apiContext);
		downloadContext3.setStartDate(startDate);
		downloadContext3.setEndDate(endDate);
		TaskModel taskModel4 = new TaskModel();
		taskModel4.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext4 = new DownloadContext(taskModel4, apiContext);
		downloadContext4.setStartDate(startDate);
		downloadContext4.setEndDate(endDate);
		
		
//		DownloadTask downloadTask = new WholeAccountDownloadTask(downloadContext1);
		DownloadTask downloadTask = new IncrementAccountDownloadTask(downloadContext0); 
//		runningTasks.put(downloadTask, executorService.submit(downloadTask));
		
		DownloadTask accountDownloadTask = new RoiDownloadTask(downloadContext1, TaskType.DOWNLOAD_ACCOUNTROI);
		
		DownloadTask planDownloadTask = new RoiDownloadTask(downloadContext2, TaskType.DOWNLOAD_PLANROI);
		
		DownloadTask unitDownloadTask = new RoiDownloadTask(downloadContext3, TaskType.DOWNLOAD_UNITROI);
		
		DownloadTask roiDownloadTask = new RoiDownloadTask(downloadContext4, TaskType.DOWNLOAD_KEYWORDROI);
		
		downloadHandler.addTask(downloadTask);
		downloadHandler.addTask(accountDownloadTask);
		downloadHandler.addTask(planDownloadTask);
		downloadHandler.addTask(unitDownloadTask);
		downloadHandler.addTask(roiDownloadTask);
//		
		downloadHandler.download();
		
//		Map<String, String> filePathMap = downloadContext2.getFilePathMap();
//		Set<String> keys = filePathMap.keySet();
//		for (String string : keys) {
//			String path = filePathMap.get(string);
//			System.out.println(path);
//			TaskModel taskModel = downloadContext2.getTaskModel();
//			if(taskModel.getTaskType() == TaskType.DOWNLOAD_PLANROI){
//				taskModel.setTaskType(TaskType.INDEX_PLANROI);
//				
//				IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.PLANROI, "1");
//
//				
////				try {
////					CsvFileParser csvFileParser = new RoiCsvFileParser(path, Calendar.getInstance().getTime());
////					while(csvFileParser.hasNext()){
////						Indexable indexable = csvFileParser.nextIndexable();
////						System.out.println(indexable);
////						IndexLogModel indexLogModel = new IndexLogModel();
////						indexLogModel.setAccountId("1");
////						indexLogModel.setStartDate(Calendar.getInstance().getTime());
////						indexLogModel.setTaskStatus(TaskStatus.INDEXING);
////						indexLogModel.setTaskType(TaskType.INDEX_PLANROI);
////						IndexContext indexContext = new IndexContext(indexLogModel);						
////						indexContext.setIndexerManager(indexerManager);
////						indexContext.setIndexable(indexable);
////						IndexTask indexTask = new RoiIndexTask(indexContext, TaskType.INDEX_PLANROI);
////						indexHandler.addIndexTask(indexTask);
////					}
////					
//////					indexHandler.processTasks();
////					
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
//				
//				
//				
//				
//			}
			
//		}
		
		
		
	}
	
		
	

}
