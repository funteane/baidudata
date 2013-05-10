package com.cubead.jinjili.index.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Field.Index;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.Operation;
import com.cubead.jinjili.domain.model.UnitModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Constants;

public class UnitCsvFileParser extends CsvFileParser{

	public UnitCsvFileParser(String fileName, Date createDate) {
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
		data.put("unitName", tempMap.get("adgroupName"));

		if (tempMap.get("maxPrice") == null || tempMap.get("maxPrice").equals(CsvFileParser.EMPTY_TAG))
			data.put("maxPrice", "0.00");
		else data.put("maxPrice", tempMap.get("maxPrice"));

		if (tempMap.get("negativeWords") == null || tempMap.get("negativeWords").equals(CsvFileParser.EMPTY_TAG))
			data.put("negativeWords", "");
		else {
			data.put("negativeWords", tempMap.get("negativeWords"));
		}

		if (tempMap.get("exactNegativeWords") == null || tempMap.get("exactNegativeWords").equals(CsvFileParser.EMPTY_TAG))
			data.put("exactNegativeWords", "");
		else {
			data.put("exactNegativeWords", tempMap.get("exactNegativeWords"));
		}

		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			data.put("status", "");
		else 
			data.put("status", tempMap.get("status"));

		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			data.put("pause", "");
		else data.put("pause", tempMap.get("pause"));

		return data;
	}
	
	@Override
	public Indexable nextIndexable() {
		Map<String, String> tempMap = super.next();
		UnitModel unitModel = new UnitModel();
		unitModel.setPlanId(tempMap.get("campaignId"));
		unitModel.setUnitId(tempMap.get("adgroupId"));
		unitModel.setUnitName(tempMap.get("adgroupName"));
		if (tempMap.get("negativeWords") == null || tempMap.get("negativeWords").equals(CsvFileParser.EMPTY_TAG))
			unitModel.setNegativeWords(new String[]{});
		else {
			unitModel.setNegativeWords(tempMap.get("negativeWords").split("\\|\\|"));
		}

		if (tempMap.get("exactNegativeWords") == null || tempMap.get("exactNegativeWords").equals(CsvFileParser.EMPTY_TAG))
			unitModel.setExactNegativeWords(new String[]{});
		else {
			unitModel.setExactNegativeWords(tempMap.get("exactNegativeWords").split("\\|\\|"));
		}
		
		if (tempMap.get("maxPrice") == null || tempMap.get("maxPrice").equals(CsvFileParser.EMPTY_TAG))
			unitModel.setMaxPrice(0.0f);
		else 
			unitModel.setMaxPrice(Float.valueOf(tempMap.get("maxPrice")));
		
		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			unitModel.setPause(false);
		else
			unitModel.setPause(Boolean.valueOf(tempMap.get("pause")));
		
		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			unitModel.setStatus("");
		else 
			unitModel.setStatus(tempMap.get("status"));
		
		unitModel.setBiddableKeywords(new String[]{});
		
		String operator = tempMap.get("operator");
		if ("4".equals(operator)) {
			unitModel.setDeleted("0");
		}else if ("5".equals(operator)) {
			unitModel.setDeleted("1");
		}else if ("6".equals(operator)) {
			unitModel.setDeleted("0");
		} else  {
			unitModel.setDeleted("0");
		}
		
		unitModel.setIndexType(IndexType.UNIT);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		unitModel.setCreateDate(calendar.getTime());
		
		return unitModel;
	}
	
	

}
