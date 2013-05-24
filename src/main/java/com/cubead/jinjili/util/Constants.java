package com.cubead.jinjili.util;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
	
	public final static String SEARCH_ENGINE_BAIDU = "baidu";
	
	
	public final static String WHOLEACCOUNT_DOWNLOAD_PATH = "d:/goldluck/temp";
	
	public final static String REPORT_DOWNLOAD_PATH = "F:/worksapce01/jinjili/WebContent/report/";
	
	public final static String WHOLE_ACCOUNT_FIRST_DOWNLOAD = "1";
	
	
	public final static Map<String, String> matchTypeMap = new HashMap<String, String>();
	static{
		matchTypeMap.put("1", "精确匹配");
		matchTypeMap.put("2", "短语匹配");
		matchTypeMap.put("3", "广泛匹配");
	}
	
	public final static Map<String, String> statusMap = new HashMap<String, String>();
	static{
		//CampaignType
		statusMap.put("21", "有效");
		statusMap.put("22", "处于暂停时段");
		statusMap.put("23", "暂停推广");
		statusMap.put("24", "推广计划预算不足");
		statusMap.put("25", "账户预算不足");
		//AdgroupType
		statusMap.put("31", "有效");
		statusMap.put("32", "暂停推广");
		statusMap.put("33", "推广计划暂停推广");
		//KeywordType
		statusMap.put("41", "有效");
		statusMap.put("42", "暂停推广");
		statusMap.put("43", "不宜推广");
		statusMap.put("44", "搜索无效");
		statusMap.put("45", "待激活");
		statusMap.put("46", "审核中");
		statusMap.put("47", "搜索量过低");
		//CreativeType
		statusMap.put("51", "有效");
		statusMap.put("52", "暂停推广");
		statusMap.put("53", "不宜推广");
		statusMap.put("54", "待激活");
		statusMap.put("55", "审核中");
		
		
		
	}
	
	public final static Map<String, String> qualityMap = new HashMap<String, String>();
	static{
		qualityMap.put("11", "一星较难优化");
		qualityMap.put("12", "一星难度中等");
		qualityMap.put("13", "一星较易优化");
		qualityMap.put("21", "二星较难优化");
		qualityMap.put("22", "二星较易优化");
		qualityMap.put("3", "三星");
	}
	
	/**
	 * 创意展现方式
	 */
	public final static Map<String, String> showProbMap = new HashMap<String, String>();
	static{
		showProbMap.put("1", "优选");
		showProbMap.put("2", "轮显");
	}
	
	
	public final static Map<String, String> pauseMap = new HashMap<String, String>();
	static{
		pauseMap.put("true", "暂停");
		pauseMap.put("false", "启用");
	}
	
	/**
	 * 是否参加网盟
	 */
	public final static Map<String, String> joinContentMap = new HashMap<String, String>();
	static{
		joinContentMap.put("true", "参加");
		joinContentMap.put("false", "不参加");
	}
	
	public final static Map<String, String> keywordReportHeaderMap = new HashMap<String, String>();
	static{
		keywordReportHeaderMap.put("日期", "createDate");
		keywordReportHeaderMap.put("计划名称", "planName");
		keywordReportHeaderMap.put("单元名称", "unitName");
		keywordReportHeaderMap.put("关键词", "keyword");
		keywordReportHeaderMap.put("价格", "price");
		keywordReportHeaderMap.put("目标地址", "linkUrl");
		keywordReportHeaderMap.put("匹配模式", "matchType");
		keywordReportHeaderMap.put("暂停启用", "pause");
		keywordReportHeaderMap.put("状态", "status");
		keywordReportHeaderMap.put("关键词质量", "quality");
		keywordReportHeaderMap.put("推广方式", "platform");
		keywordReportHeaderMap.put("展现量", "impression");
		keywordReportHeaderMap.put("点击量", "click");
		keywordReportHeaderMap.put("消费", "cost");
		keywordReportHeaderMap.put("点击率", "ctr");
		keywordReportHeaderMap.put("平均点击价格", "cpc");
		keywordReportHeaderMap.put("千次展现消费", "cpm");
		keywordReportHeaderMap.put("平均排名", "avgRank");
		keywordReportHeaderMap.put("转化", "conversion");
		
	}
	
	public final static String[] keywordReportHeaders = new String[]{
		"日期", "账户名称", "计划名称", "单元名称", "关键词", "价格", "目标地址", "匹配模式", "暂停启用", "状态", "关键词质量", 
		"推广方式", 	"展现量", "点击量", "消费","点击率", "平均点击价格", "千次展现消费", "平均排名", "转化", 
		"国家", "公司", "词类", "投放区域", "大区", "开业年份"};
		
}
