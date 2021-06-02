package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.ProdutoOutputResumidoDTO;
import com.delivery.api.entity.Produto;

@Component
public class ProdutoOutputResumidoMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public ProdutoOutputResumidoDTO mapearEntity(Produto produto) {		
		return modelMapper.map(produto, ProdutoOutputResumidoDTO.class);
	}
	
	public List<ProdutoOutputResumidoDTO> mapearCollection(List<Produto> produtos) {
		return produtos.stream()
			.map(produto -> mapearEntity(produto))
			.collect(Collectors.toList());
	}
}
