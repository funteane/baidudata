package com.cubead.jinjili.domain.model;

import java.io.Serializable;

public class ChangedModel implements Indexable, Serializable{

	private static final long serialVersionUID = 38229770652538731L;

	private String accountId;
	
	private String planId;
	
	private String unitId;
	
	private String keywordId;
	
	private Operation operation;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
