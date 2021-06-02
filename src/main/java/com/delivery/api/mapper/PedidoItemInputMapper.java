package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.delivery.api.dto.PedidoItemInputDTO;
import com.delivery.api.entity.PedidoItem;

@Component
public class PedidoItemInputMapper {
	
	public PedidoItem mapearEntity(PedidoItemInputDTO pedidoItemInputDTO) {
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setQuantidade(pedidoItemInputDTO.getQuantidade());
		pedidoItem.setSubtotal(pedidoItemInputDTO.getSubtotal());
		pedidoItem.setObservacoes(pedidoItemInputDTO.getObservacoes());	
				
		return pedidoItem;
	}
	
	public List<PedidoItem> mapearCollection(List<PedidoItemInputDTO> pedidoItemInputDTOs) {
		return pedidoItemInputDTOs.stream()
			.map(pedidoItemInputDTO -> mapearEntity(pedidoItemInputDTO))
			.collect(Collectors.toList());
	}
}
