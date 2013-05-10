package com.cubead.jinjili.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMMON_INFO")
public class CommonInfo implements Serializable {

	private static final long serialVersionUID = 4669984400260092288L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "INFO_KEY")
	private String key;

	@Column(name = "INFO_VALUE")
	private String value;

	@Column(name = "INFO_TYPE")
	private InfoType infoType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public InfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(InfoType infoType) {
		this.infoType = infoType;
	}

	
	/**
	 *  常用信息类别
	 */
	public enum InfoType {
		COUNTRY, 	//国家
		KEYWORD, //词类
		COMPANY, 	//公司
		AREA, 			//投放区域
		REGION, 		//大区
		OPENNING	//开业年份
	}

}
