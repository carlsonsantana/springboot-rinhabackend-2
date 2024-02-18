package io.github.rinhabackend2.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "cliente")
public class ClienteEntity {
	@Id
	private Integer id;
	@Column(nullable = false)
	private Long limite;
	@Column(nullable = false)
	private Long saldo;
}
