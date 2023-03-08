package com.bradesco.consultaendereco.entity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "CEP deve ser preenchido")
    @Size(min = 8, max = 8)
    private String cep;
}
