package searchapi;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class WholeAccountTest {

	
	IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		indexerManager = new IndexerManager();
//		indexer = new PlanIndexer(indexerManager);
		
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
//			String fileId = client.getAccountManager().requestDownloadWholeAccount();
			
//			System.out.println(fileId);
			Map<String, String> map =	client.getAccountManager().getDownloadFilePaths("9bc5a4280a0b455bcf8e3e4db22b4ae1");
			System.out.println(map);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
