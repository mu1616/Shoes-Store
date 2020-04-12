package com.example.project_01.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=BindException.class)
	public String handleBindException() {
		return "/error/bindError";
	}
	
	@ExceptionHandler(value=SQLIntegrityConstraintViolationException.class)
	public String handleIntegrityException() {
		return "/error/IntegrityError";
	}
	

}
