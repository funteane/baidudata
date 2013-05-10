package com.cubead.jinjili.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cubead.jinjili.domain.model.Indexable;

public class ExcelUtil {
	//私有化构造函数
	private ExcelUtil(){}
	
	/** 
	 * @author mengweifeng
	 * @param filePath
	 * @param sheets key=sheet页名称 value=需要写入的sheet页的内容(string数组的形式)，String[]第一行为标题
	 * @throws IOException 
	 * @throws IOException
	 * @since 2012-11-28
	 */ 
	public static void  create03ExcelFile(String filePath,Map<String,List<String[]>> sheets) throws IOException {
		File excelFile=new File(filePath);
		create03ExcelFile(excelFile, sheets);
	}
	/** 
	 * @author mengweifeng
	 * @param excelFile
	 * @param sheets key  =   sheet页名称   value  =   需要写入的sheet页的内容(string数组的形式)，String[]第一行为标题
	 * @throws IOException 
	 * @throws IOException
	 * @since 2012-11-28
	 */ 
	public static void  create03ExcelFile(File excelFile,Map<String,List<String[]>> sheets) throws IOException {
		Workbook wb=new HSSFWorkbook();
		if(!excelFile.getAbsolutePath().endsWith(".xls")){
			throw new IOException("错误的扩展名");
		}
		createExcelFile(wb,excelFile,sheets);
	}
	/** 创建07格式的excel文件
	 * @author mengweifeng
	 * @param filePath
	 * @param sheets
	 * @throws IOException
	 * @since 2013-5-8
	 */ 
	public static void  create07ExcelFile(String filePath,Map<String,List<String[]>> sheets) throws IOException {
		File excelFile=new File(filePath);
		create07ExcelFile(excelFile, sheets);
	}
	/** 创建07格式的excel文件
	 * @author mengweifeng
	 * @param excelFile
	 * @param sheets
	 * @throws IOException
	 * @since 2013-5-8
	 */ 
	public static void  create07ExcelFile(File excelFile,Map<String,List<String[]>> sheets) throws IOException {
		Workbook wb=new XSSFWorkbook();
		if(!excelFile.getAbsolutePath().endsWith(".xlsx")){
			throw new IOException("错误的扩展名");
		}
		createExcelFile(wb,excelFile,sheets);
	}
	
	private static void createExcelFile(Workbook wb,File excelFile,Map<String,List<String[]>> sheets ) throws IOException{
		for(Entry<String,List<String[]>> entry:sheets.entrySet()){
			String sheetName=entry.getKey();
			Sheet sheet=wb.createSheet(sheetName);
			List<String[]> sheetValues=entry.getValue();
			for(int i=0;i<sheetValues.size();i++){
				String[] values=sheetValues.get(i);
				Row row=sheet.createRow(i);
				for(int j=0;j<values.length;j++){
					Cell cell=row.createCell(j);
					cell.setCellValue(values[j]);
				}
			}
		}
		OutputStream os=new FileOutputStream(excelFile);
		wb.write(os);
		os.flush();
		os.close();
	}
	
	
	public static void create07ExcelFile(List<Indexable> indexables, Map<String, Indexable> indexableMap){
		
	}
	
	
}
