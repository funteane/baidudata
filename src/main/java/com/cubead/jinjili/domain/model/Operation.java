package com.cubead.jinjili.domain.model;



public enum Operation{
	PLAN_ADD("1"),									//1： 新增计划
	PLAN_DELETE("2"), 							//2： 删除计划
	PLAN_UPDATE("3"),							//3： 计划属性有修改
	UNIT_ADD("4"),									// 4 ： 新增单元
	UNIT_DELETE("5"),								// 5 ： 删除单元
	UNIT_UPDATE("6"),								// 6 ： 单元属性有修改
	KEYWORD_IDEA_ADD("7"),				// 7 ： 新增关键词/创意
	KEYWORD_IDEA_DELETE("8"),			// 8 ： 删除关键词/创意
	KEYWORD_IDEA_UPDATE("9");			// 9 ： 关键词/创意属性有修改
	//10：新增推广子链 
	//11：删除推广子链
	//12：推广子链属性有更改
	
	
	private String value;
	
	
	private Operation(String value){
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
