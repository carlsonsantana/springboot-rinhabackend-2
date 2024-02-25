package io.github.rinhabackend2.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseExtratoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.exceptions.PayloadInvalidoException;
import io.github.rinhabackend2.springboot.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private static final List<String> TIPOS_PERMITIDOS = List.of("c", "d");
	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}

	@PostMapping("/{idCliente}/transacoes")
	public ResponseTransacaoDTO cadastrarTransacao(@PathVariable int idCliente,
			@RequestBody RequestTransacaoDTO transacao) {
		var valor = transacao.valor();
		var descricao = transacao.descricao();
		if ((valor == null) || (valor < 0) || (!TIPOS_PERMITIDOS.contains(transacao.tipo())) || (descricao == null)
				|| (descricao.isEmpty()) || (descricao.length() > 10)) {
			throw new PayloadInvalidoException();
		}

		return service.cadastrarTransacao(idCliente, transacao);
	}

	@GetMapping("/{idCliente}/extrato")
	public ResponseExtratoDTO gerarExtrato(@PathVariable int idCliente) {
		return service.gerarExtrato(idCliente);
	}
}
