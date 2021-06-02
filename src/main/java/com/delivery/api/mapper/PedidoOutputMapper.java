package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoOutputDTO;
import com.delivery.api.entity.Pedido;

@Component
public class PedidoOutputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoOutputDTO mapearEntity(Pedido pedido) {		
		return modelMapper.map(pedido, PedidoOutputDTO.class);
	}
	
	public List<PedidoOutputDTO> mapearCollection(List<Pedido> pedidos) {
		return pedidos.stream()
			.map(pedido -> mapearEntity(pedido))
			.collect(Collectors.toList());
	}
}
