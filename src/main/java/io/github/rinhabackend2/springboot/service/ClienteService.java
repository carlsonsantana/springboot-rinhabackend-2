package io.github.rinhabackend2.springboot.service;

import org.springframework.stereotype.Service;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.exceptions.ClienteNaoEncontradoException;
import io.github.rinhabackend2.springboot.repository.ClienteRepository;

@Service
public class ClienteService {
	private final ClienteRepository repository;

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}

	public ResponseTransacaoDTO cadastrarTransacao(int idCliente, RequestTransacaoDTO transacao) {
		var cliente = repository.findById(idCliente).orElseThrow(ClienteNaoEncontradoException::new);

		return new ResponseTransacaoDTO(cliente.getLimite(), cliente.getSaldo());
	}
}
