package io.github.rinhabackend2.springboot.repository;

import org.springframework.data.repository.CrudRepository;

import io.github.rinhabackend2.springboot.entity.TransacaoEntity;

public interface TransacaoRepository extends CrudRepository<TransacaoEntity, Integer> {

}
