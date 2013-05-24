package com.cubead.jinjili.index.indexer;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexerManager {
	
	protected static Logger logger = Logger.getLogger(IndexerManager.class);
	
	private Directory directory = null;
	private IndexWriter indexWriter = null;
	private IndexSearcher indexSearcher;
	private Analyzer analyzer;
	
	private String indexPath;
	
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
			init("D:/temp/index/account/6099124/", analyzer);
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
//		directory = FSDirectory.open(new File(indexPath));
		this.indexPath = indexPath;
	}
	

	
	/**
	 * 根据索引Id删除索引
	 * @param id
	 */
	public void delete(String id) {
		try {
			getIndexWriter().deleteDocuments(new Term("id", id));
//			indexWriter.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
	}
	
	public void delete(Query... queries){
		try {
			getIndexWriter().deleteDocuments(queries);
		} catch (Exception e) {
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
			getIndexWriter().updateDocument(new Term(field, value), document);
//			indexWriter.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
	}
	
	/**
	 * 保存索引
	 * @param document
	 */
	public void save(Document document){
//		System.out.println(document);
//		logger.info("加入文档");
		try {
			getIndexWriter().addDocument(document);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void releaseWriter(){
		try {
//			Directory directory =  FSDirectory.open(new File(indexPath));
			if (directory == null) {
				return;
			}
			logger.info("try to close this indexwriter");
			if (indexWriter != null) {
				indexWriter.commit();
				indexWriter.close();  
			}
			
//			System.out.println(directory);
			if(IndexWriter.isLocked(directory)){
				IndexWriter.unlock(directory);  
			}
			directory = null;
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
		}

	}
	
	public void releaseSearch(){
		try {
			if (indexSearcher != null) {
				indexSearcher.close();
			}
			directory = null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}	
	
	public void addDirectory(Directory... directories){
		try {
			indexWriter.addIndexes(directories);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public IndexSearcher getIndexSearcher() throws Exception {
		if (indexSearcher == null ) {
			directory = FSDirectory.open(new File(indexPath));
			indexSearcher = new IndexSearcher(IndexReader.open(directory));
		}
		return indexSearcher;
	}

	public IndexWriter getIndexWriter() throws Exception {
		if (indexWriter == null) {
			directory = FSDirectory.open(new File(indexPath));
			System.err.println(directory);
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
		}
		return indexWriter;
	}
	
	
	
	


}
