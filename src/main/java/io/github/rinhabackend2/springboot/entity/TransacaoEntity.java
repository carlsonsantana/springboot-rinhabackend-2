package io.github.rinhabackend2.springboot.entity;

import java.time.OffsetDateTime;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import lombok.Data;

@Data
public class TransacaoEntity {
	private Long id;
	private Integer idCliente;
	private Long valor;
	private TipoTransacao tipo;
	private String descricao;
	private OffsetDateTime realizadoEm;
	private Long saldo;
}
