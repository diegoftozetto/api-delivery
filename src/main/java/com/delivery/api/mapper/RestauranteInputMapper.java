package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.RestauranteInputDTO;
import com.delivery.api.entity.Restaurante;

@Component
public class RestauranteInputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante mapearEntity(RestauranteInputDTO restauranteInputDTO) {		
		return modelMapper.map(restauranteInputDTO, Restaurante.class);
	}
	
	public List<Restaurante> mapearCollection(List<RestauranteInputDTO> restauranteInputDTOs) {
		return restauranteInputDTOs.stream()
			.map(restauranteInputDTO -> mapearEntity(restauranteInputDTO))
			.collect(Collectors.toList());
	}
}
