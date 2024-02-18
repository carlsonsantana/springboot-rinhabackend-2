package io.github.rinhabackend2.springboot.service;

import java.time.OffsetDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseExtratoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.entity.ClienteEntity;
import io.github.rinhabackend2.springboot.entity.TransacaoEntity;
import io.github.rinhabackend2.springboot.exceptions.ClienteNaoEncontradoException;
import io.github.rinhabackend2.springboot.exceptions.NovoSaldoInvalidoException;
import io.github.rinhabackend2.springboot.repository.ClienteRepository;
import io.github.rinhabackend2.springboot.repository.TransacaoRepository;

@Service
public class ClienteService {
	private final ClienteRepository clienteRepository;
	private final TransacaoRepository transacaoRepository;

	public ClienteService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
		this.clienteRepository = clienteRepository;
		this.transacaoRepository = transacaoRepository;
	}

	@Transactional
	public ResponseTransacaoDTO cadastrarTransacao(int idCliente, RequestTransacaoDTO transacao) {
		var cliente = clienteRepository.findByIdWithLock(idCliente).orElseThrow(ClienteNaoEncontradoException::new);

		cliente.setSaldo(calcularNovoSaldo(cliente, transacao));

		transacaoRepository
		.save(new TransacaoEntity(idCliente, transacao.valor(), transacao.tipo(), transacao.descricao()));
		clienteRepository.save(cliente);

		return new ResponseTransacaoDTO(cliente.getLimite(), cliente.getSaldo());
	}

	private long calcularNovoSaldo(ClienteEntity cliente, RequestTransacaoDTO transacao) {
		var novoSaldo = switch (transacao.tipo()) {
			case CREDITO -> cliente.getSaldo() + transacao.valor();
			case DEBITO -> cliente.getSaldo() - transacao.valor();
		};

		if (-novoSaldo > cliente.getLimite()) {
			throw new NovoSaldoInvalidoException();
		}

		return novoSaldo;
	}

	public ResponseExtratoDTO gerarExtrato(int idCliente) {
		var cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoEncontradoException::new);
		var ultimasTransacoes = transacaoRepository.findAllByIdCliente(idCliente,
				PageRequest.of(0, 10, Sort.by("realizadoEm").descending()));

		var saldoExtrato = new ResponseExtratoDTO.SaldoExtratoDTO(cliente.getSaldo(), OffsetDateTime.now(), cliente.getLimite());
		var ultimasTransacoesExtrato = ultimasTransacoes.stream()
				.map(transacao -> new ResponseExtratoDTO.TransacaoExtratoDTO(transacao.getValor(), transacao.getTipo(),
						transacao.getDescricao(), transacao.getRealizadoEm()))
				.toList();

		return new ResponseExtratoDTO(saldoExtrato, ultimasTransacoesExtrato);
	}
}
