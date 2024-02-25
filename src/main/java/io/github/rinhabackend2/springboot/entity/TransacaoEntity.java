package io.github.rinhabackend2.springboot.entity;

import java.time.OffsetDateTime;

public record TransacaoEntity(int idCliente, long valor, String tipo, String descricao,
		OffsetDateTime realizadoEm, long saldo) {
}
