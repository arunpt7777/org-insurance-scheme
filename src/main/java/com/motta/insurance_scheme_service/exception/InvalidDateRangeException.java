package com.motta.insurance_scheme_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidDateRangeException extends RuntimeException {

	public InvalidDateRangeException(String message) {
		super(message);
	}
}
