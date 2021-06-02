package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoItemOutputDTO {
	private String uuid;
	private Integer quantidade;
	private BigDecimal subtotal;
	private String observacoes;
}
