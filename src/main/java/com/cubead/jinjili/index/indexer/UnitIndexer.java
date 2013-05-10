package com.cubead.jinjili.index.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Tools;

public class UnitIndexer extends Indexer{

	public UnitIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		UnitModel unit = (UnitModel) indexable;
		Document document = new Document();
		Field field = new Field("planId", unit.getPlanId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
//		field = new Field("planName", unit.getPlanName(), Field.Store.YES ,Field.Index.ANALYZED);
//		field.setBoost(1);
//		document.add(field);
		
		field = new Field("unitId", unit.getUnitId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		field = new Field("unitName", unit.getUnitName(), Field.Store.YES ,Field.Index.ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		field = new Field("pause", String.valueOf(unit.getPause()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("status", unit.getStatus(), Field.Store.YES ,Field.Index.ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("bidding_strategy", String.valueOf(unit.getBidding_strategy()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		document.add(new NumericField("maxPrice", Field.Store.YES, true).setFloatValue(unit.getMaxPrice()));
		
		for(String exactNegativeWord : unit.getExactNegativeWords()){
			field = new Field("exactNegativeWords", exactNegativeWord, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		
		for(String negativeWord : unit.getNegativeWords()){
			field = new Field("negativeWords", negativeWord, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		for(String biddableKeyword : unit.getBiddableKeywords()){
			field = new Field("biddableKeywords", biddableKeyword, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		field = new Field("deleted", unit.getDeleted(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		document.add(new Field("createDate", Tools.getSimpleDateString(unit.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED));

		document.add(new Field("indexType", unit.getIndexType().name(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		document.add(new Field("id", Tools.generateDocId(unit.getUnitId(), unit.getIndexType().name(), 
				Tools.getSimpleDateString(unit.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));

		return document;
	}
	
	
	@Override
	public Indexable convert2Indexable(Document document) {
		UnitModel unitModel = new UnitModel();
		unitModel.setPlanId(document.get("planId"));
		unitModel.setUnitId(document.get("unitId"));
		unitModel.setUnitName(document.get("unitName"));
		unitModel.setBidding_strategy(document.get("bidding_strategy"));
		unitModel.setPause(document.get("pause") == null ? false : Boolean.valueOf(document.get("pause")));
		unitModel.setStatus(document.get("status"));
		unitModel.setBiddableKeywords(document.getValues("biddableKeywords"));
		unitModel.setExactNegativeWords(document.getValues("exactNegativeWords"));
		unitModel.setNegativeWords(document.getValues("negativeWords"));
		unitModel.setMaxPrice(document.get("maxPrice") == null ? null : Float.valueOf(document.get("maxPrice")));
		unitModel.setDeleted(document.get("deleted"));
		
		unitModel.setCreateDate(Tools.getDate(document.get("createDate")));
	
		unitModel.setIndexType(IndexType.valueOf(document.get("indexType")));
		
		unitModel.setKey(document.get("id"));
		
		return unitModel;
	}

}
