package com.cubead.jinjili.index.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.Operation;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Constants;
import com.cubead.jinjili.util.Tools;
import com.cubead.searchapi.data.vo.OfflineTimeType;
import com.cubead.searchapi.data.vo.ScheduleType;

public class PlanCsvFileParser extends CsvFileParser{

	public PlanCsvFileParser(String fileName, Date createDate) {
		super(fileName, createDate);
	}
	
	@Override
	public Map<String, String> next() {
		Map<String, String> tempMap = super.next();

		Map<String, String> data = new HashMap<String, String>();
		data.put("searchEngine", Constants.SEARCH_ENGINE_BAIDU);
		data.put("bidding_strategy", "CPC");// 百度所有数据均为CPC
		data.put("planId", tempMap.get("campaignId"));
		data.put("planName", tempMap.get("campaignName"));
		if (tempMap.get("budget") == null || tempMap.get("budget").equals(CsvFileParser.EMPTY_TAG)){
			data.put("budget", "0.00");
		}
		else {
			data.put("budget", tempMap.get("budget"));
		}

		if (tempMap.get("regionTarget") == null || tempMap.get("regionTarget").equals(CsvFileParser.EMPTY_TAG))
			data.put("campaignRegionTarget", "");
		else {
			data.put("regionTarget", tempMap.get("regionTarget"));
		}

		if (tempMap.get("negativeWords") == null || tempMap.get("negativeWords").equals(CsvFileParser.EMPTY_TAG))
			data.put("negativeWords", "");
		else {
			data.put("negativeWords", tempMap.get("negativeWords"));
		}

		if (tempMap.get("exactNegativeWords") == null || tempMap.get("exactNegativeWords").equals(CsvFileParser.EMPTY_TAG))
			data.put("exactNegativeWords", "");
		else {
			data.put("exactNegativeWords", tempMap.get("negativeWords"));
		}

		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			data.put("status", "");
		else 
			data.put("status", tempMap.get("status"));

		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			data.put("pause", "");
		else 
			data.put("pause", tempMap.get("pause"));

		data.put("createDate", Tools.getNormalDateString(getCreateDate()));
	
		return data;
	}
	
	@Override
	public Indexable nextIndexable() {
		Map<String, String> tempMap = super.next();
		PlanModel planModel = new PlanModel();
		planModel.setPause(tempMap.get("pause") == null ? false : Boolean.valueOf(tempMap.get("pause")));
		planModel.setPlanId(tempMap.get("campaignId"));
		planModel.setPlanName(tempMap.get("campaignName"));
		planModel.setBidding_strategy(tempMap.get("CPC"));
		
		if (tempMap.get("budget") == null || tempMap.get("budget").equals(CsvFileParser.EMPTY_TAG)){
			planModel.setBudget(0.0f);
		}
		else {
			planModel.setBudget(Float.valueOf(tempMap.get("budget")));
		}

		if (tempMap.get("regionTarget") == null || tempMap.get("regionTarget").equals(CsvFileParser.EMPTY_TAG))
			planModel.setRegionTarget(new String[]{});
		else {
			planModel.setRegionTarget(tempMap.get("regionTarget").split("\\|\\|"));
		}

		if (tempMap.get("negativeWords") == null || tempMap.get("negativeWords").equals(CsvFileParser.EMPTY_TAG))
			planModel.setNegativeWords(new String[]{});
		else {
			planModel.setNegativeWords(tempMap.get("negativeWords").split("\\|\\|"));
		}

		if (tempMap.get("exactNegativeWords") == null || tempMap.get("exactNegativeWords").equals(CsvFileParser.EMPTY_TAG))
			planModel.setExactNegativeWords(new String[]{});
		else {
			planModel.setExactNegativeWords(tempMap.get("exactNegativeWords").split("\\|\\|"));
		}

		if (tempMap.get("status") == null || tempMap.get("status").equals(CsvFileParser.EMPTY_TAG))
			planModel.setStatus("");
		else
			planModel.setStatus(tempMap.get("status"));

		if (tempMap.get("pause") == null || tempMap.get("pause").equals(CsvFileParser.EMPTY_TAG))
			planModel.setPause(false);
		else
			planModel.setPause(Boolean.valueOf(tempMap.get("pause")));
		
		String schedulestring = tempMap.get("schedule(weekDay startHour endHour)");
		List<ScheduleType> scheduleTypes = new ArrayList<ScheduleType>();
		if (!Tools.empty(schedulestring) && !schedulestring.equals("-")) {
			String[] schedules = schedulestring.split("\\|\\|");
			for(String string : schedules){
				String[] temp = string.split("\\*\\*");
				ScheduleType scheduleType = new ScheduleType();
				scheduleType.setWeekDay(Integer.parseInt(temp[0]));
				scheduleType.setStartHour(Long.parseLong(temp[1]));
				scheduleType.setEndHour(Long.parseLong(temp[2]));
				scheduleTypes.add(scheduleType);
			}
			
		}
		planModel.setSchedule(scheduleTypes);
		
		String offlineTimeString = tempMap.get("budgetOfflineTime(time flag)");
		List<OfflineTimeType> offlineTimeTypes = new ArrayList<OfflineTimeType>();
		if (!Tools.empty(offlineTimeString) && !offlineTimeString.equals("-")) {
			String[] offlineTimes = offlineTimeString.split("\\|\\|");
			for(String string : offlineTimes){
				String[] temp = string.split("\\*\\*");
				OfflineTimeType offlineTimeType = new OfflineTimeType();
				offlineTimeType.setTime(Tools.getUsDate(temp[0]));
				offlineTimeType.setFlag(Integer.parseInt(temp[1]));
				offlineTimeTypes.add(offlineTimeType);
			}
		}
		planModel.setBudgetOfflineTime(offlineTimeTypes);
		
		String operator = tempMap.get("operator");
		if ("1".equals(operator)) {
			planModel.setDeleted("0");
		}else if ("2".equals(operator)) {
			planModel.setDeleted("1");
		}else if ("3".equals(operator)) {	
			planModel.setDeleted("0");
		}else {//null
			planModel.setDeleted("0");
		}
		
		planModel.setIndexType(IndexType.PLAN);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		planModel.setCreateDate(calendar.getTime());
		
		return planModel;
	}
	
	

}
