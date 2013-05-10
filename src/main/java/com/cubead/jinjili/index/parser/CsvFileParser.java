package com.cubead.jinjili.index.parser;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.cubead.jinjili.domain.model.Indexable;

public class CsvFileParser implements Iterator<Map<String, String>> {
	
	protected static Logger logger = Logger.getLogger(CsvFileParser.class);
	
	private Date createDate;

	private String tempFileName;
	
	//用于标识空字符
	public static final String EMPTY_TAG = "-";
	//csv文件的头
	private String[] header;
	
	private LineIterator lineIterator;
	
	
	public String[] getHeader() {
		return header;
	}
	
	public CsvFileParser(File file, Date createDate){
		this.tempFileName = file.getPath();
		this.createDate = createDate;
		try {
			//File file=new File(fileName);
			lineIterator=FileUtils.lineIterator(file, "GBK");
			
			//初始化头
			if(lineIterator.hasNext()){
				String headLine=lineIterator.nextLine();
				header=headLine.split("\t");
				
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public CsvFileParser(String fileName, Date createDate) {		
		this(new File(fileName), createDate);
	}

	@Override
	public boolean hasNext() {		
		return lineIterator.hasNext();
	}

	@Override
	public Map<String, String> next() {
		Map<String, String> result=new HashMap<String, String>();
		String line=lineIterator.nextLine();
		if(!"".equals(line)){
			String[] dataArray=line.split("\t");
			if(dataArray.length != header.length){
				logger.warn("dataArray.length = [" + dataArray.length + "] and header.length = [" + header.length + "] of file[" + this.tempFileName + "]");
			}
			for (int i = 0; i < header.length; i++) {
				//log.debug("ga:"+header[i]+"="+dataArray[i]);
				if("-".equals(dataArray[i]))
					result.put(header[i], null);
				else
					result.put(header[i], dataArray[i]);
			}
			return result;
		}
		return null;
	}
	
	
	public Indexable nextIndexable(){
		throw new UnsupportedOperationException("Please override");
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on CsvFileParser");
		
	}
	
	public void close(){
		lineIterator.close();
	}

	public Date getCreateDate() {
		return createDate;
	}

//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
	
	

}
