package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteOutputDTO {
	private String uuid;
	private String nome;
	private BigDecimal taxaFrete;
	private String cep;
	private String rua;
	private Integer numero;
	private String bairro;
	private String complemento;
}
