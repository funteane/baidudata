package searchapi;

import java.util.Date;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.RoiIndexer;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.RoiCsvFileParser;

public class RoiParserTest {
	
	CsvFileParser csvFileParser ;
	
	Indexer indexer;
	
	IndexerManager indexerManager;
	
	@Before
	public void init(){
		String fileName = "d:/goldluck/9c72ff4bf6867de47afa0bc88ff0187d-1";
		indexerManager = new IndexerManager();
		csvFileParser = new RoiCsvFileParser(fileName, new Date());
		indexer = new RoiIndexer(indexerManager);
	}
	
	@Test
	public void index(){
		while(csvFileParser.hasNext()){
			RoiModel roiModel = (RoiModel) csvFileParser.nextIndexable();
			roiModel.setRoiType(RoiType.KEYWORD);
			indexer.index("accountId", roiModel.getAccountId(), roiModel, 1.0f, false);
			System.out.println(roiModel);
		}
	}
	
	@Test
	public void query(){
		Query query = new TermQuery(new Term("createDate","2013-05-06"));
		List<Indexable> accounts =  indexer.search(query, 100);
		for(Indexable indexable : accounts){
			System.out.println((RoiModel)indexable);
		}
		
	}

}
