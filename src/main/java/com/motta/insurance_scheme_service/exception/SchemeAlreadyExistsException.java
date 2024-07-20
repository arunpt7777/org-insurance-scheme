package com.motta.insurance_scheme_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class SchemeAlreadyExistsException extends RuntimeException {

	public SchemeAlreadyExistsException(String message) {
		super(message);
	}
}
