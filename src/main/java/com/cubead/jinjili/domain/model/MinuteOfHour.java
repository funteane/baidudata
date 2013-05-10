package com.cubead.jinjili.domain.model;

public enum MinuteOfHour {

	ZERO("ZERO"),

	FIFTEEN("FIFTEEN"),

	THIRTY("THIRTY"),

	FORTY_FIVE("FORTY_FIVE");

	private String code;

	private MinuteOfHour(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static MinuteOfHour fromCode(String s) {
		for (MinuteOfHour type : MinuteOfHour.values()) {
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
