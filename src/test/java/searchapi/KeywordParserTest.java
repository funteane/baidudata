package searchapi;

import java.util.Date;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.UnitIndexer;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.KeywordCsvFileParser;
import com.cubead.jinjili.index.parser.UnitCsvFileParser;

public class KeywordParserTest {

	CsvFileParser csvFileParser ;
	
	Indexer indexer;
	
	IndexerManager indexerManager;
	
	@Before
	public void init(){
		String fileName = "C:/Users/Administrator/Desktop/goldluck/keyword-20130424-105536-9bc5a4280a0b455bcf8e3e4db22b4ae1.csv";
		indexerManager = new IndexerManager();
		csvFileParser = new KeywordCsvFileParser(fileName, new Date());
		indexer = new KeywordIndexer(indexerManager);
		
	}
	
	@Test
	public void index(){
		while(csvFileParser.hasNext()){
			KeywordModel keywordModel = (KeywordModel) csvFileParser.nextIndexable();
			indexer.index("keywordId", keywordModel.getKeywordId(), keywordModel, 1.0f, false);
			System.out.println(keywordModel);
		}
	}
	
	@Test
	public void query(){
		Query query = new TermQuery(new Term("keywordId","3714400866"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((KeywordModel)indexable);
		}
		
	}

}
