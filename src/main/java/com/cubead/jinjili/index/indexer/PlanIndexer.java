package com.cubead.jinjili.index.indexer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.PlanModel;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Tools;
import com.cubead.searchapi.data.vo.OfflineTimeType;
import com.cubead.searchapi.data.vo.ScheduleType;

public class PlanIndexer extends Indexer{
	
	public PlanIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		PlanModel plan = (PlanModel) indexable;
		Document document = new Document();
//		Field field = new Field("id", plan.getPlanId(), 
//				"YES".equals(IndexConfig.config.get("common").get("campaignId")) ? Field.Store.YES : Field.Store.NO,
//				"NO".equals(IndexConfig.config.get("common").get("").get(""))	?	Field.Index.NOT_ANALYZED_NO_NORMS : null
//						);
		Field field = new Field("planId", plan.getPlanId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		field = new Field("planName", plan.getPlanName(), Field.Store.YES ,Field.Index.ANALYZED);
		field.setBoost(1);
		document.add(field);
		
		document.add(new NumericField("budget", Field.Store.YES, true).setFloatValue(plan.getBudget()));
		
		field = new Field("bidding_strategy", String.valueOf(plan.getBidding_strategy()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("pause", String.valueOf(plan.getPause()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		field = new Field("status", plan.getStatus(), Field.Store.YES ,Field.Index.ANALYZED_NO_NORMS);
		document.add(field);
		
		for(String regionTarget : plan.getRegionTarget()){
			field = new Field("regionTarget", regionTarget, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		for(String exactNegativeWord : plan.getExactNegativeWords()){
			field = new Field("exactNegativeWords", exactNegativeWord, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		
		for(String negativeWord : plan.getNegativeWords()){
			field = new Field("negativeWords", negativeWord, Field.Store.YES ,Field.Index.NOT_ANALYZED_NO_NORMS);
			document.add(field);
		}
		
		for(OfflineTimeType offlineTimeType : plan.getBudgetOfflineTime()){
			document.add(new NumericField("budgetOfflineTime_flag", Field.Store.YES, true).setIntValue(offlineTimeType.getFlag()));
			document.add(new NumericField("budgetOfflineTime_time", Field.Store.YES, true).setLongValue(offlineTimeType.getTime().getTime()));
		}
		
		for( ScheduleType scheduleType : plan.getSchedule()){
			document.add(new NumericField("schedule_weekDay", Field.Store.YES, true).setLongValue(scheduleType.getWeekDay()));
			document.add(new NumericField("schedule_startHour", Field.Store.YES, true).setLongValue(scheduleType.getStartHour()));
			document.add(new NumericField("schedule_endHour", Field.Store.YES, true).setLongValue(scheduleType.getEndHour()));
		}
		
		field = new Field("deleted", plan.getDeleted(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		document.add(field);
		
		document.add(new Field("createDate", Tools.getSimpleDateString(plan.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED));
		
		document.add(new Field("indexType", plan.getIndexType().name(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		document.add(new Field("id", Tools.generateDocId(plan.getPlanId(), plan.getIndexType().name(), 
				Tools.getSimpleDateString(plan.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));

		
		return document;
	}
	
	@Override
	public Indexable convert2Indexable(Document document) {
		PlanModel planModel = new PlanModel();
		planModel.setPlanId(document.get("planId"));
		planModel.setPlanName(document.get("planName"));
		planModel.setBudget(document.get("budget") == null ? null : Float.valueOf(document.get("budget")));
		planModel.setExactNegativeWords(document.getValues("exactNegativeWords"));
		planModel.setNegativeWords(document.getValues("negativeWords"));
		planModel.setPause(document.get("") == null ? false : true);
		planModel.setRegionTarget(document.getValues("regionTarget"));
		planModel.setStatus(document.get("status"));
		planModel.setBidding_strategy(document.get("bidding_strategy"));
		
		String[] budgetOfflineTimeFlags = document.getValues("budgetOfflineTime_flag");
		String[] budgetOfflineTimeDates = document.getValues("budgetOfflineTime_time");
		List<OfflineTimeType> offlineTimeTypes = new ArrayList<OfflineTimeType>();
		Calendar calendar = Calendar.getInstance();
		for(int i = 0, length = budgetOfflineTimeFlags.length; i < length; i++){
			OfflineTimeType offlineTimeType = new OfflineTimeType();
			offlineTimeType.setFlag(Integer.valueOf(budgetOfflineTimeFlags[i]));
			calendar.setTimeInMillis(Long.parseLong(budgetOfflineTimeDates[i]));
			offlineTimeType.setTime(calendar.getTime());
			offlineTimeTypes.add(offlineTimeType);
		}
		planModel.setBudgetOfflineTime(offlineTimeTypes);
		
		String[] scheduleWeekDays = document.getValues("schedule_weekDay");
		String[] scheduleStartHours = document.getValues("schedule_startHour");
		String[] scheduleEndHours = document.getValues("schedule_endHour");
		List<ScheduleType> scheduleTypes = new ArrayList<ScheduleType>();
		for(int i = 0, length = scheduleWeekDays.length; i < length; i++){
			ScheduleType scheduleType = new ScheduleType();
			scheduleType.setWeekDay(Integer.parseInt(scheduleWeekDays[i]));
			scheduleType.setStartHour(Long.parseLong(scheduleStartHours[i]));
			scheduleType.setEndHour(Long.parseLong(scheduleEndHours[i]));
			scheduleTypes.add(scheduleType);
		}
		planModel.setSchedule(scheduleTypes);
		
		planModel.setDeleted(document.get("deleted"));
		
		planModel.setCreateDate(Tools.getNormalDate(document.get("createDate")));
	
		planModel.setIndexType(IndexType.valueOf(document.get("indexType")));
		
		planModel.setKey(document.get("id"));
		
		return planModel;
	}
	
}
