package com.cubead.jinjili.index.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.cubead.jinjili.common.ApplicationProperties;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;

public class IndexWriterFactory {
	
	public static IndexWriter getIndexWriter(IndexType indexType, String id) throws Exception{
		String path = "";
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		IndexWriter indexWriter;
		Directory directory ;
		
		switch (indexType) {
		case ACCOUNT:
			//path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
			//directory = FSDirectory.open(new File(path.concat(id)));
			//indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			//break;
		case PLAN:
			//path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
			//directory = FSDirectory.open(new File(path.concat(id)));
			//indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			//break;
		case UNIT:
			//path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
			//directory = FSDirectory.open(new File(path.concat(id)));
			//indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			//break;
		case KEYWORD:
			//path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
			//directory = FSDirectory.open(new File(path.concat(id)));
			///indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			//break;
		case ACCOUNTROI:
			path = ApplicationProperties.getProproperty("base.account.roi.index");
			directory = FSDirectory.open(new File(path.concat(id)));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;
		case PLANROI:
//			path = ApplicationProperties.getProproperty("base.plan.roi.index");
			path = "d:/temp/index/roi/";
			directory = FSDirectory.open(new File(path.concat(id)));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;	
		case UNITROI:
			path = ApplicationProperties.getProproperty("base.unit.roi.index");
			directory = FSDirectory.open(new File(path.concat(id)));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;
		case KEYWORDROI:
			path = ApplicationProperties.getProproperty("base.keyword.roi.index");
			directory = FSDirectory.open(new File(path.concat(id)));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;
		case QUALITY:
			path = ApplicationProperties.getProproperty("base.quality.index.dir");
			directory = FSDirectory.open(new File(path.concat(id)));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;
		default:
			path = ApplicationProperties.getProproperty("base.default.index");
			directory = FSDirectory.open(new File(path));
			indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LUCENE_36, analyzer));
			break;
		}
		
		return indexWriter;
	}

}
