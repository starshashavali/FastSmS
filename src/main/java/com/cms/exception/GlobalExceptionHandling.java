package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

	@ExceptionHandler(DuplicateUserExists.class)
	public ResponseEntity<?> handleDuplicateUserExists(DuplicateUserExists ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	// UserNotValidException
	@ExceptionHandler(UserNotValidException.class)
	public ResponseEntity<?> handleUserNotValidException(DuplicateUserExists ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
