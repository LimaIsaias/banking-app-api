package br.com.limaisaias.bankingappapi.api.service;

import br.com.limaisaias.bankingappapi.api.model.Conta;
import br.com.limaisaias.bankingappapi.api.repository.ContaRepository;
import br.com.limaisaias.bankingappapi.api.service.impl.ContaServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContaServiceTest {

    ContaService contaService;

    @MockBean
    ContaRepository repository;

    @BeforeEach
    public void setUp(){
        this.contaService = new ContaServiceImpl(repository) ;
    }

    @Test
    @DisplayName("Deve salvar uma conta")
    public void saveConta() {
        Conta conta = Conta.builder().conta("12345").agencia("0001").digito("2").saldo(01.10).build();
        Mockito.when(repository.save(conta)).thenReturn(Conta.builder().id(11L).conta("12345").agencia("0001").digito("2").saldo(01.10).build());
        Conta savedConta = contaService.save(conta);
        Assertions.assertThat(savedConta.getId()).isNotNull();
        Assertions.assertThat(savedConta.getConta()).isNotNull();
    }
}
