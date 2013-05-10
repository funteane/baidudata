package searchapi;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.QualityModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.QualityIndexer;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class QualityTest {
	
	IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init(){
		indexerManager = new IndexerManager();
		indexer = new QualityIndexer(indexerManager);
		
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		
	}
	
	@Test
	public void index(){
		ISearchApiClient client;
		try {
//			client = ClientManager.getSearchApiClient(apiContext);
//			com.cubead.searchapi.client.data.Quality[] qualities = client.getKeywordManager().getKeywordQuality(KeywordQualityType.KEYWORD, new long[]{3654062262l});
//			
//			for (com.cubead.searchapi.client.data.Quality quality : qualities) {
//				System.err.println(quality);
//				QualityModel qualityModel = new QualityModel();
//				BeanUtils.copyProperties(qualityModel, quality);
//				indexer.index(String.valueOf(quality.getId()), qualityModel, 1.0f, false);
//			}
			
//			for (Plan plan : plans) {
//				System.out.println(plan);
//				PlanModel planModel = new PlanModel();
//				BeanUtils.copyProperties(planModel, plan);
//				indexer.index(plan.getPlanId(), planModel, 1.0f, false);
//			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		QualityModel qualityModel = new QualityModel();
		qualityModel.setUnitId("204390755");
		qualityModel.setPlanId("9840427");
		qualityModel.setKeywordId("3654062262");
		qualityModel.setQuality(22);
		
		indexer.index("keywordId", "3654062262", qualityModel, 1.0f, false);
		
//		Query query  = new TermQuery(new Term("keywordId", "3654062262"));
//		indexer = new KeywordIndexer(indexerManager);
//		List<Indexable> accounts =  indexer.search(query, 10);
//		for(Indexable indexable : accounts){
//			System.out.println((KeywordModel)indexable);
//		}
		
	}


}
