package download;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskStatus;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.download.task.DownloadContext;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.jinjili.index.indexer.QualityUpdateIndexer;
import com.cubead.jinjili.index.indexer.RoiIndexer;
import com.cubead.jinjili.index.indexer.UnitIndexer;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.parser.AccountCsvFileParser;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.KeywordCsvFileParser;
import com.cubead.jinjili.index.parser.PlanCsvFileParser;
import com.cubead.jinjili.index.parser.RoiCsvFileParser;
import com.cubead.jinjili.index.parser.UnitCsvFileParser;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.client.type.Method;
import com.cubead.searchapi.client.type.Network;

public class IndexIncrementTest {
	private static Map<String, IndexType> indexTypeMap = new HashMap<String, IndexType>();
	static{
		indexTypeMap.put("AccountFilePath", IndexType.ACCOUNT);
		indexTypeMap.put("AdgroupFilePath", IndexType.UNIT);
		indexTypeMap.put("CampaignFilePath", IndexType.PLAN);
		indexTypeMap.put("KeywordFilePath", IndexType.KEYWORD);
	}
	
	public static void main(String[] args) {
		String token = "14085b1c9d84bb1b52b9b64cd6a71c08";
		String userName = "bj-dafengso";
		String password = "Bylf2008";
		ApiContext  apiContext = new ApiContext(userName, password, Network.BAIDU ,Method.POST, token);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();
		
		calendar.add(Calendar.DATE, -1);
		Date startDate = calendar.getTime();
		
		TaskModel taskModel0 = new TaskModel();
		taskModel0.setTaskStatus(TaskStatus.TODO);
		DownloadContext downloadContext0 = new DownloadContext(taskModel0, apiContext);
		downloadContext0.setStartDate(startDate);
		downloadContext0.setEndDate(endDate);
		
		
		Map<String, String> roiFilePathMap = new HashMap<String, String>();
//		roiFilePathMap.put("keyword", "d:/goldluck/9c72ff4bf6867de47afa0bc88ff0187d-1");
//		roiFilePathMap.put("unit", "d:/goldluck/b0629944c82d1b573d9a043d3caee9d1-0");
//		roiFilePathMap.put("account", "d:/goldluck/dd60f0f6104346411058735324fc25c8-0");
//		roiFilePathMap.put("plan", "d:/goldluck/19e43bc7aa8f6d81978a8b2aa504cd34-0");
		Map<String, TaskType> taskTypes = new HashMap<String, TaskType>();
		taskTypes.put("account", TaskType.DOWNLOAD_ACCOUNTROI);
		taskTypes.put("plan", TaskType.DOWNLOAD_PLANROI);
		taskTypes.put("unit", TaskType.DOWNLOAD_UNITROI);
		taskTypes.put("keyword", TaskType.DOWNLOAD_KEYWORDROI);
		
		
		Set<String> keys = roiFilePathMap.keySet();
		for (String key : keys) {
			String path = roiFilePathMap.get(key);
			System.out.println(path);
			TaskModel taskModel = downloadContext0.getTaskModel();
			RoiType roiType = RoiType.PLAN;
			IndexType indexType = IndexType.PLANROI;
			switch (taskTypes.get(key)) {
			case DOWNLOAD_ACCOUNTROI:
				taskModel.setTaskType(TaskType.INDEX_ACCOUNTROI);
				roiType = RoiType.ACCOUNT;
				indexType = IndexType.ACCOUNTROI;
				break;
			case DOWNLOAD_PLANROI:
				taskModel.setTaskType(TaskType.INDEX_PLANROI);
				roiType = RoiType.PLAN;
				indexType = IndexType.PLANROI;
				break;
			case DOWNLOAD_UNITROI:
				taskModel.setTaskType(TaskType.INDEX_PLANROI);
				roiType = RoiType.UNIT;
				indexType = IndexType.UNITROI;
				break;
			case DOWNLOAD_KEYWORDROI:
				taskModel.setTaskType(TaskType.INDEX_PLANROI);
				roiType = RoiType.KEYWORD;
				indexType = IndexType.KEYWORDROI;
				break;				
			}
			
			IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext0.getAccountId());
			CsvFileParser csvFileParser = new RoiCsvFileParser(path, Calendar.getInstance().getTime());
			Indexer indexer = new RoiIndexer(indexerManager);
			while(csvFileParser.hasNext()){
				Indexable indexable = csvFileParser.nextIndexable();
				RoiModel roiModel = (RoiModel) indexable;
				roiModel.setRoiType(roiType);
				indexer.index(roiModel.getAccountId(), roiModel.getAccountId(), indexable, 1.0f, false);
				
			}		
			indexerManager.release();
		}
		
		
		
		
		Map<String, String> filePathMap = new HashMap<String, String>();
		filePathMap.put("CampaignFilePath", "D:/goldluck/campaign.csv");
		filePathMap.put("AdgroupFilePath", "D:/goldluck/adgroup.csv");
		filePathMap.put("KeywordFilePath", "D:/goldluck/keyword.csv");
		
		Set<String> key0s = filePathMap.keySet();
		
		for (String key : key0s) {
			String path = filePathMap.get(key);
			System.out.println(path);
			IndexType indexType = indexTypeMap.get(key);
			if (indexType == null) {
				continue;
			}
			IndexerManager indexerManager;
			CsvFileParser csvFileParser;
			Indexer indexer ;
			switch (indexType) {
			case ACCOUNT:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext0.getAccountId());
				csvFileParser = new AccountCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new AccountIndexer(indexerManager);
				
				while(csvFileParser.hasNext()){
					Indexable indexable = csvFileParser.nextIndexable();
					AccountModel accountModel = (AccountModel) indexable;
					accountModel.setDeleted("0");
					indexer.index("accountId", accountModel.getAccountId(), indexable, 1.0f, true);
				}		
				indexerManager.release();
				break;
			case PLAN:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext0.getAccountId());
				csvFileParser = new PlanCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new PlanIndexer(indexerManager);
				while(csvFileParser.hasNext()){
					Indexable indexable = csvFileParser.nextIndexable();
					PlanModel planModel = (PlanModel) indexable;
					indexer.index("planId", planModel.getPlanId(), indexable, 1.0f, true);
				}
				indexerManager.release();
				break;
			case UNIT:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext0.getAccountId());
				csvFileParser = new UnitCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new UnitIndexer(indexerManager);
				while(csvFileParser.hasNext()){
					Indexable indexable = csvFileParser.nextIndexable();
					UnitModel unitModel = (UnitModel) indexable;
					indexer.index("unitId", unitModel.getUnitId(), indexable, 1.0f, true);
				}		
				indexerManager.release();
				break;
			case KEYWORD:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext0.getAccountId());
				IndexerManager roiIndexerManager = IndexMangerFactory.getIndexerManager(IndexType.KEYWORDROI, downloadContext0.getAccountId());
				csvFileParser = new KeywordCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new KeywordIndexer(indexerManager);
				Indexer qualityUpdateIndexer = new QualityUpdateIndexer(roiIndexerManager);
				while(csvFileParser.hasNext()){
					Indexable indexable = csvFileParser.nextIndexable();
					KeywordModel keywordModel = (KeywordModel) indexable;
					
					indexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
					qualityUpdateIndexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
				}		
				indexerManager.release();
				roiIndexerManager.release();
				
				break;
			}
		}
	}

}
