package com.francisco.openpolls.configuration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.francisco.openpolls.dto.ErrorResponse;

import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException exception){
		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({DataIntegrityViolationException .class, ConstraintViolationException.class})
	public ResponseEntity<?> handleDatabaseException(Exception exception){
		ErrorResponse errorResponse = ErrorResponse.builder().message("Database error").statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception){
		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage()).statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
