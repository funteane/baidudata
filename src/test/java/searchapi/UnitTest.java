package searchapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.UnitIndexer;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;
import com.cubead.searchapi.data.vo.Unit;

public class UnitTest {

	
	IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		indexerManager = new IndexerManager();
		indexer = new UnitIndexer(indexerManager);
		
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
			//Unit[] units = 
			Map<String, long[]> map = new HashMap<String, long[]>();
//			map.put("planId", new long[]{9840427});
			map.put("unitId", new long[]{204390755});
			
			Unit[] units = client.getUnitManager().getUnit(map);
			
			for (Unit unit : units) {
				System.out.println(unit);
				UnitModel unitModel = new UnitModel();
				BeanUtils.copyProperties(unitModel, unit);
				indexer.index("unitId", unit.getUnitId(), unitModel, 1.0f, false);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		Query query  = new TermQuery(new Term("accountId", "5365897"));
//		Query query  = new TermQuery(new Term("unitId", "204390755"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((UnitModel)indexable);
		}
		
	}

}
