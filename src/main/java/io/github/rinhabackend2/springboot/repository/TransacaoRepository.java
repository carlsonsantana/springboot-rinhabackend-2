package io.github.rinhabackend2.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.rinhabackend2.springboot.entity.TransacaoEntity;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Integer> {
	List<TransacaoEntity> findAllByIdCliente(int idCliente, Pageable pageable);
}
