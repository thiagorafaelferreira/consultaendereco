package com.bradesco.consultaendereco.service;

import com.bradesco.consultaendereco.entity.enums.Regiao;
import com.bradesco.consultaendereco.entity.viacep.Endereco;
import com.bradesco.consultaendereco.entity.response.ConsultaEnderecoResponse;
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

@Slf4j
@Service
public class EnderecoService {

    private final RestTemplate restTemplate;

    public EnderecoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConsultaEnderecoResponse consultaEndereco(String cep) {
        String url = "http://viacep.com.br/ws/{cep}/json/";
        Map<String, String> params = new HashMap<>();
        params.put("cep", cep);

        try {
            ResponseEntity<Endereco> enderecoResponse = restTemplate.exchange(url, HttpMethod.GET, null, Endereco.class, params);

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

        for(Regiao regiao : REGIAO_ESTADO.keySet()) {
            if(REGIAO_ESTADO.get(regiao).contains(estado)) {
               return REGIAO_PRECO.get(regiao);
            }
        }

        return null;
    }
}
