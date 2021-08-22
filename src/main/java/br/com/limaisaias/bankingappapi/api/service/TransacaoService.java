package br.com.limaisaias.bankingappapi.api.service;

import br.com.limaisaias.bankingappapi.api.model.Transacao;

import java.util.Optional;

public interface TransacaoService {

    Transacao save(Transacao transacao);

    Optional<Transacao> getById(Long id);

    void delete(Transacao transacao);

    Transacao update(Transacao transacao);


}
