package io.github.rinhabackend2.springboot.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseExtratoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.exceptions.ClienteNaoEncontradoException;
import io.github.rinhabackend2.springboot.repository.TransacaoRepository;

@Service
public class ClienteService {
	private final TransacaoRepository transacaoRepository;

	public ClienteService(TransacaoRepository transacaoRepository) {
		this.transacaoRepository = transacaoRepository;
	}

	public ResponseTransacaoDTO cadastrarTransacao(int idCliente, RequestTransacaoDTO transacao) {
		var limite = getLimite(idCliente);
		var saldo = calcularNovoSaldo(idCliente, transacao);

		return new ResponseTransacaoDTO(limite, saldo);
	}

	private long calcularNovoSaldo(int idCliente, RequestTransacaoDTO transacao) {
		var valor = switch (transacao.tipo()) {
			case CREDITO -> transacao.valor();
			case DEBITO -> -transacao.valor();
		};

		return transacaoRepository.adicionarTransacao(idCliente, valor, transacao.tipo().tipo(), transacao.descricao());
	}

	public ResponseExtratoDTO gerarExtrato(int idCliente) {
		var limite = getLimite(idCliente);

		var ultimasTransacoes = transacaoRepository.findAllByIdCliente(idCliente);

		var saldo = 0l;
		if (!ultimasTransacoes.isEmpty()) {
			saldo = ultimasTransacoes.get(0).saldo();
		}
		var saldoExtrato = new ResponseExtratoDTO.SaldoExtratoDTO(saldo, OffsetDateTime.now(), limite);
		var ultimasTransacoesExtrato = ultimasTransacoes.stream()
				.map(transacao -> new ResponseExtratoDTO.TransacaoExtratoDTO(transacao.valor(), transacao.tipo(),
						transacao.descricao(), transacao.realizadoEm()))
				.toList();

		return new ResponseExtratoDTO(saldoExtrato, ultimasTransacoesExtrato);
	}

	private int getLimite(int idCliente) {
		return switch (idCliente) {
			case 1 -> 100000;
			case 2 -> 80000;
			case 3 -> 1000000;
			case 4 -> 10000000;
			case 5 -> 500000;
			default -> throw new ClienteNaoEncontradoException();
		};
	}
}
