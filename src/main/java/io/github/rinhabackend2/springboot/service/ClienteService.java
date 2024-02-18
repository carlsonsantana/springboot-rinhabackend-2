package io.github.rinhabackend2.springboot.service;

import org.springframework.stereotype.Service;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.entity.TransacaoEntity;
import io.github.rinhabackend2.springboot.exceptions.ClienteNaoEncontradoException;
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

	public ResponseTransacaoDTO cadastrarTransacao(int idCliente, RequestTransacaoDTO transacao) {
		var cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoEncontradoException::new);
		transacaoRepository.save(new TransacaoEntity(transacao.valor(), transacao.tipo(), transacao.descricao()));

		return new ResponseTransacaoDTO(cliente.getLimite(), cliente.getSaldo());
	}
}
