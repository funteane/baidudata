package com.cubead.jinjili.index.searcher;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.index.indexer.Indexer;
import com.cubead.jinjili.util.Tools;

@Service
public class DataSearcher {

	
	private Indexer indexer;
	
	public  List<Indexable> getRoiData(RoiType roiType, Date startDate, Date endDate) throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(Tools.getSimpleDateString(startDate)).append(" TO ").append(Tools.getSimpleDateString(endDate)).append("]");
		
		try {
			Thread.sleep(1000);
			Query keywordQuery = MultiFieldQueryParser.parse(Version.LUCENE_36, 
					new String[]{roiType.name(), sb.toString()}, 
					new String[]{"roiType", "createDate"}, 
					new StandardAnalyzer(Version.LUCENE_36));
			List<Indexable> indexables = indexer.search(keywordQuery, Integer.MAX_VALUE);
			
			System.out.println(indexables.size());
//			List<String> fields = new ArrayList<String>();
//			List<String> values = new ArrayList<String>();
//			List<Occur> occurs = new ArrayList<Occur>();
//			for (Indexable keyword : rois) {
//				RoiModel roiModel = (RoiModel) keyword;
//				fields.add("keywordId");
//				values.add(roiModel.getKeywordId());
//				occurs.add(Occur.SHOULD);
//			}
			
//			System.out.println(fields);
//			System.err.println(values);
//			System.err.println(occurs);
//			
			
			return indexables;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败");
		}
		
		
	}
	
	public Map<String, Indexable> getIndexableMap(Query query, IndexType indexType) throws Exception{
		Map<String, Indexable> indexableMap = new HashMap<String, Indexable>();
		List<Indexable> indexables = indexer.search(query, Integer.MAX_VALUE);
		for (Indexable indexable : indexables) {
			if (indexType == IndexType.PLAN) {
				PlanModel planModel = (PlanModel) indexable;
				indexableMap.put(planModel.getPlanId(), planModel);
			}else if (indexType == IndexType.UNIT) {
				UnitModel unitModel = (UnitModel) indexable;
				indexableMap.put(unitModel.getUnitId(), unitModel);
			}else if (indexType == IndexType.KEYWORD) {
				KeywordModel keywordModel = (KeywordModel) indexable;
				indexableMap.put(keywordModel.getKeywordId(), keywordModel);
			}else  { //account
				AccountModel accountModel = (AccountModel) indexable;
				indexableMap.put(accountModel.getAccountId(), accountModel);
			}
		}
		
		return indexableMap;
	}
	
	
	public BooleanQuery createBooleanQuery(List<Indexable> indexables, IndexType indexType){
		BooleanQuery booleanQuery = new BooleanQuery();
		
		String field = "";
		for (Indexable indexable : indexables) {
			RoiModel roiModel = (RoiModel) indexable;
			if (indexType == IndexType.PLAN) {
				field = "planId";
				Term planTerm = new Term(field, roiModel.getPlanId());
				booleanQuery.add(new TermQuery(planTerm), Occur.SHOULD);
			}else if (indexType == IndexType.UNIT) {
				field = "unitId";
				Term unitTerm = new Term(field, roiModel.getUnitId());
				booleanQuery.add(new TermQuery(unitTerm), Occur.SHOULD);
			}else if (indexType == IndexType.KEYWORD) {
				field = "keywordId";
				Term keywordTerm = new Term(field, roiModel.getKeywordId());
				booleanQuery.add(new TermQuery(keywordTerm), Occur.SHOULD);
			}else  { //account
				field = "accountId";
				Term accountTerm = new Term(field, roiModel.getAccountId());
				booleanQuery.add(new TermQuery(accountTerm), Occur.SHOULD);
			}
			
		}
		
		return booleanQuery;
	}
	
	
	

	public Indexer getRoiIndexer() {
		return indexer;
	}

	public void setRoiIndexer(Indexer roiIndexer) {
		this.indexer = roiIndexer;
	}
	
	
	
}
