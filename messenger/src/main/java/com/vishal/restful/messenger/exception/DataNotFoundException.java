package com.vishal.restful.messenger.exception;

public class DataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -54564686416846L;

	public DataNotFoundException(String message) {
		super(message);
	}

}