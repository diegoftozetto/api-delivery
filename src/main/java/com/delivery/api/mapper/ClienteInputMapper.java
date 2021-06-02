package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.ClienteInputDTO;
import com.delivery.api.entity.Cliente;

@Component
public class ClienteInputMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public Cliente mapearEntity(ClienteInputDTO clienteInputDTO) {		
		return modelMapper.map(clienteInputDTO, Cliente.class);
	}
	
	public List<Cliente> mapearCollection(List<ClienteInputDTO> clienteInputDTOs) {
		return clienteInputDTOs.stream()
			.map(clienteInputDTO -> mapearEntity(clienteInputDTO))
			.collect(Collectors.toList());
	}
}
