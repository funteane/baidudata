package com.cubead.jinjili.index.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.QualityModel;

public class QualityIndexer extends Indexer{

	public QualityIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		Document document = new Document();

		QualityModel qualityModel = (QualityModel) indexable;
		document.add(new NumericField("quality", Field.Store.YES, true).setIntValue(qualityModel.getQuality()));
		Field field = new Field("planId", qualityModel.getPlanId(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		field = new Field("unitId", qualityModel.getUnitId(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		field = new Field("keywordId", qualityModel.getKeywordId(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		return document;
	}

	@Override
	public Indexable convert2Indexable(Document document) {
//		
//		KeywordModel keywordModel = new KeywordModel();
//		keywordModel.setKeywordId(document.get("keywordId"));
//		keywordModel.setKeyword(document.get("keyword"));
//		keywordModel.setUnitId(document.get("unitId"));
//		keywordModel.setLinkUrl(document.get("linkUrl"));
//		keywordModel.setMatchType(document.get("matchType"));
//		keywordModel.setPause(document.get("pause") == null ? false : Boolean.valueOf(document.get("pause")));
//		keywordModel.setStatus(document.get("status"));
//		keywordModel.setBidding_strategy(document.get("bidding_strategy"));
//		keywordModel.setPrice(document.get("price") == null ? null : Float.valueOf(document.get("price")));
//		keywordModel.setQuality(document.get("quality") == null ? null : Integer.parseInt(document.get("quality")));
//		keywordModel.setPlanId(document.get("planId"));
//		
//		return keywordModel;
		
		QualityModel qualityModel = new QualityModel();

		qualityModel.setKeywordId(document.get("keywordId"));
		qualityModel.setPlanId(document.get("planId"));
		qualityModel.setUnitId(document.get("unitId"));
		qualityModel.setQuality(Integer.parseInt(document.get("quality")));
		
		return qualityModel;
	}
	
//	@Override
//	public void index(String field, String value, Indexable indexable, float boost,	boolean updated) {
//		Document document = convert2Document(indexable);
//		
//		Query query = new TermQuery(new Term(field, value));
////		new MultiFieldQueryParser(Version.LUCENE_35, new String[]{"unitId","keywordId"}, getIndexerManager().getAnalyzer());
//		List<Document> documents = searchDoc(query, 10);
//		if (!Tools.empty(documents)) {
//			Document keywordDocument = documents.get(0);
//			keywordDocument.add(document.getFieldable("quality"));
//			keywordDocument.add(document.getFieldable("planId"));
//			
//		    getIndexerManager().update(keywordDocument, field, value);
//			
//		}
//		
//	}

}
