package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.ProdutoInputDTO;
import com.delivery.api.entity.Produto;

@Component
public class ProdutoInputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public Produto mapearEntity(ProdutoInputDTO produtoInputDTO) {		
		return modelMapper.map(produtoInputDTO, Produto.class);
	}
	
	public List<Produto> mapearCollection(List<ProdutoInputDTO> produtoInputDTOs) {
		return produtoInputDTOs.stream()
			.map(produtoInputDTO -> mapearEntity(produtoInputDTO))
			.collect(Collectors.toList());
	}
}
