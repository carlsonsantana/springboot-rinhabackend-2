package io.github.rinhabackend2.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteNaoEncontradoException extends ResponseStatusException {
	private static final long serialVersionUID = -1433465488278786040L;

	public ClienteNaoEncontradoException() {
		super(HttpStatus.NOT_FOUND);
	}
}
