package com.bradesco.consultaendereco.controller;

import com.bradesco.consultaendereco.entity.request.ConsultaEnderecoRequest;
import com.bradesco.consultaendereco.entity.response.ConsultaEnderecoResponse;
import com.bradesco.consultaendereco.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * EnderecoController
 * Esta controller vai conter operacoes relacionadas a endereco
 */
@RestController
@RequestMapping("/v1")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/consulta-endereco")
    public ResponseEntity<ConsultaEnderecoResponse> buscarEndereco(@RequestBody ConsultaEnderecoRequest consultaEnderecoRequest) {
        try {
            ConsultaEnderecoResponse consultaEnderecoResponse = enderecoService.consultaEndereco(consultaEnderecoRequest.getCep());
            return ResponseEntity.ok(consultaEnderecoResponse);
        } catch(HttpClientErrorException exception) {
            if(exception.getStatusCode().is4xxClientError()) {
                return ResponseEntity.status(HttpStatus.resolve(exception.getStatusCode().value())).body(new ConsultaEnderecoResponse());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ConsultaEnderecoResponse());
        }
    }
}
