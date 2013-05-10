//package searchapi;   
//  
//import java.io.File;   
//  
//import org.apache.lucene.analysis.Analyzer;   
//import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;   
//import org.apache.lucene.analysis.WhitespaceAnalyzer;   
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;   
//import org.apache.lucene.document.Field;   
//import org.apache.lucene.document.NumericField;   
//import org.apache.lucene.index.IndexReader;   
//import org.apache.lucene.index.IndexWriter;   
//import org.apache.lucene.search.IndexSearcher;   
//import org.apache.lucene.search.NumericRangeQuery;   
//import org.apache.lucene.search.Query;   
//import org.apache.lucene.search.ScoreDoc;   
//import org.apache.lucene.search.Searcher;   
//import org.apache.lucene.search.TopDocs;   
//import org.apache.lucene.store.FSDirectory;   
//import org.apache.lucene.util.Version;
//import org.wltea.analyzer.lucene.IKAnalyzer;   
//  
//public class LuceneIndexWriter {   
//       
//    public static String index_path="D:\\temp";   
//    public static String index_path2="D:\\temp";   
//    public static void main(String[]args) throws Exception{   
//           
//           
//            
//        createMultiValueIndex();   
//        //createIndex();    
//        //词范围查询，但需要将价格字符串格式化，按位数对此，前面补0   
//        //TermRangeQuery  trquery  = new TermRangeQuery("price", "088", "100",true, true);   
//        //按数字类型范围查询   
//        Query trquery  = NumericRangeQuery.newIntRange("price", 1, 99,true, true);   
//           
//        //IndexReader  indexReader = IndexReader.open(FSDirectory.open(new File(index_path)));   
//        IndexReader  indexReader = IndexReader.open(FSDirectory.open(new File(index_path2)));   
//           
//        Searcher  searcher = new IndexSearcher(indexReader);   
//           
//        TopDocs topDocs = searcher.search(trquery,100);   
//           
//        for (ScoreDoc hits:topDocs.scoreDocs){   
//               
//            Document doc = searcher.doc(hits.doc);   
//            System.out.println("doc = "+doc.getValues("price"));   
//               
//        }   
//           
//  
//           
//    }   
//    /**  
//     * document包含多个同名price Filed  
//     */  
//    private static void createMultiValueIndex() {   
////        Analyzer  analyzer = new IKAnalyzer();   
//        Analyzer  analyzer = new StandardAnalyzer(Version.LUCENE_35);
//        PerFieldAnalyzerWrapper  perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(analyzer);   
//        try {   
//               
//            //perFieldAnalyzerWrapper.addAnalyzer("price", new WhitespaceAnalyzer());   
//            IndexWriter writer = new IndexWriter(FSDirectory.open(new File(index_path2)), perFieldAnalyzerWrapper, true,IndexWriter.MaxFieldLength.LIMITED);   
//            Document doc  = new Document();  
//            doc.add(new Field("hotelName", "test2", Field.Store.YES, Field.Index.ANALYZED));   
//  
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(100));   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(200));   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(300));   
//            writer.addDocument(doc);   
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test3", Field.Store.YES, Field.Index.ANALYZED));   
//                            //增加多个同名字段(price)   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(99));   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(213));   
//            writer.addDocument(doc);   
//               
//               
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test1", Field.Store.YES, Field.Index.ANALYZED));   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(400));   
//            doc.add(new NumericField("price", Field.Store.YES, true).setIntValue(200));   
//            writer.addDocument(doc);   
//               
//           
//               
//            writer.close();   
//               
//        } catch (Exception e) {   
//            // TODO Auto-generated catch block   
//            e.printStackTrace();   
//        }   
//    }   
//    /**  
//     * price中价格按照空格分隔，保存到一个field  
//     */  
//    private static void createIndex() {   
//        Analyzer  analyzer = new IKAnalyzer();   
//        PerFieldAnalyzerWrapper  perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(analyzer);   
//        try {   
//               
//            perFieldAnalyzerWrapper.addAnalyzer("price", new WhitespaceAnalyzer());   
//            IndexWriter writer = new IndexWriter(FSDirectory.open(new File(index_path)), perFieldAnalyzerWrapper, true,IndexWriter.MaxFieldLength.LIMITED);   
//            Document doc  = new Document();   
//            doc.add(new Field("hotelName", "test2", Field.Store.YES, Field.Index.ANALYZED));   
//            doc.add(new Field("price", "100 200 300", Field.Store.YES, Field.Index.ANALYZED));   
//            writer.addDocument(doc);   
//               
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test1", Field.Store.YES, Field.Index.ANALYZED));   
//            doc.add(new Field("price", "400 500", Field.Store.YES, Field.Index.ANALYZED));   
//            writer.addDocument(doc);   
//               
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test8", Field.Store.YES, Field.Index.ANALYZED));   
//                             //price字段以空格分隔，使用WhitespaceAnalyzer分词   
//            doc.add(new Field("price", "213 588 099 ", Field.Store.YES, Field.Index.ANALYZED));   
//            writer.addDocument(doc);   
//               
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test3", Field.Store.YES, Field.Index.ANALYZED));   
//            doc.add(new Field("price", "600 700 518", Field.Store.YES, Field.Index.ANALYZED));   
//            writer.addDocument(doc);   
//               
//               
//            doc  = new Document();   
//            doc.add(new Field("hotelName", "test4", Field.Store.YES, Field.Index.ANALYZED));   
//            doc.add(new Field("price", "200 228 518", Field.Store.YES, Field.Index.ANALYZED));   
//            writer.addDocument(doc);   
//               
//            writer.close();   
//               
//        } catch (Exception e) {   
//            // TODO Auto-generated catch block   
//            e.printStackTrace();   
//        }   
//    }   
//}  
