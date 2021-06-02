package com.delivery.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class PedidoInputDTO {
	@NotNull
	@DecimalMin(value = "0.00", inclusive = false)
	@Digits(integer = 15, fraction = 2)
	private BigDecimal total;
	private String descricao;
}
