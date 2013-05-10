package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;

public class RoiModel extends BaseModel implements Indexable, Serializable{
	
	//日期	账户ID	账户	推广计划ID	推广计划	推广单元ID	推广单元	关键词keywordID	关键词	
	//推广方式	展现量	点击量	消费	点击率	平均点击价格	千次展现消费	转化

	private static final long serialVersionUID = -3237098254612004621L;
	
	private RoiType roiType;

	private Date createDate;

	private String accountId;

	private String accountName;
	
	private String planId;
	
	private String planName;
	
	private String unitId;
	
	private String unitName;
	
	private String keywordId;
	
	private String keyword;
	
	private String quality;
	
	private String platform;

	private Long impression;

	private Long click;

	private Double cost;
	
	private Double ctr;
	
	private Double cpc;
	
	private Double cpm;
	
	private Double conversion;
	
	private Double avgRank;
	
	private IndexType indexType;
	
	
	public RoiType getRoiType() {
		return roiType;
	}

	public void setRoiType(RoiType roiType) {
		this.roiType = roiType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public Long getImpression() {
		return impression;
	}

	public void setImpression(Long impression) {
		this.impression = impression;
	}

	public Long getClick() {
		return click;
	}

	public void setClick(Long click) {
		this.click = click;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public Double getCpc() {
		return cpc;
	}

	public void setCpc(Double cpc) {
		this.cpc = cpc;
	}

	public Double getCpm() {
		return cpm;
	}

	public void setCpm(Double cpm) {
		this.cpm = cpm;
	}

	public Double getConversion() {
		return conversion;
	}

	public void setConversion(Double conversion) {
		this.conversion = conversion;
	}
	
		
	public Double getAvgRank() {
		return avgRank;
	}

	public void setAvgRank(Double avgRank) {
		this.avgRank = avgRank;
	}

	
	
	
	public IndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}

	@Override
	public String toString() {
		return "RoiModel [roiType=" + roiType + ", createDate=" + createDate
				+ ", accountId=" + accountId + ", accountName=" + accountName
				+ ", planId=" + planId + ", planName=" + planName + ", unitId="
				+ unitId + ", unitName=" + unitName + ", keywordId="
				+ keywordId + ", keyword=" + keyword + ", quality=" + quality
				+ ", platform=" + platform + ", impression=" + impression
				+ ", click=" + click + ", cost=" + cost + ", ctr=" + ctr
				+ ", cpc=" + cpc + ", cpm=" + cpm + ", conversion="
				+ conversion + ", avgRank=" + avgRank + ", indexType="
				+ indexType + "]";
	}









	public enum RoiType{
		ACCOUNT("1"),
		PLAN("2"),
		UNIT("3"),
		KEYWORD("4");
		
		private String value;
		
		private RoiType(String value){
			this.value = value;
		}
		
		public String getName(){
			return this.name();
		}
		
		public String getValue(){
			return this.value;
		}
		
		
	}
	
	

}
