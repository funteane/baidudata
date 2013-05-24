package com.cubead.jinjili.index.parser;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.util.Tools;

public class RoiCsvFileParser extends CsvFileParser{

	public RoiCsvFileParser(String fileName, Date createDate) {
		super(fileName, createDate);
	}
	
	@Override
	public Map<String, String> next() {
		Map<String, String> temMap = super.next();
		Map<String, String> data = new HashMap<String, String>();
		String[] header = getHeader();
		for(int i = 0, length = header.length; i < length; i++){
			String fieldName = nameMapping.get(header[i]);
			data.put(fieldName, temMap.get(header[i]));
		}
		
		return data;
	}
	
	@Override
	public Indexable nextIndexable() throws Exception {
		Map<String, String> valueMap = super.next();
		
		RoiModel roiModel = new RoiModel();
		Class<?> clazz = roiModel.getClass();
		String[] header = getHeader();
		for(int i = 0, length = header.length; i < length; i++){
			String fieldName = nameMapping.get(header[i]);
			String methodName = "set".concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
			try {
				Method method = clazz.getMethod(methodName, new Class[]{typeMapping.get(fieldName)});
				if (typeMapping.get(fieldName).equals(Date.class)) {
					method.invoke(roiModel, new Object[]{Tools.getNormalDate(valueMap.get(header[i]))});
				}else if (typeMapping.get(fieldName).equals(Long.class)) {
					method.invoke(roiModel, new Object[]{Long.valueOf(valueMap.get(header[i]))});
				}else if (typeMapping.get(fieldName).equals(Double.class)) {
					String value = valueMap.get(header[i]);
					if (!Tools.empty(value) && value.indexOf("%") > 0) {
						value = value.substring(0, value.length() - 1);
						method.invoke(roiModel, new Object[]{Double.valueOf(value) * 0.01});
					}else {
						method.invoke(roiModel, new Object[]{Double.valueOf(value)});
					}
				}else {
					method.invoke(roiModel, new Object[]{valueMap.get(header[i])});
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new Exception(e);
			}
			
		}
		
		return roiModel;
	}
	
	//日期	账户ID	账户	推广计划ID	推广计划	推广单元ID	推广单元	关键词keywordID	关键词	
	//推广方式	展现量	点击量	消费	点击率	平均点击价格	千次展现消费	转化
	static Map<String, String> nameMapping = new HashMap<String, String>();
	static{
		nameMapping.put("日期", "createDate");
		nameMapping.put("账户ID", "accountId");
		nameMapping.put("账户", "accountName");
		nameMapping.put("推广计划ID", "planId");
		nameMapping.put("推广计划", "planName");
		nameMapping.put("推广单元ID", "unitId");
		nameMapping.put("推广单元", "unitName");
		nameMapping.put("关键词keywordID", "keywordId");
		nameMapping.put("关键词", "keyword");
		nameMapping.put("推广方式", "platform");
		nameMapping.put("展现量", "impression");
		nameMapping.put("点击量", "click");
		nameMapping.put("消费", "cost");
		nameMapping.put("点击率", "ctr");
		nameMapping.put("平均点击价格", "cpc");
		nameMapping.put("千次展现消费", "cpm");
		nameMapping.put("转化", "conversion");
		nameMapping.put("平均排名", "avgRank");
	}
	
	@SuppressWarnings("rawtypes")
	static Map<String, Class> typeMapping = new HashMap<String, Class>(); 
	static{
		typeMapping.put("createDate", Date.class);
		typeMapping.put("accountId", String.class);
		typeMapping.put("accountName", String.class);
		typeMapping.put("planId", String.class);
		typeMapping.put("planName", String.class);
		typeMapping.put("unitId", String.class);
		typeMapping.put("unitName", String.class);
		typeMapping.put("keywordId", String.class);
		typeMapping.put("keyword", String.class);
		typeMapping.put("platform", String.class);
		typeMapping.put("impression", Long.class);
		typeMapping.put("click", Long.class);
		typeMapping.put("cost", Double.class);
		typeMapping.put("ctr", Double.class);
		typeMapping.put("cpc", Double.class);
		typeMapping.put("cpm", Double.class);
		typeMapping.put("conversion", Double.class);
		typeMapping.put("avgRank", Double.class);
	}

}
