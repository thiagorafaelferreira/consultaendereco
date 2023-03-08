package com.bradesco.consultaendereco.service;

import com.bradesco.consultaendereco.entity.enums.Regiao;
import com.bradesco.consultaendereco.entity.response.ConsultaEnderecoResponse;
import com.bradesco.consultaendereco.entity.viacep.Endereco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.bradesco.consultaendereco.entity.Constantes.REGIAO_ESTADO;
import static com.bradesco.consultaendereco.entity.Constantes.REGIAO_PRECO;

/**
 * EnderecoService
 * Responsavel por processar informacoes de endereco
 */
@Slf4j
@Service
public class EnderecoService {

    private static final String URL = "http://viacep.com.br/ws/{cep}/json/";
    private final RestTemplate restTemplate;

    public EnderecoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConsultaEnderecoResponse consultaEndereco(String cep) {
        Map<String, String> params = new HashMap<>() {{
            put("cep", cep);
        }};

        try {
            ResponseEntity<Endereco> enderecoResponse = restTemplate.exchange(URL, HttpMethod.GET, null, Endereco.class, params);

            Endereco endereco = enderecoResponse.getBody();

            return ConsultaEnderecoResponse.builder()
                    .bairro(endereco.getBairro())
                    .estado(endereco.getUf())
                    .complemento(endereco.getComplemento())
                    .rua(endereco.getLogradouro())
                    .cidade(endereco.getLocalidade())
                    .cep(endereco.getCep())
                    .preco(calculaPreco(endereco.getUf()))
                    .build();

        } catch(HttpClientErrorException exception) {
            if(exception.getStatusCode().is4xxClientError()) {
                log.error("Falha ao requisitar API via cep, Bad Request");
                throw exception;
            }
            if(exception.getStatusCode().is5xxServerError()) {
                log.error("Falha ao requisitar API via cep, Internal Server Error");
                throw exception;
            }
            throw exception;
        }
    }

    private BigDecimal calculaPreco(String estado) {
        if (estado == null || estado == "") return null;

        for(Map.Entry<Regiao, String> regiaoEstado: REGIAO_ESTADO.entrySet()) {
            if(regiaoEstado.getValue().contains(estado)) {
               return REGIAO_PRECO.get(regiaoEstado.getKey());
            }
        }

        return null;
    }
}
