package io.github.rinhabackend2.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
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

		transacaoRepository.save(new TransacaoEntity(transacao.valor(), transacao.tipo(), transacao.descricao()));
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
}
