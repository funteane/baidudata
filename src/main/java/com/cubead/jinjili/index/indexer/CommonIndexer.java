package com.cubead.jinjili.index.indexer;
//package com.cubead.jinjili.index;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.Fieldable;
//import org.apache.lucene.document.NumericField;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.Query;
//
//import com.cubead.datacenter.Constants;
//import com.cubead.datacenter.index.util.IndexUtil;
//import com.cubead.datacenter.search.SearchUtil;
//import com.cubead.datacenter.util.BeanUtil;
//
//public class CommonIndexer {
//	
////	/private static Log log = LogFactory.getLog(CommonIndexer.class);
//
////	private Document doc;
//	private IndexWriter writer;
//	private String tenantId;
//	private String type;
//
//	public CommonIndexer(String tenantId, String type) {
//		this(tenantId,type,false);
//	}
//	
//
//
//	public CommonIndexer(String tenantId, String type,boolean create) {
//		
//		this.tenantId = tenantId;
//		this.type = type;
//		
//		writer=IndexerManager2.getIndexer(tenantId);
//
//	}
//	
//	public void addDocument(String[] fieldNames,String[] fieldValues){
//		Map<String, Map<String, String>> commonDocConfig = IndexConfig.getCommonDocConfig();
//
//		Document doc = new Document();
//		//需要将type索引到docment中
//		Field typeField=new Field("docType",type,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS);
//		doc.add(typeField);
//		//开始索引其他字段
//		for (int i=0;i<fieldNames.length;i++) {
//	        String field=fieldNames[i];
//	        String value=fieldValues[i];	        
//	        
//	        //判断该字段是否需要索引，如果在index_config.xml中存在，则需要索引
//	        Map<String, String> fieldConfig=commonDocConfig.get(field);	        	        
//	        Field f=null;
//	        if(fieldConfig!=null&&value!=null){
//	        	if(field.equals("id"))
//	        		value=type+"_"+value;
//	        	f=new Field(field, value, Field.Store.YES,
//		        		"YES".equals(fieldConfig.get("is_analyzed")) ? Field.Index.ANALYZED_NO_NORMS : Field.Index.NOT_ANALYZED_NO_NORMS);
//	        }
//	        else{
//	        	if(value==null)
//		        	value="";
//	        	f=new Field(field, value, Field.Store.YES, Field.Index.NO);
//	        }	        
//	        doc.add(f);
//	        
//	        //判断是否需要精确查询
//	        Map<String, String> fieldNotAnalyzedConfig=commonDocConfig.get(field+"_NoAanalyzed");
//	        if(fieldNotAnalyzedConfig!=null){
//	        	Field f2=new Field(field+"_NoAanalyzed",value,Field.Store.NO,Field.Index.NOT_ANALYZED_NO_NORMS);
//	        	doc.add(f2);
//	        }
//	        
//        }
//		
//		try {
//				writer.addDocument(doc);
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}
//	
////	private void refreshDocumentWithFields(Set<String> fields){
////		if(!BeanUtil.isBlank(fields) && doc != null){
////			for(String field : fields){
////				doc.removeFields(field);
////				doc.
////			}
////		}
////	}
//	
//	public void addDocument(Map<String, String> data,boolean delete) {
//		Map<String, Map<String, String>> commonDocConfig = IndexConfig.getCommonDocConfig();
////		if(doc == null){
////			doc = new Document();
////		}
//		
//		//需要将type索引到docment中
//		Field typeField=new Field("docType",type,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS);
//		Document doc = new Document();
//		doc.add(typeField);
//		//开始索引其他字段
//		for (Entry<String, String> entry : data.entrySet()) {
//	        String field=entry.getKey();
//	        String value=entry.getValue();	        
//	        
//	        //判断该字段是否需要索引，如果在index_config.xml中存在，则需要索引
//	        Map<String, String> fieldConfig=commonDocConfig.get(field);	        	        
//	        //boolean isIndex=(fieldConfig!=null);      	        
//	        Fieldable f=null;
//	        if(fieldConfig!=null&&value!=null){
//	        	String fieldType=fieldConfig.get("type");
//	        	if(fieldType.equals("float")){
//	        		f=new NumericField(field,Field.Store.YES,true).setFloatValue(getFloatValue(value));
//	        	}
//	        	else if(fieldType.equals("int")){
//	        		f=new NumericField(field,Field.Store.YES,true).setIntValue(getIntValue(value));
//	        	}
//	        	else{
//	        		if(field.equals("id"))
//		        		value=type+"_"+value;	        	
//		        	f=new Field(field, value, Field.Store.YES,
//			        		"YES".equals(fieldConfig.get("is_analyzed")) ? Field.Index.ANALYZED_NO_NORMS : Field.Index.NOT_ANALYZED_NO_NORMS);
//	        	}
//	        	
//	        }
//	        else{
//	        	if(value==null)
//		        	value="";
//	        	f=new Field(field, value, Field.Store.YES, Field.Index.NO);
//	        }	        
//	        doc.add(f);
//	        
//	        //判断是否需要精确查询
//	        Map<String, String> fieldNotAnalyzedConfig=commonDocConfig.get(field+"_NoAanalyzed");
//	        if(fieldNotAnalyzedConfig!=null){
//	        	Field f2=new Field(field+"_NoAanalyzed",value,Field.Store.NO,Field.Index.NOT_ANALYZED_NO_NORMS);
//	        	doc.add(f2);
//	        }
//	        
//        }
//		
//		try {
//			if(delete){
//				Term term = new Term("id", type+"_"+data.get("id"));
//				writer.updateDocument(term, doc);
//			}
//			else{
//				writer.addDocument(doc);
//			}
//			
//
////			writer.deleteDocuments(term);
////
////			
//			
//			//writer.commit();
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		} finally {
////			refreshDocumentWithFields(data.keySet());
//		}
//	}
//	
//	private int getIntValue(String value){
//		int result=0;
//		try {
//			result=Float.valueOf(value).intValue();
//        }
//        catch (Exception e) {
//	        //ignore
//        }
//		
//		return result;
//	}
//	
//	private float getFloatValue(String value){
//		float result=0;
//		try{
//			result=Float.valueOf(value);
//		}
//		catch (Exception e) {
//			//ignore
//		}
//		
//		return result;
//		
//	}
//	
//	
//
//	public void addDocument(Map<String, String> data) {
//		addDocument(data, false);
//	}
//	
//	/**
//	 * 
//	 * @param data
//	 */
//	public void updateDocument(Map<String, String> data){
//		addDocument(data,true);
//	}
//
//	public void delete(String field_name, List<String> list) {
//
//		try {
//			
//			if(list.size()==0)
//				return;
//			
//			StringBuffer sb=new StringBuffer();
//			sb.append(field_name+":(");
//			for (String id : list) {				
//				if(field_name.equals("id"))
//					id=type+"_"+id;
//				sb.append(id).append(" ");
//			}
//
//			sb.append(")");
//			
//			delete(sb.toString());
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}
//
//	public void delete(String where, boolean force){
//		try {
//			Query query=SearchUtil.getQueryObject(where, type);
//			writer.deleteDocuments(query);
//			if(force){
//				writer.forceMergeDeletes();
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}
//	
//	public void delete(String where) {
//		delete(where, false);
//	}
//
//	public void close() {
//		try {
//			writer.commit();
//			IndexerManager2.releaseIndexWriterIfNecessary(tenantId);
////			IndexerManager.backupIndex(tenantId);
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		}
//		
//		
//	}
//
////	public void optimize() {
////		try {
////			writer.forceMergeDeletes();
////		} catch (Exception e) {
////			throw new RuntimeException(e.getMessage(), e);
////		}
////	}
//	
//	public static void main(String[] args) {
//		IndexConfig.loadConfig();
//		IndexUtil.INDEX_DIR="c:/opt/data/index";
//		
//		CommonIndexer indexer=new CommonIndexer("32", Constants.INDEX_TYPE_KEYWORD_ROI);
//		
//		indexer.delete("id",Arrays.asList("b2138f30f92555930796396cf15d4e9b"));
//		
//		indexer.close();
//	}
//
//}
