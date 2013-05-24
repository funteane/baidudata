package schedule;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.job.schedule.DownloadScheduleAccountJob;
import com.cubead.jinjili.job.schedule.DownloadScheduleJob;
import com.cubead.jinjili.job.schedule.DownloadScheduleRoiJob;
import com.cubead.jinjili.job.schedule.KeywordDailyReportJob;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ScheduleTest {
	
	@Autowired
	private DownloadScheduleJob downloadScheduleJob;
	
	@Autowired
	private DownloadScheduleAccountJob downloadScheduleAccountJob;
	
	@Autowired
	private DownloadScheduleRoiJob downloadScheduleRoiJob;
	
	@Autowired
	private KeywordDailyReportJob keywordDailyReportJob;
	
//	@Ignore
	@Test
	public void testDownloadJob(){
//		downloadScheduleJob.execute();
		downloadScheduleAccountJob.execute();
//		downloadScheduleRoiJob.execute();
	}
	
	@Test
	public void testKeywordDailyJob(){
		keywordDailyReportJob.execute();
	}
	
	@Test
	public void testReader() throws Exception{
		IndexReader indexReader = IndexReader.open(FSDirectory.open(new File("D:\\temp\\index\\wholeaccount\\6099130")));
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		KeywordIndexer keywordIndexer = new KeywordIndexer();
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(new TermQuery(new Term("keywordId", "3785977527")), Occur.SHOULD);
		booleanQuery.add(new TermQuery(new Term("keywordId", "3785929629")), Occur.SHOULD);
		booleanQuery.add(new TermQuery(new Term("keywordId", "3766419100")), Occur.SHOULD);
		booleanQuery.add(new TermQuery(new Term("keywordId", "3786649128")), Occur.SHOULD);
		booleanQuery.add(new TermQuery(new Term("keywordId", "3786052947")), Occur.SHOULD);
		booleanQuery.add(new TermQuery(new Term("keywordId", "3685750184")), Occur.SHOULD);
		
//		Term term = new Term("indexType", "KEYWORD");
//		TopDocs topDocs = indexSearcher.search(new TermQuery(term), Integer.MAX_VALUE);
		TopDocs topDocs = indexSearcher.search(booleanQuery, Integer.MAX_VALUE);
		for(ScoreDoc sd : topDocs.scoreDocs) {
			Document document = indexSearcher.doc(sd.doc);
			KeywordModel keywordModel = (KeywordModel) keywordIndexer.convert2Indexable(document);
			System.out.println(keywordModel);
			
		}
		
		
	}

}
