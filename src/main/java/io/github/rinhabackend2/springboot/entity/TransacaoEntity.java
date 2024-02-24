package io.github.rinhabackend2.springboot.entity;

import java.time.OffsetDateTime;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;

public record TransacaoEntity(int idCliente, long valor, TipoTransacao tipo, String descricao,
		OffsetDateTime realizadoEm, long saldo) {
}
