package com.cubead.jinjili.download.task;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.model.TaskType;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.RoiIndexer;
import com.cubead.jinjili.index.parser.CsvFileParser;
import com.cubead.jinjili.index.parser.RoiCsvFileParser;
import com.cubead.jinjili.util.Constants;
import com.cubead.jinjili.util.Tools;
import com.cubead.searchapi.client.ClientManager;
import com.cubead.searchapi.client.ISearchApiClient;
import com.cubead.searchapi.client.data.ApiContext;

public class RoiDownloadTask extends DownloadTask{
	
	private static Map<String, String> roiDownloadTypeMap = new HashMap<String, String>();
	static{
		roiDownloadTypeMap.put(TaskType.DOWNLOAD_ACCOUNTROI.getName(), "2");
		roiDownloadTypeMap.put(TaskType.DOWNLOAD_PLANROI.getName(), "10");
		roiDownloadTypeMap.put(TaskType.DOWNLOAD_UNITROI.getName(), "11");
		roiDownloadTypeMap.put(TaskType.DOWNLOAD_KEYWORDROI.getName(), "14");
	}

	public RoiDownloadTask(DownloadContext downloadContext, TaskType taskType) {
		super(downloadContext);
		downloadContext.getTaskModel().setTaskType(taskType);
		
	}

	@Override
	protected void processDownload(DownloadContext downloadContext)	throws Exception {
		logger.info("begion to download roi " + downloadContext.getTaskModel().getTaskType().getName());
		
		String filePath = getRoiDownloadUrl(downloadContext);
		downloadContext.getFilePathMap().put(downloadContext.getTaskModel().getTaskType().getName(), filePath);
		logger.info("/////////////////////////////////////");
		logger.info(filePath);
	}

	private String getRoiDownloadUrl(DownloadContext downloadContext) throws Exception {
		ApiContext apiContext = downloadContext.getApiContext();
		ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
		String downloadUrl = searchApiClient.getrReportManager().requestReportDownloadUrl(downloadContext.getTaskModel().getFileId());
		String downloadPath = Constants.WHOLEACCOUNT_DOWNLOAD_PATH;//ConfigUtil.getBaiduWholeAccountFolder((String)setting.get("tenantId")) + "/";
		File path = new File(downloadPath);
		
		if (!path.exists()){
			path.mkdirs();
		}

		logger.info(downloadPath.concat(downloadContext.getTaskModel().getFileId()));
		
		downloadFile(downloadPath.concat(downloadContext.getTaskModel().getFileId()), downloadUrl);
		
		return downloadPath.concat(downloadContext.getTaskModel().getFileId());
	}
	
	@Override
	protected String requestFileId(DownloadContext downloadContext) {
		ApiContext apiContext = downloadContext.getApiContext(); 
		try {
			ISearchApiClient searchApiClient = ClientManager.getSearchApiClient(apiContext);
			String roiDownloadType = roiDownloadTypeMap.get(downloadContext.getTaskModel().getTaskType().getName());
			logger.info(roiDownloadType);
			
			String fileId = searchApiClient.getrReportManager().requestReportId(downloadContext.getStartDate(), 
					downloadContext.getEndDate(), roiDownloadType);
			return fileId;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.requestFileId(downloadContext);
	}

	@Override
	protected void processIndex(DownloadContext downloadContext)	throws Exception {
		Map<String, String> filePathMap = downloadContext.getFilePathMap();
		Set<String> keys = filePathMap.keySet();
		for (String key : keys) {
			String path = filePathMap.get(key);
			System.out.println(path);
			TaskModel taskModel = downloadContext.getTaskModel();
			RoiType roiType = RoiType.PLAN;
			IndexType indexType = IndexType.PLANROI;
			switch (taskModel.getTaskType()) {
			case DOWNLOAD_ACCOUNTROI:
				roiType = RoiType.ACCOUNT;
				indexType = IndexType.ACCOUNTROI;
				break;
			case DOWNLOAD_PLANROI:
				roiType = RoiType.PLAN;
				indexType = IndexType.PLANROI;
				break;
			case DOWNLOAD_UNITROI:
				roiType = RoiType.UNIT;
				indexType = IndexType.UNITROI;
				break;
			case DOWNLOAD_KEYWORDROI:
				roiType = RoiType.KEYWORD;
				indexType = IndexType.KEYWORDROI;
				break;				
			}
			
			IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(indexType, downloadContext.getAccountId());
			CsvFileParser csvFileParser = new RoiCsvFileParser(path, Calendar.getInstance().getTime());
			Indexer indexer = new RoiIndexer(indexerManager);
			while(csvFileParser.hasNext()){
				Indexable indexable = csvFileParser.nextIndexable();
				RoiModel roiModel = (RoiModel) indexable;
				roiModel.setRoiType(roiType);
				String id;
				if (roiModel.getRoiType() == RoiType.ACCOUNT) {
					id = Tools.generateDocId(roiModel.getAccountId(), roiModel.getRoiType().name(), Tools.getSimpleDateString(roiModel.getCreateDate()));
					indexer.index("id", id, indexable, 1.0f, false);
				}else if (roiModel.getRoiType() == RoiType.PLAN) {
					id = Tools.generateDocId(roiModel.getPlanId(), roiModel.getRoiType().name(), 	Tools.getSimpleDateString(roiModel.getCreateDate()));
					indexer.index("id", id, indexable, 1.0f, false);
				}else if (roiModel.getRoiType() == RoiType.UNIT) {
					id = Tools.generateDocId(roiModel.getUnitId(), roiModel.getRoiType().name(), Tools.getSimpleDateString(roiModel.getCreateDate()));
					indexer.index("id", id, indexable, 1.0f, false);
				}else if (roiModel.getRoiType() == RoiType.KEYWORD) {
					id = Tools.generateDocId(roiModel.getKeywordId(), roiModel.getRoiType().name(), Tools.getSimpleDateString(roiModel.getCreateDate()));
					indexer.index("id", id, indexable, 1.0f, false);
				}
				
			}		
			indexerManager.release();
		}
	}
}
