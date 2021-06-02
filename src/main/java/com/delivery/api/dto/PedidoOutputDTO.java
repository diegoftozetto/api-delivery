package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoOutputDTO {
	private String uuid;
	private BigDecimal total;
	private String descricao;
}
