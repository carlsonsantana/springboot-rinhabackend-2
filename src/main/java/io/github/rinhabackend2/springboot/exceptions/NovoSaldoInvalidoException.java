package io.github.rinhabackend2.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NovoSaldoInvalidoException extends ResponseStatusException {
	private static final long serialVersionUID = -2588087916115216890L;

	public NovoSaldoInvalidoException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
