package searchapi;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.QualityUpdateIndexer;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.KeywordCsvFileParser;

public class QualityIndexerTest {

	IndexerManager indexerManager;
	CsvFileParser csvFileParser;
	String path = "d:/goldluck/keyword.csv";
	Indexer indexer;
	Indexer qualityUpdateIndexer ;
	IndexerManager roiIndexerManager;
	
	@Before
	public void init(){
		indexerManager = IndexMangerFactory.getIndexerManager(IndexType.KEYWORD, "123456");
		roiIndexerManager = IndexMangerFactory.getIndexerManager(IndexType.KEYWORDROI, "123456");
		csvFileParser = new KeywordCsvFileParser(path, Calendar.getInstance().getTime());
		indexer = new KeywordIndexer(indexerManager);
		qualityUpdateIndexer = new QualityUpdateIndexer(roiIndexerManager);
		
		
	}
	
	@Test
	public void test() throws Exception{
		while(csvFileParser.hasNext()){
			Indexable indexable = csvFileParser.nextIndexable();
			KeywordModel keywordModel = (KeywordModel) indexable;
			indexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
			qualityUpdateIndexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
		}		
		indexerManager.releaseWriter();
		roiIndexerManager.releaseWriter();
	}
}
