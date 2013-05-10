package searchapi;

import java.util.Date;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.InitBinder;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.parser.AccountCsvFileParser;
import com.cubead.jinjili.index.parser.CsvFileParser;

public class AccountParserTest {
	
	CsvFileParser csvFileParser ;
	
	Indexer indexer;
	
	IndexerManager indexerManager;
	
	@Before
	public void init(){
		String fileName = "C:/Users/Administrator/Desktop/goldluck/account-20130424-105536-9bc5a4280a0b455bcf8e3e4db22b4ae1.csv";
		indexerManager = new IndexerManager();
		csvFileParser = new AccountCsvFileParser(fileName, new Date());
		indexer = new AccountIndexer(indexerManager);
	}
	
	@Test
	public void index(){
		while(csvFileParser.hasNext()){
			AccountModel accountModel = (AccountModel) csvFileParser.nextIndexable();
			indexer.index("accountId", accountModel.getAccountId(), accountModel, 1.0f, false);
			System.out.println(accountModel);
		}
	}
	
	@Test
	public void query(){
		Query query = new TermQuery(new Term("accountId","5365897"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((AccountModel)indexable);
		}
		
	}

}
