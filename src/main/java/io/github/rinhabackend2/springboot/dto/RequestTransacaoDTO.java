package io.github.rinhabackend2.springboot.dto;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;

public record RequestTransacaoDTO(long valor, TipoTransacao tipo, String descricao) {

}
