package io.github.rinhabackend2.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "cliente")
public class ClienteEntity {
	@Id
	private Integer id;
	private Long limite;
	private Long saldo;
}
