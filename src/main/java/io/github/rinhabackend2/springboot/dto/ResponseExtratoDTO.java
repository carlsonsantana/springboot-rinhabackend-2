package io.github.rinhabackend2.springboot.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseExtratoDTO {
	private final SaldoExtratoDTO saldo;
	@JsonProperty("ultimas_transacoes")
	private final List<TransacaoExtratoDTO> ultimasTransacoes;

	@Getter
	@AllArgsConstructor
	public static class SaldoExtratoDTO {
		private final long total;
		@JsonProperty("data_extrato")
		// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
		// "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZ")
		private final OffsetDateTime dataExtrato;
		private final long limite;
	}

	@Getter
	@AllArgsConstructor
	public static class TransacaoExtratoDTO {
		private final long valor;
		private final TipoTransacao tipo;
		private final String descricao;
		@JsonProperty("realizada_em")
		// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
		// "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZ")
		private final OffsetDateTime realizadoEm;
	}
}
