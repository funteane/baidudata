package com.cubead.jinjili.job.schedule;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.ApplicationProperties;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.domain.service.ICommonInfoService;
import com.cubead.jinjili.index.indexer.IndexDirectoryFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.RoiIndexer;
import com.cubead.jinjili.index.searcher.DataSearcher;
import com.cubead.jinjili.job.AbstractJob;
import com.cubead.jinjili.report.ReportManager;
import com.cubead.jinjili.util.Tools;

@Service
public class KeywordDailyReportJob extends AbstractJob{
	
	@Autowired
	private IBaiduAccountService baiduAccountService;
	
	@Autowired
	private ICommonInfoService commonInfoService;
	
	@Autowired
	private DataSearcher dataSearcher;

	@Override
	public void execute() {
		
		if (!enable) {
			return;
		}
		
		
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
		
		int rows = 0;
		for (BaiduAccount baiduAccount : accounts) {
//			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path.concat(baiduAccount.getAccountId())));
//			path = ApplicationProperties.getProproperty("base.account.roi.index");
//			indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path.concat(baiduAccount.getAccountId())));
//			path = ApplicationProperties.getProproperty("base.plan.roi.index");
//			indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path.concat(baiduAccount.getAccountId())));
//			path = ApplicationProperties.getProproperty("base.unit.roi.index");
//			indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path.concat(baiduAccount.getAccountId())));
			
			try {
//				path = ApplicationProperties.getProproperty("base.keyword.roi.index");
//				indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path.concat(baiduAccount.getAccountId())));
//				Thread.sleep(1000);
				IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.SEARCHROI, baiduAccount.getAccountId());
				Indexer roiIndexer = new RoiIndexer(indexerManager);
				
				dataSearcher.setRoiIndexer(roiIndexer);
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);
				Date endDate = calendar.getTime();
				Date startDate = calendar.getTime();
				List<Indexable> indexables = dataSearcher.getRoiData(RoiType.KEYWORD, startDate, endDate);
				
				indexerManager = IndexMangerFactory.getIndexerManager(IndexType.SEARCHKEYWORD, baiduAccount.getAccountId());
				Indexer keywordIndexer = new KeywordIndexer(indexerManager);
				dataSearcher.setRoiIndexer(keywordIndexer);
//				Thread.sleep(1000);
				logger.warn("max 1024, greater than 1024 must query more");
				
				Map<String, Indexable> indexableMap = new HashMap<String, Indexable>();
				int length = indexables.size();
				
				int counter = length / 1000;
				for (int i = 0; i < counter + 1; i++) {
					int start = 1000 * i;
					int end = 1000 * (i + 1) > length ? length : (1000 * (i + 1));
 					Query query = dataSearcher.createBooleanQuery(indexables.subList(start,end ), IndexType.KEYWORD);
					
					indexableMap.putAll(dataSearcher.getIndexableMap(query, IndexType.KEYWORD));
					
				}
				
				
				Map<String, String> commonInfoMap = ReportManager.getCommonInfoMap(commonInfoService);

				String fileName = Tools.getNormalDateString(calendar.getTime()).concat("_").concat(IndexType.KEYWORD.name()).concat(".xlsx");
			
				ReportManager.create07ExcelFile(fileName, indexables, indexableMap, commonInfoMap, rows);
				
				rows += length; //累计行数
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
	}

}
