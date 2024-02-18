package io.github.rinhabackend2.springboot.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "transacao")
public class TransacaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Long valor;
	@Column(nullable = false)
	private TipoTransacao tipo;
	@Column(nullable = false, length = 10)
	private String descricao;
	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime realizadoEm;

	public TransacaoEntity() {
	}

	public TransacaoEntity(long valor, TipoTransacao tipo, String descricao) {
		this.valor = valor;
		this.tipo = tipo;
		this.descricao = descricao;
	}
}
