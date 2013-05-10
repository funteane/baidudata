package schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.job.manual.DownloadSingleManualJob;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManualJobTest {
	
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private DownloadSingleManualJob downloadManualJob;
	
	public void init(){
		
	}
	
	@Test
	public void testJob(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();
//		calendar.add(Calendar.DATE, -1);
		Date startDate = calendar.getTime();
		
		TaskModel taskModel = commonService.get(TaskModel.class, 32l);
		BaiduAccount baiduAccount = commonService.get(BaiduAccount.class, 1l);
		
		downloadManualJob.setTaskModel(taskModel);
		downloadManualJob.setBaiduAccount(baiduAccount);
		downloadManualJob.setStartDate(startDate);
		downloadManualJob.setEndDate(endDate);
		
		
		downloadManualJob.execute();
	
		
	}
	
	

}
