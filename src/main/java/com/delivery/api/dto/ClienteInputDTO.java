package com.delivery.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ClienteInputDTO {
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@NotBlank
	private String telefone;
}