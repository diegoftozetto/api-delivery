package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.ClienteOutputDTO;
import com.delivery.api.entity.Cliente;

@Component
public class ClienteOutputMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteOutputDTO mapearEntity(Cliente cliente) {		
		return modelMapper.map(cliente, ClienteOutputDTO.class);
	}
	
	public List<ClienteOutputDTO> mapearCollection(List<Cliente> clientes) {
		return clientes.stream()
			.map(cliente -> mapearEntity(cliente))
			.collect(Collectors.toList());
	}
}
