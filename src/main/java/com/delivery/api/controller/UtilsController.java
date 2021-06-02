package com.delivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.dto.EnderecoDTO;
import com.delivery.api.service.CEPService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/utils")
@Tag(name = "Utilidades", description = "Acesso a diferentes serviços da plataforma")
public class UtilsController {

	@Autowired
	private CEPService cepService;

	@Operation(summary = "Obter informações pelo CEP")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CEP encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "CEP não encontrado", content = @Content) })
	@GetMapping("/cep/{cep}")
	@ResponseStatus(code = HttpStatus.OK)
	public EnderecoDTO consultarCEP(@PathVariable String cep) {
		return cepService.consultarCEP(cep);
	}
}
