package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoItemOutputResumidoDTO {
	private String uuid;
	private Integer quantidade;
	private BigDecimal subtotal;
}
