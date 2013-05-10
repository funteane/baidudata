package com.cubead.jinjili.exception;

public class RequestDownException extends Exception {

	private static final long serialVersionUID = 1748554936434368110L;

	public RequestDownException() {
		super();
	}

	public RequestDownException(String s) {
		super(s);
	}

	public RequestDownException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestDownException(Throwable cause) {
		super(cause);
	}
}
