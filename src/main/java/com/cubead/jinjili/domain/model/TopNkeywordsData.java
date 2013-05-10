package com.cubead.jinjili.domain.model;

public class TopNkeywordsData {

	private long keywordId;
	private float keywordCost;
	private float keywordACP;
	private float keywordClick;
	private String keyword;
	private Integer isdel;
	private Integer status;
	public long getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}
	public float getKeywordCost() {
		return keywordCost;
	}
	public void setKeywordCost(float keywordCost) {
		this.keywordCost = keywordCost;
	}
	public float getKeywordACP() {
		return keywordACP;
	}
	public void setKeywordACP(float keywordACP) {
		this.keywordACP = keywordACP;
	}
	public float getKeywordClick() {
		return keywordClick;
	}
	public void setKeywordClick(float keywordClick) {
		this.keywordClick = keywordClick;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	    
}
