package com.cubead.jinjili.domain.model;

import java.io.Serializable;
import java.util.List;

public class AccountModel extends BaseModel implements Indexable, Serializable{
	
	private static final long serialVersionUID = -4712584468405854782L;
	
	String username;
	
	String password;
	
	String accountId;
	
	//账户预算
	Double budget;
	//账户累积消费
	Double cost;
	//账户余额
	Double balance;
	//账户投资
	Double payment;
	//账户财务数据类型
	int type;
	//推广地域列表
	List<Integer> regions;
	//ip排除列表
	List<String> excludeIp;
	//	账户开放域名列表
	List<String> openDomains;
	
	String searchEngine;
	

	public AccountModel(){
		
	}
	public List<String> getOpenDomains() {
		return openDomains;
	}

	public void setOpenDomains(List<String> openDomains) {
		this.openDomains = openDomains;
	}

	public AccountModel(String username,String password){
		this.username=username;
		this.password=password;
	}
	
	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    public List<Integer> getRegions() {
    	return regions;
    }
	
    public void setRegions(List<Integer> regions) {
    	this.regions = regions;
    }
	public List<String> getExcludeIp() {
		return excludeIp;
	}
	public void setExcludeIp(List<String> excludeIp) {
		this.excludeIp = excludeIp;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	
	public String getSearchEngine() {
		return searchEngine;
	}
	public void setSearchEngine(String searchEngine) {
		this.searchEngine = searchEngine;
	}
	@Override
	public String toString() {
		return "AccountModel [username=" + username + ", password=" + password
				+ ", accountId=" + accountId + ", budget=" + budget + ", cost="
				+ cost + ", balance=" + balance + ", payment=" + payment
				+ ", type=" + type + ", regions=" + regions + ", excludeIp="
				+ excludeIp + ", openDomains=" + openDomains
				+ ", searchEngine=" + searchEngine + "]";
	}

	

	
	
	
	
}
