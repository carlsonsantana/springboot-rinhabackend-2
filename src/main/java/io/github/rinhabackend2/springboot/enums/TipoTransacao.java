package io.github.rinhabackend2.springboot.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoTransacao {
	CREDITO('c'),
	DEBITO('d'),
	;

	@JsonValue
	private final char tipo;

	private TipoTransacao(char tipo) {
		this.tipo = tipo;
	}

	public char tipo() {
		return tipo;
	}
}
