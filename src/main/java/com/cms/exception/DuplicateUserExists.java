package com.cms.exception;

public class DuplicateUserExists extends RuntimeException {

	public DuplicateUserExists(String msg) {
		super(msg);
	}

}
