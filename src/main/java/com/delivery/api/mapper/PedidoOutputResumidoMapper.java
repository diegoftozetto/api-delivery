package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoOutputResumidoDTO;
import com.delivery.api.entity.Pedido;

@Component
public class PedidoOutputResumidoMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoOutputResumidoDTO mapearEntity(Pedido pedido) {		
		return modelMapper.map(pedido, PedidoOutputResumidoDTO.class);
	}
	
	public List<PedidoOutputResumidoDTO> mapearCollection(List<Pedido> pedidos) {
		return pedidos.stream()
			.map(pedido -> mapearEntity(pedido))
			.collect(Collectors.toList());
	}
}
