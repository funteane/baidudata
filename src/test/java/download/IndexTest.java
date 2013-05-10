package download;

import java.util.Calendar;

import org.apache.lucene.index.IndexWriter;
import org.junit.Before;
import org.junit.Test;

import com.cubead.jinjili.domain.model.IndexLogModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.download.handler.DownloadHandler;
import com.cubead.jinjili.index.handler.IndexHandler;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.IndexWriterFactory;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.RoiCsvFileParser;
import com.cubead.jinjili.index.task.IndexContext;
import com.cubead.jinjili.index.task.IndexTask;
import com.cubead.jinjili.index.task.RoiIndexTask;
import com.cubead.searchapi.client.data.ApiContext;

public class IndexTest {
	
DownloadHandler downloadHandler;
	
	ApiContext apiContext = null;
	
	IndexHandler indexHandler;
	
	
	@Before
	public void init(){
		
	     indexHandler = new IndexHandler();
		
	}
	
	@Test
	public void index(){
//		IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.PLANROI, "1");
		try {
			IndexWriter indexWriter = IndexWriterFactory.getIndexWriter(IndexType.PLANROI, "1");
			String path = "d:/goldluck/6db4473d3cb037d471be4a2ca75dc6c8-0";
			
			CsvFileParser csvFileParser = new RoiCsvFileParser(path, Calendar.getInstance().getTime());
			while(csvFileParser.hasNext()){
				Indexable indexable = csvFileParser.nextIndexable();
				System.out.println(indexable);
				IndexLogModel indexLogModel = new IndexLogModel();
				indexLogModel.setAccountId("1");
				indexLogModel.setStartDate(Calendar.getInstance().getTime());
				indexLogModel.setTaskStatus(TaskStatus.INDEXING);
				indexLogModel.setTaskType(TaskType.INDEX_PLANROI);
				IndexContext indexContext = new IndexContext();	
				indexContext.setIndexLogModel(indexLogModel);
				indexContext.setIndexWriter(indexWriter);
				indexContext.setIndexable(indexable);
				IndexTask indexTask = new RoiIndexTask(indexContext, TaskType.INDEX_PLANROI);
				indexHandler.addIndexTask(indexTask);
			}
			
//			indexHandler.processTasks();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
