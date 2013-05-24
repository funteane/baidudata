package com.cubead.jinjili.job.manual;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.domain.service.ICommonInfoService;
import com.cubead.jinjili.index.indexer.IndexMangerFactory;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.index.indexer.IndexerManager;
import com.cubead.jinjili.index.indexer.KeywordIndexer;
import com.cubead.jinjili.index.indexer.RoiIndexer;
import com.cubead.jinjili.index.searcher.DataSearcher;
import com.cubead.jinjili.report.ReportManager;
import com.cubead.jinjili.util.Tools;


@Service
public class MakeReportManualJob {
	
	private static Logger logger = Logger.getLogger(MakeReportManualJob.class);
	
	@Autowired
	private IBaiduAccountService baiduAccountService;
	
	@Autowired
	private ICommonInfoService commonInfoService;
	
	@Autowired
	private DataSearcher dataSearcher;
	
	private Date startDate;
	
	private Date endDate;
	
	public void makeKeywordDailyReport(){
		Map<String, String> commonInfoMap = ReportManager.getCommonInfoMap(commonInfoService);
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
	
		String fileName = Tools.getNormalDateString(endDate).concat("_").concat(IndexType.KEYWORD.name()).concat(".xlsx");
		
		try {
			
			ReportManager.createWorkbook(fileName);
			for (BaiduAccount baiduAccount : accounts) {
				IndexerManager indexerManager = IndexMangerFactory.getIndexerManager(IndexType.SEARCHROI, baiduAccount.getAccountId());
//						ApplicationProperties.getProproperty("base.keyword.roi.index").concat(baiduAccount.getAccountId()));
//				IndexReader indexReader = IndexReader.open(FSDirectory.open(new File(ApplicationProperties.getProproperty("base.keyword.roi.index").concat(baiduAccount.getAccountId()))));
//				IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			
				Indexer roiIndexer = new RoiIndexer(indexerManager);
//				roiIndexer.setIndexSearcher(indexSearcher);
			
				dataSearcher.setRoiIndexer(roiIndexer);
			
				List<Indexable> indexables = dataSearcher.getRoiData(RoiType.KEYWORD, startDate, endDate);
				indexerManager.releaseSearch();
				
				indexerManager = IndexMangerFactory.getIndexerManager(IndexType.SEARCHKEYWORD, baiduAccount.getAccountId());
//						ApplicationProperties.getProproperty("base.whole.account.index.dir").concat(baiduAccount.getAccountId()));
//				IndexReader indexReader2 = IndexReader.open(FSDirectory.open(new File(ApplicationProperties.getProproperty("base.whole.account.index.dir").concat(baiduAccount.getAccountId()))));
//				IndexSearcher indexSearcher2 = new IndexSearcher(indexReader2);
//				Thread.sleep(10000);
//				Indexer keywordIndexer = new KeywordIndexer(indexerManager);
				Indexer keywordIndexer = new KeywordIndexer(indexerManager);
//				keywordIndexer.setIndexSearcher(indexSearcher2);
				dataSearcher.setRoiIndexer(keywordIndexer);
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
				indexerManager.releaseSearch();	
				
				ReportManager.create07ExcelFile(fileName, indexables, indexableMap, commonInfoMap);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	

}
