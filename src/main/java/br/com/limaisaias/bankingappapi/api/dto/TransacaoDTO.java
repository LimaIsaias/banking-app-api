package br.com.limaisaias.bankingappapi.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TransacaoDTO {

    private Long id;

    @NotEmpty
    private ContaDTO contaRemetente;
    @NotEmpty
    private ContaDTO contaDestinatario;
    @NotNull
    private Double valor;
    @NotNull
    private LocalDate dataTransacao;

}
