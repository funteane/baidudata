package searchapi;

import java.util.Date;
import java.util.List;

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
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.UnitCsvFileParser;

public class UnitParserTest {
	CsvFileParser csvFileParser ;
	
	Indexer indexer;
	
	IndexerManager indexerManager;
	
	@Before
	public void init(){
		String fileName = "C:/Users/Administrator/Desktop/goldluck/adgroup-20130424-105536-9bc5a4280a0b455bcf8e3e4db22b4ae1.csv";
		indexerManager = new IndexerManager();
		csvFileParser = new UnitCsvFileParser(fileName, new Date());
		indexer = new UnitIndexer(indexerManager);
		
	}
	
	@Test
	public void index() throws Exception{
		while(csvFileParser.hasNext()){
			UnitModel unitModel = (UnitModel) csvFileParser.nextIndexable();
			indexer.index("unitId", unitModel.getUnitId(), unitModel, 1.0f, false);
			System.out.println(unitModel);
		}
	}
	
	@Test
	public void query(){
		Query query = new TermQuery(new Term("unitId","204390755"));
		List<Indexable> accounts =  indexer.search(query, 10);
		for(Indexable indexable : accounts){
			System.out.println((UnitModel)indexable);
		}
		
	}

}
