package com.cubead.jinjili.domain.model;

public enum DayOfWeek {

	MONDAY("MONDAY"),

	TUESDAY("TUESDAY"),

	WEDNESDAY("WEDNESDAY"),

	THURSDAY("THURSDAY"),

	FRIDAY("FRIDAY"),

	SATURDAY("SATURDAY"),

	SUNDAY("SUNDAY");

	private String code;

	private DayOfWeek(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static DayOfWeek fromCode(String s) {
		for (DayOfWeek type : DayOfWeek.values()) {
			if (type.equals(s.toUpperCase()))
				return type;
		}
		throw new IllegalArgumentException(s.toUpperCase());
	}

	public boolean equals(String s) {
		if (this.code != null)
			return this.code.equalsIgnoreCase(s);
		return false;
	}
}
