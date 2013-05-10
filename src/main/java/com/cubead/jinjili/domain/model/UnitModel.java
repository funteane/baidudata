package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Arrays;

public class UnitModel extends BaseModel implements Indexable, Serializable{

	private static final long serialVersionUID = -9221828539599211358L;
	
	private String planId;
	private String planName;
	private String unitId;
	private String unitName;
	//推广单元最高出价
	private Float maxPrice;
	private Boolean pause;
	//31-有效	32-暂停推广	33-推广计划暂停推广
	private String status;
	private String bidding_strategy;// 定价策略

	private String[] negativeWords;// 否定关键词列表
	private String[] exactNegativeWords;// 精确否定关键词列表

	private String[] biddableKeywords;// 竞价关键词
	

	private String errorMsg;
	


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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
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

	public String getBidding_strategy() {
		return bidding_strategy;
	}

	public void setBidding_strategy(String biddingStrategy) {
		bidding_strategy = biddingStrategy;
	}

	public String[] getBiddableKeywords() {
		return biddableKeywords;
	}

	public void setBiddableKeywords(String[] biddableKeywords) {
		this.biddableKeywords = biddableKeywords;
	}

	@Override
	public String toString() {
		return "UnitModel [planId=" + planId + ", planName=" + planName
				+ ", unitId=" + unitId + ", unitName=" + unitName
				+ ", maxPrice=" + maxPrice + ", pause=" + pause + ", status="
				+ status + ", bidding_strategy=" + bidding_strategy
				+ ", negativeWords=" + Arrays.toString(negativeWords)
				+ ", exactNegativeWords=" + Arrays.toString(exactNegativeWords)
				+ ", biddableKeywords=" + Arrays.toString(biddableKeywords)
				+ ", errorMsg=" + errorMsg + "]";
	}
	
	

}
