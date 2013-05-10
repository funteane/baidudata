package com.cubead.jinjili.index.task;

import org.apache.lucene.index.IndexWriter;

import com.cubead.jinjili.domain.model.IndexLogModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.index.indexer.IndexerManager;

public class IndexContext {
	
	private IndexLogModel indexLogModel;
	
	private Indexable indexable;
	
	private IndexWriter indexWriter;
	
	private IndexerManager indexerManager;

	
	
	public IndexLogModel getIndexLogModel() {
		return indexLogModel;
	}

	public void setIndexLogModel(IndexLogModel indexLogModel) {
		this.indexLogModel = indexLogModel;
	}

	public Indexable getIndexable() {
		return indexable;
	}

	public void setIndexable(Indexable indexable) {
		this.indexable = indexable;
	}

	public IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		this.indexWriter = indexWriter;
	}

	public IndexerManager getIndexerManager() {
		return indexerManager;
	}

	public void setIndexerManager(IndexerManager indexerManager) {
		this.indexerManager = indexerManager;
	}
	

		
	

}
