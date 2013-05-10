package com.cubead.jinjili.index.indexer;

import java.util.Calendar;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.util.Tools;


/**
 * for the whole account index or increment account index
 *
 */
public class QualityUpdateIndexer extends Indexer {

	public QualityUpdateIndexer(IndexerManager indexerManager) {
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
		
		document.add(new NumericField("createDate", Field.Store.YES, true).setLongValue(Calendar.getInstance().getTime().getTime()));
		document.add(new Field("indexType", keyword.getIndexType().name(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		return document;
	}

	@Override
	public Indexable convert2Indexable(Document document) {
		return null;
	}
	
	@Override
	public void index(String field, String value, Indexable indexable,	float boost, boolean updated) {
//		Document document = convert2Document(indexable);
		KeywordModel keywordModel = (KeywordModel) indexable;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
//			Query query = MultiFieldQueryParser.parse(Version.LUCENE_36, 
//					new String[]{keywordModel.getKeywordId(), keywordModel.getIndexType().name(), 
//					Tools.getDateString(calendar.getTime())}, 
//					new String[]{"keywordId", "roiType", "createDate"}, new KeywordAnalyzer());
			
			Query query  = new TermQuery(new Term("id", Tools.generateDocId(keywordModel.getKeywordId(), 
					keywordModel.getIndexType().name(), 
					Tools.getSimpleDateString(keywordModel.getCreateDate()) )));
			
			List<Document> documents = searchDoc(query, 10);
			
			if (!Tools.empty(documents)) {
				Document roiDocument = documents.get(0);
				logger.info(roiDocument);
				if (!Tools.empty(keywordModel.getQuality()) && keywordModel.getQuality() > 0) {
					roiDocument.add(new NumericField("quality", Field.Store.YES, true).setIntValue(keywordModel.getQuality()));
				}
				
//				logger.info("删除");
//				getIndexerManager().delete(query);
//				logger.info("增加");
			    getIndexerManager().update(roiDocument, "id", roiDocument.get("id"));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}

}
