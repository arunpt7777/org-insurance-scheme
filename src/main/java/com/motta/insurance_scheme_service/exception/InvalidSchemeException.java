package com.motta.insurance_scheme_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidSchemeException extends RuntimeException {

	public InvalidSchemeException(String message) {
		super(message);
	}
}
