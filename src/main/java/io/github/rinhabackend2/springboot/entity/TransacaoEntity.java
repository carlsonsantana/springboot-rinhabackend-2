package io.github.rinhabackend2.springboot.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity(name = "transacao")
public class TransacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacao_seq")
	@SequenceGenerator(name = "transacao_seq", sequenceName = "transacao_seq", allocationSize = 1)
	private Long id;
	@Column(name = "id_cliente", nullable = false)
	private Integer idCliente;
	@Column(nullable = false)
	private Long valor;
	@Column(nullable = false)
	private TipoTransacao tipo;
	@Column(nullable = false, length = 10)
	private String descricao;
	@CreationTimestamp
	@Column(name = "realizado_em", nullable = false)
	private OffsetDateTime realizadoEm;

	public TransacaoEntity() {
	}

	public TransacaoEntity(Integer idCliente, long valor, TipoTransacao tipo, String descricao) {
		this.idCliente = idCliente;
		this.valor = valor;
		this.tipo = tipo;
		this.descricao = descricao;
	}
}
