package com.bradesco.consultaendereco.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConsultaEnderecoRequest
 * Objeto que define as informacoes para consultar um endereco
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEnderecoRequest {

    private String cep;
}
