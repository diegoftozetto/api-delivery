package com.delivery.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.delivery.api.dto.ClienteOutputResumidoDTO;
import com.delivery.api.entity.Cliente;

@Component
public class ClienteOutputResumidoMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	public ClienteOutputResumidoDTO mapearEntity(Cliente cliente) {		
		return modelMapper.map(cliente, ClienteOutputResumidoDTO.class);
	}
	
	public List<ClienteOutputResumidoDTO> mapearCollection(List<Cliente> clientes) {
		return clientes.stream()
			.map(cliente -> mapearEntity(cliente))
			.collect(Collectors.toList());
	}
}
