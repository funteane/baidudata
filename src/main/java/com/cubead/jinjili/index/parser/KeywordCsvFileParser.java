package com.cubead.jinjili.index.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Constants;

public class KeywordCsvFileParser extends CsvFileParser{

	public KeywordCsvFileParser(String fileName, Date createDate) {
		super(fileName, createDate);
	}
	
	@Override
	public Map<String, String> next() {
		Map<String, String> tempMap = super.next();

		Map<String, String> data = new HashMap<String, String>();
		data.put("searchEngine", Constants.SEARCH_ENGINE_BAIDU);
		data.put("bidding_strategy", "CPC");// 百度所有数据均为CPC
		data.put("planId", tempMap.get("campaignId"));
		data.put("unitId", tempMap.get("adgroupId")); // 因为Google使用adGroupId，所以此处将其进行了转换
		data.put("keywordId", tempMap.get("keywordId"));
		data.put("keyword", tempMap.get("keyword"));
		data.put("quality", tempMap.get("quality"));

		if (tempMap.get("price") == null || tempMap.get("price").equals(CsvFileParser.EMPTY_TAG))
			data.put("price", "0.00");
		else data.put("price", tempMap.get("price"));

		if (tempMap.get("destinationUrl") == null || tempMap.get("destinationUrl").equals(CsvFileParser.EMPTY_TAG))
			data.put("linkUrl", "");
		else data.put("linkUrl", tempMap.get("destinationUrl"));

		if (tempMap.get("matchType") == null || tempMap.get("matchType").equals(CsvFileParser.EMPTY_TAG))
			data.put("matchType", "");
		else data.put("matchType", tempMap.get("matchType"));

		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			data.put("pause", "");
		else data.put("pause", tempMap.get("pause"));

		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			data.put("status", "");
		else data.put("status", tempMap.get("status"));

		return data;
	}
	
	@Override
	public Indexable nextIndexable() {
		Map<String, String> tempMap = super.next();
		KeywordModel keywordModel = new KeywordModel();
		keywordModel.setBidding_strategy("CPC");
		keywordModel.setPlanId(tempMap.get("campaignId"));
		keywordModel.setUnitId(tempMap.get("adgroupId"));
		keywordModel.setKeyword(tempMap.get("keyword"));
		keywordModel.setKeywordId(tempMap.get("keywordId"));

		keywordModel.setQuality(Integer.valueOf(tempMap.get("quality")));
		
		if (tempMap.get("price") == null || tempMap.get("price").equals(CsvFileParser.EMPTY_TAG))
			keywordModel.setPrice(0.0f);
		else
			keywordModel.setPrice(Float.valueOf(tempMap.get("price")));

		if (tempMap.get("destinationUrl") == null || tempMap.get("destinationUrl").equals(CsvFileParser.EMPTY_TAG))
			keywordModel.setLinkUrl("");
		else 
			keywordModel.setLinkUrl(tempMap.get("destinationUrl"));

		if (tempMap.get("matchType") == null || tempMap.get("matchType").equals(CsvFileParser.EMPTY_TAG))
			keywordModel.setMatchType("");
		else
			keywordModel.setMatchType(tempMap.get("matchType"));

		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			keywordModel.setPause(false);
		else
			keywordModel.setPause(Boolean.valueOf(tempMap.get("pause")));
		
		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			keywordModel.setStatus("");
		else 
			keywordModel.setStatus(tempMap.get("status"));
		
		
		String operator = tempMap.get("operator");
		if ("7".equals(operator)) {
			keywordModel.setDeleted("0");
		}else if ("8".equals(operator)) {
			keywordModel.setDeleted("1");
		}else if("9".equals(operator)){//9
			keywordModel.setDeleted("0");
		}else {
			keywordModel.setDeleted("0");
		}
		
		keywordModel.setIndexType(IndexType.KEYWORD);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		keywordModel.setCreateDate(calendar.getTime());
		
		return keywordModel;
	}

}
