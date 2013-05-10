package com.cubead.jinjili.index.indexer;
//package com.cubead.jinjili.index;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//
//import javax.swing.text.Highlighter;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.Field.Index;
//import org.apache.lucene.index.CorruptIndexException;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.queryParser.MultiFieldQueryParser;
//import org.apache.lucene.queryParser.ParseException;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.NRTManager;
//import org.apache.lucene.search.NRTManagerReopenThread;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.SearcherManager;
//import org.apache.lucene.search.SearcherWarmer;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.store.LockObtainFailedException;
//import org.apache.lucene.util.Version;
//
///* 通过它来对索引做直接的操作
//* 
//* @author Administrator
//* 
//*/
//public class LuceneUtil {
//// 近实时搜索管理器
//public static NRTManager nrtManager;
//// 索引的写工具
//public static IndexWriter indexWriter;
//// 分词器
//public static Analyzer analyzer;
//// 索引目录对象
//public static Directory dir;
//// 索引的路径
//public static String INDEX_PATH;
//// 字典的路径
//public static String DICTIONARY_PATH;
//// 近实时搜索的线程
//public static NRTManagerReopenThread thread;
//
///**
//* 单例模式获取nrtmanager对象
//* 
//* @return
//*/
//public static NRTManager getNrtManager() {
//if (nrtManager == null) {
//try {
//				analyzer = new MMSegAnalyzer(new File(DICTIONARY_PATH));
//dir = FSDirectory.open(new File(INDEX_PATH));
//IndexWriterConfig config = new IndexWriterConfig(
//Version.LUCENE_35, analyzer);
//indexWriter = new IndexWriter(dir, config);
//nrtManager = new NRTManager(indexWriter,
//Executors.newCachedThreadPool(), new SearcherWarmer() {
//@Override
//public void warm(IndexSearcher arg0)
//throws IOException {
//
//System.out.println("it  has  changed!!!");
//
//}
//});
//double minStaleSec = 0.025;
//double maxStaleSec = 5.0;
//thread = new NRTManagerReopenThread(nrtManager, maxStaleSec,
//minStaleSec);
//thread.setDaemon(true);
//thread.start();
//
//} catch (CorruptIndexException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (LockObtainFailedException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (IOException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//}
//}
//return nrtManager;
//}
//
///**
//* 初始化路径
//* 
//* @param indexpath
//* @param dicpath
//*/
//public static void init(String indexpath, String dicpath) {
//INDEX_PATH = indexpath;
//DICTIONARY_PATH = dicpath;
//
//}
//
///**
//* 添加文件
//* 
//* @param path
//*/
//public void addFile(String path, String address, String filetruename) {
//
//try {
//NRTManager manager = LuceneUtil.getNrtManager();
//File file = new File(path);
//Document document = new Document();
//Field field = new Field("filename", filetruename, Field.Store.YES,
//Index.NOT_ANALYZED);
//document.add(field);
//field = new Field("filecontent", new Tika().parseToString(file),
//Field.Store.NO, Index.ANALYZED);
//document.add(field);
//field = new Field("filepath", path, Field.Store.YES, Index.NO);
//document.add(field);
//
//field = new Field("fileaddr", address, Field.Store.YES, Index.NO);
//document.add(field);
//
//manager.addDocument(document);
//LuceneUtil.indexWriter.commit();
//
//} catch (IOException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (TikaException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//}
//
//}
//
///**
//* 根据条件查询
//* 
//* @param conditions
//* @return
//*/
//public SearcherResVo searcherByCondition(int currentpage, String conditions) {
//SearcherResVo rs = new SearcherResVo();
//
//List<DocumentVo> documentvos = new ArrayList<DocumentVo>();
//try {
//System.out.println(INDEX_PATH);
//// 开始查询的系统时间
//long begin = System.currentTimeMillis();
//NRTManager nrtmanager = LuceneUtil.getNrtManager();
//SearcherManager manager = nrtmanager.getSearcherManager(true);
//System.out.println("----------------");
//System.out.println(indexWriter.maxDoc());
//System.out.println(indexWriter.numDocs());
//
//IndexSearcher searcher = manager.acquire();
//
//MultiFieldQueryParser parser = new MultiFieldQueryParser(
//Version.LUCENE_35, new String[] { "filecontent", },
//analyzer);
//
//Query query = parser.parse(conditions);
//
//System.out.println("在查询条件是" + conditions);
//
//TopDocs docs = searcher.search(query, 100);
//
//System.out.println("长度" + docs.scoreDocs.length);
//
//// 设置当前页
//rs.setCurrentpage(currentpage);
//// 结束查询是的时间
//long end = System.currentTimeMillis();
//// 设置搜索时间
//rs.setSearcherTime(end - begin);
//// 设置总共查询到的条数
//rs.setTotalresult(docs.totalHits);
//// 设置总的页数
//if (rs.getTotalresult() != 0
//&& rs.getTotalresult() % rs.getPerpagesize() == 0)
//rs.setPageTotal(rs.getTotalresult() / rs.getPerpagesize());
//else {
//rs.setPageTotal(rs.getTotalresult() / rs.getPerpagesize() + 1);
//}
//// scoreafter对应的下标
//int start = (currentpage - 1) * (rs.getPerpagesize() - 1);
//
//System.out.println("start------->" + start);
//
//System.out.println("hits-------->" + docs.totalHits);
//
//System.out.println("currentpage-------->" + currentpage);
//
//// 当当前页码是1的时候，searchAfter会忽略第一个命中的元素,所以提前将其置于数组当中
//if (currentpage == 1 && docs.scoreDocs.length >= 1) {
//DocumentVo vo = new DocumentVo();
//Document document = searcher.doc(docs.scoreDocs[0].doc);
//vo.setFilename(document.get("filename"));
//vo.setFileurl(document.get("filepath"));
//vo.setFileaddr(document.get("fileaddr"));
//System.out.println("--------------------------->"
//+ document.get("fileaddr"));
//vo.setFilescoredfragment(hightlightText(query, document));
//documentvos.add(vo);
//}
//TopDocs tops = null;
//if (docs.scoreDocs.length > 1) {
//if (currentpage == 1)
//tops = searcher.searchAfter(docs.scoreDocs[start], query,
//rs.getPerpagesize() - 1);
//else
//tops = searcher.searchAfter(docs.scoreDocs[start], query,
//rs.getPerpagesize());
//}
//if (tops != null)
//for (ScoreDoc doc : tops.scoreDocs) {
//System.out.println("i------------->");
//DocumentVo vo = new DocumentVo();
//Document document = searcher.doc(doc.doc);
//vo.setFilename(document.get("filename"));
//vo.setFileurl(document.get("filepath"));
//vo.setFileaddr(document.get("fileaddr"));
//vo.setFilescoredfragment(hightlightText(query, document));
//documentvos.add(vo);
//}
//// 设置记录的详细
//rs.setDocumentvos(documentvos);
//
//} catch (ParseException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (IOException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//}
//return rs;
//}
//
///**
//* 高亮选中
//* 
//* @param query
//* @param document
//* @return
//*/
//public String hightlightText(Query query, Document document) {
//String res = null;
//
//try {
//SimpleFragmenter fragment = new SimpleFragmenter();
//fragment.setFragmentSize(200);
//QueryScorer queryScorer = new QueryScorer(query);
//Highlighter highter = new Highlighter(new SimpleHTMLFormatter(
//"<font   color='red'>", "</font>"), queryScorer);
//highter.setTextFragmenter(fragment);
//res = highter.getBestFragment(new MMSegAnalyzer(
//LuceneUtil.DICTIONARY_PATH), "filecontent", new Tika()
//.parseToString(new File(document.get("filepath"))));
//
//} catch (IOException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (InvalidTokenOffsetsException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//} catch (TikaException e) {
//// TODO Auto-generated catch block
//e.printStackTrace();
//}
//return res;
//
//}
//
//}
//
