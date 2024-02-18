package io.github.rinhabackend2.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.github.rinhabackend2.springboot.entity.ClienteEntity;
import jakarta.persistence.LockModeType;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Integer> {
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT c FROM cliente c WHERE c.id = :idCliente")
	Optional<ClienteEntity> findByIdWithLock(int idCliente);
}
