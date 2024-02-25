package io.github.rinhabackend2.springboot.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseExtratoDTO(SaldoExtratoDTO saldo,
		@JsonProperty("ultimas_transacoes") List<TransacaoExtratoDTO> ultimasTransacoes) {
	public record SaldoExtratoDTO(long total, @JsonProperty("data_extrato") OffsetDateTime dataExtrato, long limite) {
	}

	public record TransacaoExtratoDTO(long valor, String tipo, String descricao,
			@JsonProperty("realizada_em") OffsetDateTime realizadoEm) {
	}
}
