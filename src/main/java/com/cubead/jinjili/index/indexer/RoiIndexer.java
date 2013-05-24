package com.cubead.jinjili.index.indexer;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.util.Tools;

public class RoiIndexer extends Indexer{

	public RoiIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	public RoiIndexer() {
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		Document document = new Document();
		RoiModel roiModel = (RoiModel) indexable;
		
		document.add(new Field("createDate", Tools.getSimpleDateString(roiModel.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED));

		document.add(new Field("accountId", roiModel.getAccountId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		document.add(new Field("accountName", roiModel.getAccountName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		if (!Tools.empty(roiModel.getPlanId())) {
			document.add(new Field("planId", roiModel.getPlanId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (!Tools.empty(roiModel.getPlanName())) {
			document.add(new Field("planName", roiModel.getPlanName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (!Tools.empty(roiModel.getUnitId())) {
			document.add(new Field("unitId", roiModel.getUnitId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (!Tools.empty(roiModel.getUnitName())) {
			document.add(new Field("unitName", roiModel.getUnitName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (!Tools.empty(roiModel.getKeywordId())) {
			document.add(new Field("keywordId", roiModel.getKeywordId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (!Tools.empty(roiModel.getKeyword())) {
			document.add(new Field("keyword", roiModel.getKeyword(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		document.add(new Field("platform", roiModel.getPlatform(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		document.add(new NumericField("impression", Field.Store.YES, true).setLongValue(Long.valueOf(roiModel.getImpression())));
		document.add(new NumericField("click", Field.Store.YES, true).setLongValue(Long.valueOf(roiModel.getClick())));
		document.add(new NumericField("cost", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getCost())));
		document.add(new NumericField("ctr", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getCtr())));
		document.add(new NumericField("cpc", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getCpc())));
		document.add(new NumericField("cpm", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getCpm())));
		if (!Tools.empty(roiModel.getConversion())) {
			document.add(new NumericField("conversion", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getConversion())));
		}
		if (!Tools.empty(roiModel.getAvgRank())) {
			document.add(new NumericField("avgRank", Field.Store.YES, true).setDoubleValue(Double.valueOf(roiModel.getAvgRank())));
		}
		
		document.add(new Field("roiType", roiModel.getRoiType().getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		if (roiModel.getRoiType() == RoiType.ACCOUNT) {
			document.add(new Field("id", Tools.generateDocId(roiModel.getAccountId(), roiModel.getRoiType().name(), 
					Tools.getSimpleDateString(roiModel.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		}else if (roiModel.getRoiType() == RoiType.PLAN) {
			document.add(new Field("id", Tools.generateDocId(roiModel.getPlanId(), roiModel.getRoiType().name(), 
					Tools.getSimpleDateString(roiModel.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		}else if (roiModel.getRoiType() == RoiType.UNIT) {
			document.add(new Field("id", Tools.generateDocId(roiModel.getUnitId(), roiModel.getRoiType().name(), 
					Tools.getSimpleDateString(roiModel.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		}else if (roiModel.getRoiType() == RoiType.KEYWORD) {
			document.add(new Field("id", Tools.generateDocId(roiModel.getKeywordId(), roiModel.getRoiType().name(), 
					Tools.getSimpleDateString(roiModel.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		}
		
		return document;
	}

	@Override
	public Indexable convert2Indexable(Document document) {
		RoiModel roiModel = new RoiModel();
		
		roiModel.setRoiType(RoiType.valueOf(document.get("roiType")));
		
		roiModel.setCreateDate(Tools.getSimpleDate(document.get("createDate")));
		roiModel.setAccountId(document.get("accountId"));
		roiModel.setAccountName(document.get("accountName"));
		roiModel.setPlanId(document.get("planId"));
		roiModel.setPlanName(document.get("planName"));
		roiModel.setUnitId(document.get("unitId"));
		roiModel.setUnitName(document.get("unitName"));
		roiModel.setKeywordId(document.get("keywordId"));
		roiModel.setKeyword(document.get("keyword"));
		roiModel.setQuality(document.get("quality"));
		roiModel.setPlatform(document.get("platform"));
		roiModel.setClick(Long.parseLong(document.get("click")));
		roiModel.setImpression(Long.parseLong(document.get("impression")));
		roiModel.setCost(Double.parseDouble(document.get("cost")));
		roiModel.setCpc(Double.parseDouble(document.get("cpc")));
		roiModel.setCpm(Double.parseDouble(document.get("cpm")));
		roiModel.setCtr(Double.parseDouble(document.get("ctr")));
		roiModel.setAvgRank(document.get("avgRank") == null ? null : Double.valueOf(document.get("avgRank")));
		roiModel.setConversion(document.get("conversion") == null ? null : Double.valueOf(document.get("conversion")));

//		roiModel.setIndexType(IndexType.valueOf(document.get("indexType")));
		
		roiModel.setKey(document.get("id"));
		
		return roiModel;
	}

}
