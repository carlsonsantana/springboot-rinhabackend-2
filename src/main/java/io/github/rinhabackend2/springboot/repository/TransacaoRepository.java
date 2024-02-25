package io.github.rinhabackend2.springboot.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import io.github.rinhabackend2.springboot.entity.TransacaoEntity;
import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import io.github.rinhabackend2.springboot.exceptions.NovoSaldoInvalidoException;

@Component
public class TransacaoRepository {
	private static final String QUERY_ADICIONAR_TRANSACAO = """
				WITH updated as (
					UPDATE cliente SET saldo = saldo + ?
					WHERE
						id = ?
						AND limite > -(saldo + ?)
					RETURNING *
				)
				INSERT INTO transacao (id_cliente, valor, tipo, descricao, realizado_em, saldo)
				SELECT id, ABS(?), ?, ?, now(), saldo from updated RETURNING saldo
			""";
	private static final String QUERY_EXTRATO = """
				SELECT id_cliente, valor, tipo, descricao, realizado_em, saldo FROM transacao
				WHERE
					id_cliente = ?
				ORDER BY transacao.id DESC LIMIT 10
			""";
	private final JdbcTemplate jdbcTemplate;
	private final TransacaoRowMapper transacaoRowMapper;

	public TransacaoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

		this.transacaoRowMapper = new TransacaoRowMapper();
	}

	private static class TransacaoRowMapper implements RowMapper<TransacaoEntity> {
		@Override
		public TransacaoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
			var tipoTransacao = rs.getString(3).equals("c") ? TipoTransacao.CREDITO : TipoTransacao.DEBITO;

			return new TransacaoEntity(rs.getInt(1), rs.getLong(2), tipoTransacao, rs.getString(4),
					rs.getObject(5, OffsetDateTime.class), rs.getLong(6));
		}
	}

	public long adicionarTransacao(int idCliente, long valor, String tipo, String descricao) {
		try {
			return jdbcTemplate.queryForObject(QUERY_ADICIONAR_TRANSACAO, Long.class, valor, idCliente, valor, valor,
					tipo, descricao).longValue();
		} catch (EmptyResultDataAccessException e) {
			throw new NovoSaldoInvalidoException();
		}
	}

	public List<TransacaoEntity> findAllByIdCliente(int idCliente) {
		return jdbcTemplate.query(QUERY_EXTRATO, transacaoRowMapper, idCliente);
	}
}
