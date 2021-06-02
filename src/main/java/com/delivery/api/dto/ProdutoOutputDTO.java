package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProdutoOutputDTO {
	private String uuid;
	private String nome;
	private BigDecimal preco;
	private String descricao;
}
