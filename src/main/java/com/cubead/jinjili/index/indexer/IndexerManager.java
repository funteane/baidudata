package com.cubead.jinjili.index.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexerManager {
	
	protected static Logger logger = Logger.getLogger(IndexerManager.class);
	
	private Directory directory = null;
	private NRTManager nrtManager = null;
	private IndexWriter indexWriter = null;
	private TrackingIndexWriter trackingIndexWriter;
	private Analyzer analyzer;
	
	public IndexerManager(String indexPath, Analyzer analyzer){
		try {
			init(indexPath, analyzer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
	}
	
	@Deprecated
	public IndexerManager(){
		try {
			analyzer = new StandardAnalyzer(Version.LUCENE_36);
			init("D:/temp/index/account/2466159/", analyzer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public IndexerManager(String indexPath){
		try {
			analyzer = new StandardAnalyzer(Version.LUCENE_36);
			init(indexPath, analyzer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
	}

	private void init(String indexPath, Analyzer analyzer) throws Exception {
		this.analyzer = analyzer;
		directory = FSDirectory.open(new File(indexPath));
		indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
		trackingIndexWriter = new TrackingIndexWriter(indexWriter);
		SearcherFactory searcherFactory = new SearcherFactory();
		nrtManager  = new NRTManager(trackingIndexWriter, searcherFactory);
		NRTManagerReopenThread nrtManagerReopenThread = new NRTManagerReopenThread(nrtManager, 0.5, 0.0025);
//			nrtManagerReopenThread.setName("JJL");
		nrtManagerReopenThread.start();
	}
	
	
	public IndexSearcher getIndexSearcher() {
		return nrtManager.acquire();
	}
	
	/**
	 * 根据索引Id删除索引
	 * @param id
	 */
	public void delete(String id) {
		try {
			trackingIndexWriter.deleteDocuments(new Term("id", id));
//			ntm.maybeReopen(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
	}
	
	public void delete(Query... queries){
		try {
			trackingIndexWriter.deleteDocuments(queries);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	
	
	/**
	 * 更新索引
	 * @param document
	 * @param id
	 */
	public void update(Document document, String field, String value) {
		try {
			trackingIndexWriter.updateDocument(new Term(field, value), document);
//			indexWriter.commit();
//			nrtManager.maybeReopen(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
	}
	
	/**
	 * 保存索引
	 * @param document
	 */
	public void save(Document document){
		System.out.println(document);
		System.out.println(trackingIndexWriter);
		logger.info("加入文档");
		try {
			trackingIndexWriter.addDocument(document);
//			indexWriter.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void release(IndexSearcher searcher) throws Exception{
		nrtManager.release(searcher);
	}
	
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public NRTManager getNrtManager() {
		return nrtManager;
	}

	public void release(){
		try {
			indexWriter.commit();
			if(IndexWriter.isLocked(directory)){
				logger.info("try to close this indexwriter");
				
				indexWriter.close();  
				IndexWriter.unlock(directory);  
			}  
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}

	}
	
	public void addDirectory(Directory... directories){
		try {
			trackingIndexWriter.addIndexes(directories);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


}
