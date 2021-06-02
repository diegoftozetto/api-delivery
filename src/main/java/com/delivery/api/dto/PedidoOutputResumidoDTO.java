package com.delivery.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PedidoOutputResumidoDTO {
	private String uuid;
	private BigDecimal total;
}
