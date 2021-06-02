package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.RestauranteOutputResumidoDTO;
import com.delivery.api.entity.Restaurante;

@Component
public class RestauranteOutputResumidoMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteOutputResumidoDTO mapearEntity(Restaurante restaurante) {		
		return modelMapper.map(restaurante, RestauranteOutputResumidoDTO.class);
	}
	
	public List<RestauranteOutputResumidoDTO> mapearCollection(List<Restaurante> restaurantes) {
		return restaurantes.stream()
			.map(restaurante -> mapearEntity(restaurante))
			.collect(Collectors.toList());
	}
}
