package schedule;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubead.jinjili.job.schedule.DownloadScheduleJob;
import com.cubead.jinjili.job.schedule.KeywordDailyReportJob;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ScheduleTest {
	
	@Autowired
	private DownloadScheduleJob downloadScheduleJob;
	
	@Autowired
	private KeywordDailyReportJob keywordDailyReportJob;
	
	@Ignore
	@Test
	public void testDownloadJob(){
		downloadScheduleJob.execute();
	}
	
	@Test
	public void testKeywordDailyJob(){
		keywordDailyReportJob.execute();
	}

}
