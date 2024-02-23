package io.github.rinhabackend2.springboot.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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

	@Converter(autoApply = true)
	public static class TipoTransacaoConverter implements AttributeConverter<TipoTransacao, String> {
		@Override
		public String convertToDatabaseColumn(TipoTransacao tipoTransacao) {
			if (tipoTransacao == null) {
				return null;
			}

			return Character.toString(tipoTransacao.tipo());
		}

		@Override
		public TipoTransacao convertToEntityAttribute(String tipo) {
			if (tipo == null) {
				return null;
			}

			return switch (tipo) {
				case "c" -> TipoTransacao.CREDITO;
				default -> TipoTransacao.CREDITO;
			};
		}
	}
}
