package com.motta.insurance_scheme_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmployeeServiceUnavailableException extends RuntimeException {

	public EmployeeServiceUnavailableException(String message) {
		super(message);
	}
}
