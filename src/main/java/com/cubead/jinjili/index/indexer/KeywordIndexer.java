package com.cubead.jinjili.index.indexer;

import java.util.Calendar;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Tools;

public class KeywordIndexer extends Indexer{

	public KeywordIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		KeywordModel keyword = (KeywordModel) indexable;
		Document document = new Document();
		
		Field field = new Field("keywordId", keyword.getKeywordId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		if (Tools.empty(keyword.getPlanId())) {
			document.add(new Field("planId", keyword.getPlanId(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
		}

		if (!Tools.empty(keyword.getQuality()) && keyword.getQuality() > 0) {
			document.add(new NumericField("quality", Field.Store.YES, true).setIntValue(keyword.getQuality()));
		}
		
		field = new Field("unitId", keyword.getUnitId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		field = new Field("keyword", keyword.getKeyword(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		field = new Field("linkUrl", keyword.getLinkUrl(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		document.add(new NumericField("price", Field.Store.YES, true).setFloatValue(keyword.getPrice()));
		
		field = new Field("pause", String.valueOf(keyword.getPause()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("status", keyword.getStatus(), Field.Store.YES ,Field.Index.ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("bidding_strategy", keyword.getBidding_strategy(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("matchType", keyword.getMatchType(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("deleted", keyword.getDeleted(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);

	    document.add(new Field("createDate", Tools.getSimpleDateString(keyword.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		document.add(new Field("indexType", keyword.getIndexType().name(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		document.add(new Field("id", Tools.generateDocId(keyword.getKeywordId(), keyword.getIndexType().name(), 
				Tools.getSimpleDateString(keyword.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		return document;
	}
	
	@Override
	public Indexable convert2Indexable(Document document) {
		KeywordModel keywordModel = new KeywordModel();
		keywordModel.setKeywordId(document.get("keywordId"));
		keywordModel.setKeyword(document.get("keyword"));
		keywordModel.setUnitId(document.get("unitId"));
		keywordModel.setLinkUrl(document.get("linkUrl"));
		keywordModel.setMatchType(document.get("matchType"));
		keywordModel.setPause(document.get("pause") == null ? false : Boolean.valueOf(document.get("pause")));
		keywordModel.setStatus(document.get("status"));
		keywordModel.setBidding_strategy(document.get("bidding_strategy"));
		keywordModel.setPrice(document.get("price") == null ? null : Float.valueOf(document.get("price")));
		keywordModel.setQuality(document.get("quality") == null ? null : Integer.parseInt(document.get("quality")));
		keywordModel.setPlanId(document.get("planId"));
		keywordModel.setDeleted(document.get("deleted"));
		
		keywordModel.setCreateDate(Tools.getSimpleDate(document.get("createDate")));
	
//		keywordModel.setIndexType(IndexType.valueOf(document.get("indexType")));
		keywordModel.setKey(document.get("id"));
		
		return keywordModel;
	}

}
