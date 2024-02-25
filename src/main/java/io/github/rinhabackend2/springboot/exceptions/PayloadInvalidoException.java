package io.github.rinhabackend2.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PayloadInvalidoException extends ResponseStatusException {
	private static final long serialVersionUID = 8959231239011337201L;

	public PayloadInvalidoException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
