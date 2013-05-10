package com.cubead.jinjili.index.indexer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;

public class CommonWriter extends IndexWriter {

	private static Log log = LogFactory.getLog(CommonWriter.class);
	
	private final AtomicInteger refcount = new AtomicInteger();
	
	public CommonWriter(Directory d, IndexWriterConfig conf) throws CorruptIndexException, LockObtainFailedException, IOException {
		super(d, conf);
		refcount.incrementAndGet();
	}
	
	/**
	 * as this is not a synchronized method, you should synchronize it by yourself. 
	 */
	public int closeWriterIfNecessary(String tenantId) throws IOException {
		if (refcount.decrementAndGet() == 0) {
			log.info(tenantId + " close actual indexReader!!!");
			try{
				super.close();
			}catch(Exception e){
				log.error("an error happen while closing an IndexWriter.", e);
				throw new RuntimeException(e);
			}
		}
		return refcount.get();
	}
	
	public void incRef(){
		refcount.incrementAndGet();
	}
	
	public int getRefcount(){
		return refcount.get();
	}
}
