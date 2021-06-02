package com.delivery.api.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
	private String cep;
	private String logradouro;
	private String bairro;
	private String uf;
	private String localidade;
}
