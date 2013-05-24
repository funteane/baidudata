package com.cubead.jinjili.download.task;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cubead.jinjili.common.ApplicationProperties;
import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.AccountIndexer;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.PlanIndexer;
import com.cubead.jinjili.index.indexer.UnitIndexer;
import com.cubead.jinjili.index.parser.AccountCsvFileParser;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.KeywordCsvFileParser;
import com.cubead.jinjili.index.parser.PlanCsvFileParser;
import com.cubead.jinjili.index.parser.UnitCsvFileParser;
import com.cubead.jinjili.util.ZipUtil;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;
import com.cubead.searchapi.exceptions.CubeadBusinessException;

public class IncrementAccountDownloadTask extends DownloadTask{
	
	private static Map<String, IndexType> indexTypeMap = new HashMap<String, IndexType>();
	static{
		indexTypeMap.put("AccountFilePath", IndexType.ACCOUNT);
		indexTypeMap.put("AdgroupFilePath", IndexType.UNIT);
		indexTypeMap.put("CampaignFilePath", IndexType.PLAN);
		indexTypeMap.put("KeywordFilePath", IndexType.KEYWORD);
	}

	public IncrementAccountDownloadTask(DownloadContext downloadContext) {
		super(downloadContext);
		downloadContext.getTaskModel().setTaskType(TaskType.DOWNLOAD_INCREMENTACCOUNT);
	}

	@Override
	protected void processDownload(DownloadContext downloadContext) throws Exception {
		logger.info("download");
//		TaskModel taskModel = downloadContext.getTaskModel();
		
		Map<String, String> filePathMap = getIncementAccountFilePaths(downloadContext);
		downloadContext.getFilePathMap().putAll(filePathMap);
		System.out.println(filePathMap);
		
		logger.info("index ");
	}

	private Map<String, String> getIncementAccountFilePaths(DownloadContext downloadContext) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		ApiContext apiContext = downloadContext.getApiContext(); 
		
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		Map<String, String> map = null;
		try{
			map = searchApiClient.getAccountManager().getDownloadChangedFilePaths(downloadContext.getTaskModel().getFileId());
		}catch(CubeadBusinessException e){
			throw new Exception(e.getCause());
		}
		if(map == null || map.isEmpty()){
			//not finished generate whole account report
			throw new CubeadBusinessException("Not generated.");
		}
		boolean firstTime = true;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			String url = map.get(key);
			
			String downloadPath = ApplicationProperties.getProproperty("base.increment.account.download.path")
					.concat(downloadContext.getAccountId()).concat("/");//ConfigUtil.getBaiduWholeAccountFolder((String)setting.get("tenantId")) + "/";
			File path = new File(downloadPath);
			
			if (!path.exists()){
				path.mkdirs();
			}

			String zipFileName = url.replaceAll(".+((account\\-|campaign\\-|adgroup\\-|keyword\\-|creative\\-)[0-9a-z\\-]+?\\.zip).+", "$1");

			// now, zipFile = creative-20130217-115837-81811247f7c01e86fb291106166b02b7.zip
			
//			if(firstTime){
//				String downDate = DateUtil.dateToStrLong(getWholeAccountDownloadTime(zipFile));
//				result.put("downDate", downDate);
//				firstTime = false;
//			}

			zipFileName = zipFileName.replaceAll("-\\d{8}-\\d{6}-[0-9a-z]+\\.zip", ".zip");
			
			// now, zipFile = creative.zip
			
			logger.debug("zipFile is " + zipFileName);

			// 下载压缩文件
			downloadFile(downloadPath.concat(zipFileName), url);

			// 解压
			String newFileName = downloadPath.concat(zipFileName).replaceAll("\\.zip", ".csv");
			ZipUtil.unzipToFile(downloadPath.concat(zipFileName), newFileName);
			File zipFile = new File(downloadPath.concat(zipFileName));
			zipFile.delete();

			result.put(key, newFileName);

		}

		return result;
	}
	
	@Override
	protected String requestFileId(DownloadContext downloadContext) {
		ApiContext apiContext = downloadContext.getApiContext(); //getApiContext(setting);
		try {
			ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
			String fileId = searchApiClient.getAccountManager().requestDownloadChangedWholeAccount(downloadContext.getStartDate());
			return fileId;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.requestFileId(downloadContext);
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)		throws Exception {
		Map<String, String> filePathMap = downloadContext.getFilePathMap();
		Set<String> keys = filePathMap.keySet();
		
		for (String key : keys) {
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
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext.getAccountId());
				csvFileParser = new AccountCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new AccountIndexer(indexerManager);
				try {
					while(csvFileParser.hasNext()){
						Indexable indexable = csvFileParser.nextIndexable();
						AccountModel accountModel = (AccountModel) indexable;
						accountModel.setDeleted("0");
						indexer.index("accountId", accountModel.getAccountId(), indexable, 1.0f, true);
					}		
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new Exception(e);
				}finally{
					indexerManager.releaseWriter();
				}
				break;
			case PLAN:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext.getAccountId());
				csvFileParser = new PlanCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new PlanIndexer(indexerManager);
				try {
					while(csvFileParser.hasNext()){
						Indexable indexable = csvFileParser.nextIndexable();
						PlanModel planModel = (PlanModel) indexable;
						indexer.index("planId", planModel.getPlanId(), indexable, 1.0f, true);
					}
					
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new Exception(e);
				}finally{
					indexerManager.releaseWriter();
				}
				break;
			case UNIT:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext.getAccountId());
				csvFileParser = new UnitCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new UnitIndexer(indexerManager);
				try {
					while(csvFileParser.hasNext()){
						Indexable indexable = csvFileParser.nextIndexable();
						UnitModel unitModel = (UnitModel) indexable;
						indexer.index("unitId", unitModel.getUnitId(), indexable, 1.0f, true);
					}		
					
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new Exception(e);
				}finally{
					indexerManager.releaseWriter();
				}
				break;
			case KEYWORD:
				indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext.getAccountId());
//				IndexerManager roiIndexerManager = IndexMangerFactory.getIndexerManager(IndexType.KEYWORDROI, downloadContext.getAccountId());
				csvFileParser = new KeywordCsvFileParser(path, Calendar.getInstance().getTime());
				indexer = new KeywordIndexer(indexerManager);
//				Indexer qualityUpdateIndexer = new QualityUpdateIndexer(roiIndexerManager);
				try {
					while(csvFileParser.hasNext()){
						Indexable indexable = csvFileParser.nextIndexable();
						KeywordModel keywordModel = (KeywordModel) indexable;
						indexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
//						qualityUpdateIndexer.index("keywordId", keywordModel.getKeywordId(), indexable, 1.0f, true);
					}			
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new Exception(e);
				} finally{
					indexerManager.releaseWriter();
//					roiIndexerManager.release();
				}
				
				break;
			}
		}
	}

}
