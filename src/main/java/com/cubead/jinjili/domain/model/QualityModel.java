package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 关键词质量度
 * @author 
 *
 */
public class QualityModel implements Indexable, Serializable{

	private static final long serialVersionUID = -4928149867494728391L;
	/**
	 * 关键词id
	 */
	String keywordId;
	/**
	 * 该关键词所属的单元id
	 */
	String unitId; 
	/**
	 * 该关键词所属的计划id
	 */
	String planId;
	/**
	 * 该关键词的质量度，取值范围为1-3。
	 * 1：质量度为1颗星
	 * 2：质量度为2颗星
	 * 3：质量度为3颗星
	 */
	int quality;
	
	//索引建立时间
	Date createDate;
	
	
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
