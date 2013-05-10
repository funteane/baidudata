package searchapi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;
import com.cubead.searchapi.data.vo.Keyword;
import com.cubead.searchapi.data.vo.Plan;

public class KeywordTest {
IndexerManager indexerManager;
	
	Indexer indexer;
	
	ApiContext apiContext = null;
	@Before
	public void init() throws IOException{
		indexerManager = new IndexerManager();
		indexerManager.addDirectory(FSDirectory.open(new File("D:/temp/index/wholeaccount/2466159/")));
		indexer = new KeywordIndexer(indexerManager);
		
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		
	}
	
	@Ignore
	@Test
	public void index(){
		ISearchApiClient client;
		try {
			client = ClientManager.getSearchApiClient(apiContext);
			Keyword[] keywords = client.getKeywordManager().getKeywordByUnitId(new long[]{204390755});
			for (Keyword keyword : keywords) {
				System.out.println(keyword);
				KeywordModel keywordModel = new KeywordModel();
				BeanUtils.copyProperties(keywordModel, keyword);
				indexer.index("keywordId", keyword.getKeywordId(), keywordModel, 1.0f, false);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		System.out.println("begin");
		Query query  = new TermQuery(new Term("keywordId", "2749565288"));
//		Query query  = new TermQuery(new Term("accountId", "5365897"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((KeywordModel)indexable);
			System.out.println(((KeywordModel)indexable).getIndexType());
		}
		
	}


}
