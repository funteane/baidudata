package com.cubead.jinjili.domain.model;

public enum TaskType {
	
	DOWNLOAD_WHOLEACCOUNT("11"), 			//整账户下载
	DOWNLOAD_INCREMENTACCOUNT("12"),	//增量整账户下载
	DOWNLOAD_ACCOUNTROI("21"),					//账户ROI
	DOWNLOAD_PLANROI("22"),							//计划ROI
	DOWNLOAD_UNITROI("23"),							//单元ROI
	DOWNLOAD_KEYWORDROI("24"),					//关键词ROI
	DOWNLOAD_ACCOUNT("31"),						//账户
	DOWNLOAD_PLAN("32"),								//计划
	DOWNLOAD_UNIT("33"),									//单元
	DOWNLOAD_KEYWORD("34"),						//关键词
	DOWNLOAD_QUALITY("35"),							//质量度
	INDEX_ACCOUNT("41"),									//
	INDEX_PLAN("42"),											//
	INDEX_UNIT("43"),											//
	INDEX_KEYWORD("44"),									//
	INDEX_ACCOUNTROI("51"),												//
	INDEX_QUALITY("53"),									//
	INDEX_PLANROI("53"),												//
	INDEX_UNITROI("54"),											//
	INDEX_KEYWORDROI("55");												//
	
	
	
	private String value;
	
	
	private TaskType(String value){
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName(){
		return this.name();
	}

}
