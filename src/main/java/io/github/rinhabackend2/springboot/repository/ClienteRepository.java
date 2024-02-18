package io.github.rinhabackend2.springboot.repository;

import org.springframework.data.repository.CrudRepository;

import io.github.rinhabackend2.springboot.entity.ClienteEntity;

public interface ClienteRepository extends CrudRepository<ClienteEntity, Integer> {
}
