package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoItemOutputDTO;
import com.delivery.api.entity.PedidoItem;

@Component
public class PedidoItemOutputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoItemOutputDTO mapearEntity(PedidoItem pedidoItem) {		
		return modelMapper.map(pedidoItem, PedidoItemOutputDTO.class);
	}
	
	public List<PedidoItemOutputDTO> mapearCollection(List<PedidoItem> pedidoItens) {
		return pedidoItens.stream()
			.map(pedidoItem -> mapearEntity(pedidoItem))
			.collect(Collectors.toList());
	}
}
