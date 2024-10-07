package com.rest_api.fs14backend.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException extends Exception {
	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}
	
}