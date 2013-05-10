package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.cubead.searchapi.data.vo.OfflineTimeType;
import com.cubead.searchapi.data.vo.ScheduleType;

public class PlanModel extends BaseModel implements Indexable, Serializable{

	private static final long serialVersionUID = -6137913186766004273L;
	
	String planId;
	String planName;
	Float  budget;
	Boolean pause;
	//21-有效	22-处于暂停时段	23-暂停推广	24-推广计划预算不足	25-账户预算不足
	String status;
	//推广地域列表
	String[] regionTarget;
	String bidding_strategy;
	
	String[] negativeWords;//否定关键词列表
	String[] exactNegativeWords;//精确否定关键词列表
	
	//for google 广告时段设置
	GoogleAdScheduleTarget[] adScheduleTargets;
	
	String errorMsg;
	//推广暂停时段
	protected List<ScheduleType> schedule;
	//到达预算下线时段
	protected List<OfflineTimeType> budgetOfflineTime;
	
	public List<ScheduleType> getSchedule() {
		return schedule;
	}
	public void setSchedule(List<ScheduleType> schedule) {
		this.schedule = schedule;
	}
	public List<OfflineTimeType> getBudgetOfflineTime() {
		return budgetOfflineTime;
	}
	public void setBudgetOfflineTime(List<OfflineTimeType> budgetOfflineTime) {
		this.budgetOfflineTime = budgetOfflineTime;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String[] getNegativeWords() {
		return negativeWords;
	}
	public void setNegativeWords(String[] negativeWords) {
		this.negativeWords = negativeWords;
	}
	public String[] getExactNegativeWords() {
		return exactNegativeWords;
	}
	public void setExactNegativeWords(String[] exactNegativeWords) {
		this.exactNegativeWords = exactNegativeWords;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Float getBudget() {
		return budget;
	}
	public void setBudget(Float budget) {
		this.budget = budget;
	}
	public Boolean getPause() {
		return pause;
	}
	public void setPause(Boolean pause) {
		this.pause = pause;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getRegionTarget() {
		return regionTarget;
	}
	public void setRegionTarget(String[] regionTarget) {
		this.regionTarget = regionTarget;
	}
	public String getBidding_strategy() {
		return bidding_strategy;
	}
	public void setBidding_strategy(String biddingStrategy) {
		bidding_strategy = biddingStrategy;
	}
	public GoogleAdScheduleTarget[] getAdScheduleTargets() {
		return adScheduleTargets;
	}
	public void setAdScheduleTargets(GoogleAdScheduleTarget[] adScheduleTargets) {
		this.adScheduleTargets = adScheduleTargets;
	}
	

	
	@Override
	public String toString() {
		return "PlanModel [planId=" + planId + ", planName=" + planName
				+ ", budget=" + budget + ", pause=" + pause + ", status="
				+ status + ", regionTarget=" + Arrays.toString(regionTarget)
				+ ", bidding_strategy=" + bidding_strategy + ", negativeWords="
				+ Arrays.toString(negativeWords) + ", exactNegativeWords="
				+ Arrays.toString(exactNegativeWords) + ", adScheduleTargets="
				+ Arrays.toString(adScheduleTargets) + ", errorMsg=" + errorMsg
				+ ", schedule=" + schedule + ", budgetOfflineTime="
				+ budgetOfflineTime + "]";
	}
	
	
	
}
