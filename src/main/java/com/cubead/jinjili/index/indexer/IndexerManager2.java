package com.cubead.jinjili.index.indexer;
//package com.cubead.jinjili.index;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.index.IndexCommit;
//import org.apache.lucene.index.IndexDeletionPolicy;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig.OpenMode;
//import org.apache.lucene.index.LogByteSizeMergePolicy;
//import org.apache.lucene.index.SnapshotDeletionPolicy;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//import org.springframework.stereotype.Service;
//
//import com.cubead.datacenter.ConfigUtil;
//import com.cubead.datacenter.index.util.TenantIdSynchronizer;
//
//@Service
//public class IndexerManager2 {
//
//	private static Log log = LogFactory.getLog(IndexerManager2.class);
//	
//	private static Map<String, CommonWriter> indexerMap=new HashMap<String, CommonWriter>();
//	private static Map<String, SnapshotDeletionPolicy> snapshotMap=new HashMap<String, SnapshotDeletionPolicy>();
//	
//	//private final ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(1);
//	
////	private boolean backupEnable=false;
//	
//	@PostConstruct
//	public void init(){
//		log.info("indexerManager init...");	
//		
////		if(backupEnable){
////			log.info("backupManager start...");
////			Runnable backupTask=new Runnable() {
////				
////				@Override
////				public void run() {
////					try {
////						for (Entry<String, SnapshotDeletionPolicy> entry : snapshotMap.entrySet()) {
////							String[] keyArray=entry.getKey().split("/");
////							doBackup(keyArray[0],keyArray[1],entry.getValue());
////	                    }
////						
////					} catch (Exception e) {
////						log.error(e.getMessage(),e);
////					}
////					
////				}
////
////				
////			};
////			
////			scheduler.scheduleAtFixedRate(backupTask, 10, 600, TimeUnit.SECONDS);
////		}
//		
//		
//	}
//	
//	public static void backupIndex(String tenantId) throws IOException {
//		String indexDir=ConfigUtil.getIndexDir(tenantId);
//        File indexDirFile=new File(indexDir);
//        if(!indexDirFile.exists())
//        	return;
//        
//		
//        String indexBackupDir=ConfigUtil.getIndexBackupDir(tenantId);
//    	CommonWriter indexer = getIndexer(tenantId);
//		IndexDeletionPolicy policy = indexer.getConfig().getIndexDeletionPolicy();
//		SnapshotDeletionPolicy snapshotter=new SnapshotDeletionPolicy(policy);
//        //String[] keyArray=entry.getKey().split("/");
//        
//		synchronized (snapshotter) {
//			File indexBackupDirFile=new File(indexBackupDir);
//	        if(!indexBackupDirFile.exists())
//	        	indexBackupDirFile.mkdirs();
//	        
//	        IndexCommit commit=snapshotter.snapshot(tenantId);
//	        try {
//		        Collection<String> fileNames=commit.getFileNames();
//		        
//		        //删除在新的快照中不存在的文件
//		        File[] segmentFiles=indexBackupDirFile.listFiles();
//		        for (File file : segmentFiles) {
//		        	boolean exist=false;
//		        	for (String fileName : fileNames) {
//		                if(file.getName().equals(fileName)){
//		                	exist=true;
//		                	break;
//		                }	
//		            }
//		        	
//		        	if(!exist)
//		        		file.delete();
//		        }
//		        
//		        //将快照中新的文件拷贝过去，同名的文件不需要拷贝
//		        segmentFiles=indexBackupDirFile.listFiles();//剩下的文件
//		        File segNFile=new File(indexDir+"/segments.gen");
//		        FileUtils.copyFileToDirectory(segNFile, indexBackupDirFile);
//		        for (String fileName : fileNames) {
//		            boolean exist=false;
//		            for (File file : segmentFiles) {
//		                if(file.getName().equals(fileName)){
//		                	exist=true;
//		                	break;
//		                }
//		            }
//		            
//		            if(!exist){
//		            	File newFile=new File(indexDir+"/"+fileName);
//		            	FileUtils.copyFileToDirectory(newFile, indexBackupDirFile);
//		            	//log.info("backup "+newFile+" success!");
//		            }
//		            	
//		        }
//	        }
//	        finally{
//	        	snapshotter.release(tenantId);
//	        	releaseIndexWriterIfNecessary(tenantId);
//	        }
//		}
//        
//    }
//	
//	@PreDestroy
//	public void shutDown(){
//		try {
////			log.info("shutdown backupManager...");
////			scheduler.shutdown();
//			
//			log.info("shutdown indexerManager...");			
//			for (Entry<String, CommonWriter> entry : indexerMap.entrySet()) {
//				log.info("close indexer("+entry.getKey()+")");
//				entry.getValue().close();
//			}
//			
//			
//		} catch (IOException e) {
//			log.error(e.getMessage(),e);			
//		}
//	}
//	
////	public static SnapshotDeletionPolicy getSnapshot(String tenantId){
////		String key=tenantId;
////		SnapshotDeletionPolicy snapshot=snapshotMap.get(key);
////		if(snapshot==null){
////			getIndexer(tenantId);
////			snapshot=snapshotMap.get(key);
////		}
////		
////		return snapshot;
////	}
//	
//	
//	
//	
//    public static Map<String, SnapshotDeletionPolicy> getSnapshotMap() {
//    	return snapshotMap;
//    }
//
//    public static void releaseIndexWriterIfNecessary(String tenantId) throws IOException{
//    	synchronized (TenantIdSynchronizer.acquire(tenantId)) {
//    		CommonWriter writer = indexerMap.get(tenantId);
//    		if(writer != null){
//    			if(writer.closeWriterIfNecessary(tenantId) == 0){
//    				indexerMap.remove(tenantId);
//    			}
//    		}
//    	}
//    }
//    
//	public static CommonWriter getIndexer(String tenantId){
//		try {
//			String key = tenantId;
//			
//			synchronized (TenantIdSynchronizer.acquire(key)) {
//				CommonWriter indexer = indexerMap.get(key);
//	
//				if (indexer != null) {
//					((CommonWriter)indexer).incRef();
//					return indexer;
//				}
//
//				//重新检查一次，防止重复创建writer
//				indexer=indexerMap.get(key);
//				if(indexer==null){
//					File indexDir = new File(ConfigUtil.getIndexDir(tenantId));
//					IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35));
//					config.setOpenMode(OpenMode.CREATE_OR_APPEND);
////					config.setRAMBufferSizeMB(64);
////					IndexDeletionPolicy policy=new KeepOnlyLastCommitDeletionPolicy();
////					SnapshotDeletionPolicy snapshotter=new SnapshotDeletionPolicy(policy);
////					config.setIndexDeletionPolicy(snapshotter);
////					
//					LogByteSizeMergePolicy mergePolicy=new LogByteSizeMergePolicy();
//					mergePolicy.setUseCompoundFile(true);
//					mergePolicy.setNoCFSRatio(0.5);
//					config.setMergePolicy(mergePolicy);
//					
//					
//					Directory directory = FSDirectory.open(indexDir);
//					if(IndexWriter.isLocked(directory)){
//						log.info("index folder of tenantId [" + tenantId + "] is locked, here we try to unlock it.");
//						IndexWriter.unlock(directory);
//					}
//					indexer=new CommonWriter(directory,config);
//					indexerMap.put(key, indexer);
////					snapshotMap.put(key, snapshotter);
//				}
//				
//				
//				return indexer;
//			}
//
//
//		}
//		catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}
//}
