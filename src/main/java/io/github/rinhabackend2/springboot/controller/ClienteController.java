package io.github.rinhabackend2.springboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}

	@PostMapping("/{idCliente}/transacoes")
	public ResponseTransacaoDTO cadastrarTransacao(@PathVariable int idCliente,
			@RequestBody RequestTransacaoDTO transacao) {
		return service.cadastrarTransacao(idCliente, transacao);
	}
}
