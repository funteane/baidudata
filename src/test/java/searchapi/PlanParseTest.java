package searchapi;

import java.util.Date;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.PlanCsvFileParser;

public class PlanParseTest {
	CsvFileParser csvFileParser ;
	
	Indexer indexer;
	
	IndexerManager indexerManager;
	
	@Before
	public void init(){
		String fileName = "d:/goldluck/campaign.csv";
		indexerManager = new IndexerManager();
		csvFileParser = new PlanCsvFileParser(fileName, new Date());
		indexer = new PlanIndexer(indexerManager);
		
	}
	
	@Test
	public void index(){
		while(csvFileParser.hasNext()){
			PlanModel planModel = (PlanModel) csvFileParser.nextIndexable();
			indexer.index("planId", planModel.getPlanId(), planModel, 1.0f, false);
			System.out.println(planModel);
		}
	}
	
	@Test
	public void query(){
		Query query = new TermQuery(new Term("planId","9840427"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((PlanModel)indexable);
		}
		
	}


}
