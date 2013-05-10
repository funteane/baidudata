package searchapi;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class RoiTest {
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		
	}
	
	@Test
	public void index(){
		ISearchApiClient client;
		try {
			client = ClientManager.getSearchApiClient(apiContext);
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			Date endDate = calendar.getTime();
			
			calendar.add(Calendar.DATE, -1);
			Date startDate = calendar.getTime();
			
			
			String reportId = client.getrReportManager().requestReportId(startDate, endDate, "2");
//			String reportId = "25f0249efa3e07c28d231c7f85156ae3-1";
//			String reportId = client.getrReportManager().requestReportId(startDate, endDate, "10");
//			String reportId = "37376bc796301d06ef8a7b962f7c2e9c-0";
//			String reportId = client.getrReportManager().requestReportId(startDate, endDate, "11");
//			String reportId = "74c0e084ed10541d4806f681d70ec0e2-1";
//			String reportId = client.getrReportManager().requestReportId(startDate, endDate, "14");
//			String reportId = "6148d7c567992370ec6615cdc442017d-0";
//			String reportId = client.getrReportManager().requestReportId(startDate, endDate, "2 10 11 14");
//			String reportId = "6148d7c567992370ec6615cdc442017d-0";
			System.out.println(reportId);
			
//			String path = client.getrReportManager().requestReportDownloadUrl(reportId);
//			System.out.println(path);
			
//			String fileId = client.getAccountManager().requestDownloadWholeAccount();
			
//			System.out.println(fileId);
//			Map<String, String> map =	client.getAccountManager().getDownloadFilePaths("9bc5a4280a0b455bcf8e3e4db22b4ae1");
//			System.out.println(map);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
