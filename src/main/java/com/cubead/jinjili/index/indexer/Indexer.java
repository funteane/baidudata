package com.cubead.jinjili.index.indexer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.cubead.jinjili.domain.model.Indexable;



/**
 *  索引基类
 */
public abstract class Indexer {
	
	protected Logger logger = Logger.getLogger(Indexer.class);
	
	private IndexerManager indexerManager;
	
	public Indexer(IndexerManager indexerManager){
		this.indexerManager = indexerManager;
	}
	
	public Indexer(){
		
	}

	/**
	 * 建立索引
	 * @param id 				索引ID
	 * @param indexable	索引对象
	 * @param boost			分值
	 * @param updated		是否更新
	 */
	public void index(String field, String value, Indexable  indexable, float boost, boolean updated){
//		logger.info("开始索引");
		Document document = convert2Document(indexable);
//		logger.info(document);
		document.setBoost(boost);
		if (updated) {
			indexerManager.update(document, field, value);
		}else {
			indexerManager.save(document);
		}
//		logger.info("索引完成 ");
	}
	
	/**
	 * 删除索引
	 * @param id	索引ID
	 */
	public void delete(String id){
		indexerManager.delete(id);
	}
	
	
	public List<Indexable> search(Query query, int rows) {
		List<Indexable> indexables = new ArrayList<Indexable>();
		try {
			IndexSearcher searcher = indexerManager.getIndexSearcher();
			logger.info("遍历查询结果");
			TopDocs tds = searcher.search(query, rows);
			for(ScoreDoc sd : tds.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				Indexable indexable = convert2Indexable(document);
				indexables.add(indexable);
//				System.out.println(doc.get("id")+"---->"+
//						doc.get("name")+"["+doc.get("email")+"]-->"+doc.get("id")+","+
//						doc.get("attach")+","+doc.get("date"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			try {
				indexerManager.releaseSearch();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return indexables;
	}
	
	public List<Document> searchDoc(Query query, int rows) {
		List<Document> documents = new ArrayList<Document>();
		try {
			IndexSearcher searcher = indexerManager.getIndexSearcher();
			TopDocs tds = searcher.search(query, rows);
			for(ScoreDoc sd : tds.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				documents.add(document);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			try {
				indexerManager.releaseSearch();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return documents;
	}
	
	public abstract  Document convert2Document(Indexable  indexable);
	
	public abstract Indexable convert2Indexable(Document document);

	public IndexerManager getIndexerManager() {
		return indexerManager;
	}


//	public IndexSearcher getIndexSearcher() {
//		return indexSearcher;
//	}
//
//
//	public void setIndexSearcher(IndexSearcher indexSearcher) {
//		this.indexSearcher = indexSearcher;
//	}
//	
	
	

}
