package searchapi;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;
import com.cubead.searchapi.data.vo.Plan;

public class PlanTest {
	
	
	IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		indexerManager = new IndexerManager();
		indexer = new PlanIndexer(indexerManager);
		
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
//			Plan[] plans = client.getPlanManager().getAllPlan();
			Plan[] plans = client.getPlanManager().getPlan(new String[]{"7074762"});
			for (Plan plan : plans) {
				System.out.println(plan);
				PlanModel planModel = new PlanModel();
				BeanUtils.copyProperties(planModel, plan);
				indexer.index("planId", plan.getPlanId(), planModel, 1.0f, false);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		Query query  = new TermQuery(new Term("id", "352fd2833a85ccca79fa040bc5228de9"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((PlanModel)indexable);
		}
		
	}

}
