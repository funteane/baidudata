package com.cubead.jinjili.index.indexer;

import com.cubead.jinjili.common.ApplicationProperties;


public  class IndexMangerFactory {
	
	
	public static IndexerManager getIndexerManager(IndexType indexType, String id){
		String path = "";
//		Analyzer analyzer;
		IndexerManager indexerManager;
		
		switch (indexType) {
		case ACCOUNT:
			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			path = "d:/temp/index/account/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case PLAN:
			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			path = "d:/temp/index/account/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case UNIT:
			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			path = "d:/temp/index/account/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case KEYWORD:
			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			path = "d:/temp/index/account/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case ACCOUNTROI:
			path = ApplicationProperties.getProproperty("base.account.roi.index");
//			path = "d:/temp/index/roi/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case PLANROI:
			path = ApplicationProperties.getProproperty("base.plan.roi.index");
//			path = "d:/temp/index/roi/";
			indexerManager = new IndexerManager(path.concat(id));
			break;	
		case UNITROI:
			path = ApplicationProperties.getProproperty("base.unit.roi.index");
//			path = "d:/temp/index/roi/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case KEYWORDROI:
			path = ApplicationProperties.getProproperty("base.keyword.roi.index");
//			path = "d:/temp/index/roi/";
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case QUALITY:
			path = ApplicationProperties.getProproperty("base.quality.index.dir");
			indexerManager = new IndexerManager(path.concat(id));
			break;
		case SEARCHACCOUNT:
		case SEARCHPLAN:
		case SEARCHUNIT:
		case SEARCHKEYWORD:			
			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
			indexerManager = new IndexerManager(path.concat(id));
//			path = "d:/temp/index/account/";
//			indexerManager.addDirectory(IndexDirectoryFactory.getDirectory(path));
//			path = ApplicationProperties.getProproperty("base.whole.account.index.dir");
//			path = ApplicationProperties.getProproperty("base.account.roi.index");
//			path = ApplicationProperties.getProproperty("base.plan.roi.index");
//			path = ApplicationProperties.getProproperty("base.unit.roi.index");
//			path = ApplicationProperties.getProproperty("base.keyword.roi.index");
//			path = ApplicationProperties.getProproperty("base.default.index");
			break;
		case SEARCHROI:	
			path = ApplicationProperties.getProproperty("base.keyword.roi.index");
			indexerManager = new IndexerManager(path.concat(id));
			break;
		default:
			path = ApplicationProperties.getProproperty("base.default.index");
			indexerManager = new IndexerManager();
			break;
		}
		
		System.out.println(path);
		return indexerManager;
	}
	
	
	
	
	
	public enum IndexType{
		ACCOUNT,
		PLAN,
		UNIT,
		KEYWORD,
		ACCOUNTROI,
		PLANROI,
		UNITROI,
		KEYWORDROI,
		QUALITY,
		SEARCHACCOUNT,
		SEARCHPLAN,
		SEARCHUNIT,
		SEARCHKEYWORD,
		SEARCHROI
	}
	

}
