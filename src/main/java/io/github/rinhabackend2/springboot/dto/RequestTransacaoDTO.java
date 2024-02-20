package io.github.rinhabackend2.springboot.dto;

import io.github.rinhabackend2.springboot.enums.TipoTransacao;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestTransacaoDTO(@NotNull @Min(0) Long valor, @NotNull TipoTransacao tipo, @NotEmpty @Size(min = 1, max = 10) String descricao) {
}
