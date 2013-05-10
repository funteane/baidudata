package com.cubead.jinjili.domain.model;

public class AccountBudget {

	private int    effectiveType;    // 生效的预算类型	     0为不设置预算；1为日预算；2为周预算；
	private double   accountBudget;	 // 账户设置的预算值 
	private double[] weeklyBudget;   // 返回本周的每日预算值	 如设置为周预算时，该接口显示的是实际分配到每一天的日预算
	
	public int getEffectiveType() {
		return effectiveType;
	}
	public void setEffectiveType(int effectiveType) {
		this.effectiveType = effectiveType;
	}
	public double getAccountBudget() {
		return accountBudget;
	}
	public void setAccountBudget(double accountBudget) {
		this.accountBudget = accountBudget;
	}
	public double[] getWeeklyBudget() {
		return weeklyBudget;
	}
	public void setWeeklyBudget(double[] weeklyBudget) {
		this.weeklyBudget = weeklyBudget;
	}
	
}
