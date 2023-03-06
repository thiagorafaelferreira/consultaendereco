package com.bradesco.consultaendereco.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * EnderecoResponse
 * Representa a informacao de endereco retornada pela api via cep
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class ConsultaEnderecoResponse {
    public String cep;
    public String rua;
    public String complemento;
    public String bairro;
    public String estado;
    private String cidade;
    private BigDecimal preco;
}
