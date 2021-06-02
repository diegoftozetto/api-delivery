package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoInputDTO;
import com.delivery.api.entity.Pedido;

@Component
public class PedidoInputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public Pedido mapearEntity(PedidoInputDTO pedidoInputDTO) {		
		return modelMapper.map(pedidoInputDTO, Pedido.class);
	}
	
	public List<Pedido> mapearCollection(List<PedidoInputDTO> pedidoInputDTOs) {
		return pedidoInputDTOs.stream()
			.map(pedidoInputDTO -> mapearEntity(pedidoInputDTO))
			.collect(Collectors.toList());
	}
}
