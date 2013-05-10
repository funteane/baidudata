package com.cubead.jinjili.index.indexer;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public final class IndexDirectoryFactory {
	
	private static Logger logger = Logger.getLogger(IndexDirectoryFactory.class);
	
	public static Directory getDirectory(String dirPath){
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(dirPath));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return directory;
	}
	

}
