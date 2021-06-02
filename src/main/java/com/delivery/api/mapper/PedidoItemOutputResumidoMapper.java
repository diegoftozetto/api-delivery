package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoItemOutputResumidoDTO;
import com.delivery.api.entity.PedidoItem;

@Component
public class PedidoItemOutputResumidoMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoItemOutputResumidoDTO mapearEntity(PedidoItem pedidoItem) {		
		return modelMapper.map(pedidoItem, PedidoItemOutputResumidoDTO.class);
	}
	
	public List<PedidoItemOutputResumidoDTO> mapearCollection(List<PedidoItem> pedidoItens) {
		return pedidoItens.stream()
			.map(pedidoItem -> mapearEntity(pedidoItem))
			.collect(Collectors.toList());
	}
}
