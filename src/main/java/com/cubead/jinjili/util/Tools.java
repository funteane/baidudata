package com.cubead.jinjili.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cubead.jinjili.domain.model.RoiModel.RoiType;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;

public final class Tools {
	
	private static final SimpleDateFormat NORMAL_DATE_FORMAT =  new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT =  new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 列表转换为数组
	 * @param srcs
	 * @param dests 空的数组
	 */
	public static <T> void list2Array(List<T> srcs, T[] dests){
		for(int i = 0, length = srcs.size(); i < length; i++){
			dests[i] = srcs.get(i);
		}
	}
	
	/**
	 * 数组转换为列表
	 * @param srcs
	 * @param dests  空的列表
	 */
	public static <T> void array2List(T[] srcs, List<T> dests){
		for(T src : srcs){
			dests.add(src);
		}
	}
	
	/**
	 * 是否null 或者 “”
	 * @return
	 */
	public static boolean empty(String target){
		if (target == null || "".equals(target) || "null".equals(target) ) {
			return true;
		}
		
		return false;
		
	}
	
	public static <T>  boolean empty(T target){
		if (target == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否null 或者 empty
	 * @param target
	 * @return
	 */
	public static <T> boolean empty(List<T> target){
		if (target == null || target.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static <T> boolean empty(T[] target){
		if (target == null || target.length == 0) {
			return true;
		}
		return false;
	}
	
	
	public static String getNormalDateString(Date date){
		if (date == null) {
			return null;
		}
		return NORMAL_DATE_FORMAT.format(date);
	}
	
	public static String getSimpleDateString(Date date){
		if (date == null) {
			return null;
		}
		return SIMPLE_DATE_FORMAT.format(date);
	}
	
	public static Date getNormalDate(String dateString){
		if (dateString == null) {
			return null;
		}
		try {
			return NORMAL_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date getSimpleDate(String dateString){
		if (dateString == null) {
			return null;
		}
		try {
			return SIMPLE_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date getUsDate(String dateString){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy" ,Locale.US);  
		try {
			return simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <K,T>  Map<K, T> inverseMap(Map<T, K> sourceMap){
		Map<K, T> destMap = new HashMap<K, T>();
		Set<Entry<T, K>> tempSet = sourceMap.entrySet();
		for(Entry<T, K> entry : tempSet){
			destMap.put(entry.getValue(), entry.getKey());
		}
		return destMap;
	}
	
	
	public static String generateDocId(String... fields){
		String key = "";
		for (String field : fields) {
			key = key.concat(field); 
		}
		
		return Md5Util.MD5(key);
	}
	
	
	public static void main(String[] args) throws ParseException {
		String string = "Thu Apr 18 00:00:00 CST 2013";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
		System.out.println(generateDocId("2749704713", IndexType.KEYWORD.name(), getSimpleDateString(calendar.getTime())));
		System.out.println(generateDocId("2749704713", RoiType.KEYWORD.name(), getSimpleDateString(calendar.getTime())));
	}
	
	

}
