package br.com.limaisaias.bankingappapi.api.service.impl;

import br.com.limaisaias.bankingappapi.api.model.Conta;
import br.com.limaisaias.bankingappapi.api.repository.ContaRepository;
import br.com.limaisaias.bankingappapi.api.service.ContaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaServiceImpl implements ContaService {

    public ContaServiceImpl(ContaRepository repository) {
        this.repository = repository;
    }

    private ContaRepository repository;

    @Override
    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    @Override
    public Optional<Conta> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Conta conta) {
        validateConta(conta);
        this.repository.delete(conta);
    }

    @Override
    public Conta update(Conta conta) {
        validateConta(conta);
        return repository.save(conta);
    }

    private void validateConta(Conta conta) {
        if (conta == null || conta.getId() == null) {
            throw new IllegalArgumentException("Conta id cant be null.");
        }
    }

    @Override
    public Page<Conta> find(Conta filter, Pageable pageRequest) {
        Example<Conta> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
        );
        return repository.findAll(example, pageRequest);
    }
}
