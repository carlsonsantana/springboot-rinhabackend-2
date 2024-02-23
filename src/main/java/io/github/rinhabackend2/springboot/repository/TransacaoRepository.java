package io.github.rinhabackend2.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.rinhabackend2.springboot.entity.TransacaoEntity;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Integer> {
	@Query(value = """
		WITH updated as (
			UPDATE cliente SET saldo = saldo + :valor
			WHERE
				id = :idCliente
				AND limite > -(saldo + :valor)
			RETURNING *
		)
		INSERT INTO transacao (id_cliente, valor, tipo, descricao, realizado_em, saldo)
		SELECT id, :valor, :tipo, :descricao, now(), saldo from updated RETURNING *
	""", nativeQuery = true)
	Optional<TransacaoEntity> adicionarTransacao(int idCliente, long valor, char tipo, String descricao);

	@Query(value = "SELECT * FROM transacao WHERE id_cliente = :idCliente ORDER BY transacao.id DESC LIMIT 10", nativeQuery = true)
	List<TransacaoEntity> findAllByIdCliente(int idCliente);
}
