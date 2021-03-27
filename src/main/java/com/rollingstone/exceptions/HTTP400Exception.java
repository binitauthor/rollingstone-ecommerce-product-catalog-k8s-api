package com.rollingstone.exceptions;

public class HTTP400Exception extends RuntimeException {

	public HTTP400Exception() {
		super();
	}
	
	public HTTP400Exception(String message, Throwable cause) {
		super(message,cause);
	}
	
	public HTTP400Exception(String message) {
		super(message);
	}
	
	public HTTP400Exception(Throwable cause) {
		super(cause);
	}
}
