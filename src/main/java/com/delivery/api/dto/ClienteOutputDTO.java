package com.delivery.api.dto;

import lombok.Data;

@Data
public class ClienteOutputDTO {
	private String uuid;
	private String nome;
	private String endereco;
	private String telefone;
}
