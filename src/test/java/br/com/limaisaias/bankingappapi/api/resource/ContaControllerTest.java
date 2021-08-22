package br.com.limaisaias.bankingappapi.api.resource;

import br.com.limaisaias.bankingappapi.api.dto.ContaDTO;
import br.com.limaisaias.bankingappapi.api.model.Conta;
import br.com.limaisaias.bankingappapi.api.service.ContaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.print.Book;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") //apenas em ambiente de test
@WebMvcTest
@AutoConfigureMockMvc //configura mocks
public class ContaControllerTest {

    static String CONTA_API = "/api/contas";

    @Autowired
    MockMvc mvc;

    @MockBean
    ContaService contaService;

    @Test
    @DisplayName("Deve criar uma conta com sucesso.")
    public void createContaTest() throws Exception {
        ContaDTO dto = createNewContaDto();
        Conta savedConta = createNewConta();

        BDDMockito.given(contaService.save(Mockito.any(Conta.class))).willReturn(savedConta);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = post(CONTA_API).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve lançar erro quando não houver informação suficiente para criacao da conta.")
    public void createInvalidContaTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new ContaDTO());

        MockHttpServletRequestBuilder req = post(CONTA_API).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(req).andExpect(status().isBadRequest()).andExpect(jsonPath("errors", hasSize(3)));
    }

    @Test
    @DisplayName("Deve atualizar uma conta.")
    public void updateContaRest() throws Exception {

        Long id = 11l;
        String json = new ObjectMapper().writeValueAsString(createNewConta());

        Conta updatingConta = createNewConta();
        updatingConta.setConta("54321");
        BDDMockito.given(contaService.getById(id))
                .willReturn(Optional.of(updatingConta));

        Conta updatedConta = Conta.builder().id(1l).id(11L).conta("12345").agencia("0001").saldo(0.0).digito("6").build();

        BDDMockito.given(contaService.update(updatingConta)).willReturn(updatedConta);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(CONTA_API.concat("/" + 11))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));
    }

    private ContaDTO createNewContaDto() {
        return ContaDTO.builder().conta("12345").agencia("0001").saldo(0.0).digito("6").build();
    }

    private Conta createNewConta() {
        return Conta.builder().id(11L).conta("12345").agencia("0001").saldo(0.0).digito("6").build();
    }
}
