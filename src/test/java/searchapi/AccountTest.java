package searchapi;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;
import com.cubead.searchapi.data.vo.Account;



public class AccountTest {
	
	IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		indexerManager = new IndexerManager();
		indexer = new AccountIndexer(indexerManager);
		
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "金吉列英国事业部";
		String password = "jjL.2013.jinjilie";
		apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
			
		
		
	}
	
//	@Test
	public void delete(){
		indexer.delete("1");
	}
	
//	@Ignore
	@Test
	public void index(){
		Account account = null;
		ISearchApiClient client;
		try {
			client = ClientManager.getSearchApiClient(apiContext);
			account = client.getAccountManager().getAccountInfo();
			
			AccountModel accountModel = new AccountModel();
			BeanUtils.copyProperties(accountModel, account);
			accountModel.setAccountId(account.getUserId());
			System.out.println(accountModel);
			
//			indexer.index("userId", "1", accountModel, 1, false);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void query(){
		Query query  = new TermQuery(new Term("accountId", "5365897"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((AccountModel)indexable);
		}
		
	}
			
			

}
