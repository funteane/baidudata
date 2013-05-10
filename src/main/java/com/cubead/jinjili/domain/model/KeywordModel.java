package com.cubead.jinjili.domain.model;

import java.io.Serializable;

public class KeywordModel extends BaseModel implements Indexable, Serializable{
	
	private static final long serialVersionUID = 2332706075474601262L;
	
	String keywordId;
	String unitId;
	String keyword;
	//域名需和账户网站URL域名相同	长度限制：最大1024字节
	String linkUrl;
	
	Float price;
	//1 – 精确匹配	2 – 短语匹配	3 – 广泛匹配
	String matchType;
	//true - 暂停	false - 启用
	Boolean pause; 
	String status; 
	//41-有效	42-暂停推广	43-不宜推广	44-搜索无效	45-待激活	46-审核中	47-搜索量过低
	String bidding_strategy; 
	
	transient String unitName;
	String planId;
	transient String planName;
	
	Integer quality;
	
	String errorMsg;
	

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
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
	
	
	
	
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return "KeywordModel [keywordId=" + keywordId + ", unitId=" + unitId
				+ ", keyword=" + keyword + ", linkUrl=" + linkUrl + ", price="
				+ price + ", matchType=" + matchType + ", pause=" + pause
				+ ", status=" + status + ", bidding_strategy="
				+ bidding_strategy + ", planId=" + planId + ", quality="
				+ quality + ", errorMsg=" + errorMsg + "]";
	}
	
	

	
	
	
	
}
