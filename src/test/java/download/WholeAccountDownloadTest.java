package download;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.download.task.DownloadContext;
import com.cubead.jinjili.download.task.DownloadTask;
import com.cubead.jinjili.download.task.WholeAccountDownloadTask;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class WholeAccountDownloadTest {
	
	private DownloadTask downloadJob;
	
	private DownloadContext downloadContext;
	
	TaskModel taskModel ;
	
	@Before
	public void init(){
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		ApiContext apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		TaskModel taskModel = new TaskModel();
		taskModel.setTaskStatus(TaskStatus.TODO);
		downloadContext = new DownloadContext(taskModel, apiContext);
		downloadJob = new WholeAccountDownloadTask(downloadContext);
	}
	
	
	@Test
	public void run() throws Exception{
//		TaskModel taskModel = downloadJob.call();
//		System.err.println(taskModel);
		taskModel = downloadContext.getTaskModel();//用于第二次
		taskModel.setTaskStatus(TaskStatus.DOWNLOADING); //用于第二次
		taskModel.setFileId("4ff5c12ff0af9789079ce04e5a2921d2");//用于第二次
		taskModel = downloadJob.call();
		System.err.println(taskModel);
	}
	

}
