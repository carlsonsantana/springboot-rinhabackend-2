package io.github.rinhabackend2.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.rinhabackend2.springboot.dto.RequestTransacaoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseExtratoDTO;
import io.github.rinhabackend2.springboot.dto.ResponseTransacaoDTO;
import io.github.rinhabackend2.springboot.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}

	@PostMapping("/{idCliente}/transacoes")
	public ResponseTransacaoDTO cadastrarTransacao(@PathVariable int idCliente,
			@Valid @RequestBody RequestTransacaoDTO transacao) {
		return service.cadastrarTransacao(idCliente, transacao);
	}

	@GetMapping("/{idCliente}/extrato")
	public ResponseExtratoDTO gerarExtrato(@PathVariable int idCliente) {
		return service.gerarExtrato(idCliente);
	}

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentNotValidException.class })
	public void handleException(Exception ex) {
	}
}
