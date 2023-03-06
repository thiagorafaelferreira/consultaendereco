package com.bradesco.consultaendereco.entity.viacep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Endereco
 * Representa a informacao de endereco retornada pela api via cep
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Endereco {
    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;
    public String ibge;
    public String gia;
    public String ddd;
    public String siafi;
}
