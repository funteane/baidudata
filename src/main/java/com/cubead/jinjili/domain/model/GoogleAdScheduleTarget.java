package com.cubead.jinjili.domain.model;

public class GoogleAdScheduleTarget {

	// 投放时间适用于周几。此字段是必填字段，不应为 null。
	private DayOfWeek dayofWeek;

	// 在整点启动时间之后的这一分钟数后开始间隔计时。此字段是必填字段，不应为 null。
	private int startHour = 0;

	private int endHour = 24;

	private MinuteOfHour startMinute = MinuteOfHour.ZERO;

	// 在整点启动时间之后的这一分钟数后结束间隔计时。此字段是必填字段，不应为 null。
	private MinuteOfHour endMinute = MinuteOfHour.ZERO;

	// 该指定时间间隔内的出价调节系数
	private double bidMultiplier = 1.0D;

	public DayOfWeek getDayofWeek() {
		return dayofWeek;
	}

	public void setDayofWeek(DayOfWeek dayofWeek) {
		this.dayofWeek = dayofWeek;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public MinuteOfHour getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(MinuteOfHour endMinute) {
		this.endMinute = endMinute;
	}

	public double getBidMultiplier() {
		return bidMultiplier;
	}

	public void setBidMultiplier(double bidMultiplier) {
		this.bidMultiplier = bidMultiplier;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public MinuteOfHour getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(MinuteOfHour startMinute) {
		this.startMinute = startMinute;
	}

}
