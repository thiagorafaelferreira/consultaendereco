package com.bradesco.consultaendereco.service;

import com.bradesco.consultaendereco.entity.response.ConsultaEnderecoResponse;
import com.bradesco.consultaendereco.entity.viacep.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private RestTemplate restTemplate;
    private EnderecoService enderecoService;

    @BeforeEach
    public void setUp() {
        enderecoService = new EnderecoService(restTemplate);
    }
    @Test
    void consultaEndereco_retrieveData() {
        String cep = "11111111";

        String url = "http://viacep.com.br/ws/{cep}/json/";
        Map<String, String> params = new HashMap<>();
        params.put("cep", cep);

        Endereco endereco = Endereco.builder()
                .cep("11111111")
                .uf("RS")
                .localidade("Canoas")
                .logradouro("Rua dos Sabiás")
                .bairro("Igara III")
                .complemento("")
                .build();

        ConsultaEnderecoResponse expected = ConsultaEnderecoResponse.builder()
                .cep("11111111")
                .estado("RS")
                .cidade("Canoas")
                .rua("Rua dos Sabiás")
                .bairro("Igara III")
                .complemento("")
                .preco(new BigDecimal("17.30"))
                .build();

        when(restTemplate.exchange(url, HttpMethod.GET, null, Endereco.class, params)).thenReturn(ResponseEntity.ok(endereco));

        ConsultaEnderecoResponse consultaEnderecoResponse = enderecoService.consultaEndereco(cep);

        assertEquals(expected, consultaEnderecoResponse);
    }
}
