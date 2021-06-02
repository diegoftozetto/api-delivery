package com.delivery.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.Cliente;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.ClienteRepository;
import com.delivery.api.utils.Utils;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente buscarPorUUID(String uuid) {
		Optional<Cliente> cliente = clienteRepository.findByUuid(uuid);

		if (!cliente.isPresent()) {
			throw new NotFoundException("Cliente não encontrado!");
		}
		return cliente.get();
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente atualizar(String uuid, Cliente cliente) {
		Cliente clienteAtual = this.buscarPorUUID(uuid);

		BeanUtils.copyProperties(cliente, clienteAtual, "id", "uuid");

		return this.salvar(clienteAtual);
	}

	public Cliente ajustar(String uuid, Map<String, Object> changes) {
		Cliente clienteAtual = this.buscarPorUUID(uuid);

		Utils.merge(clienteAtual, changes);

		clienteAtual = this.salvar(clienteAtual);

		return clienteAtual;
	}

	public boolean excluir(String uuid) {
		if (!clienteRepository.existsByUuid(uuid)) {
			throw new NotFoundException("Cliente não encontrado!");
		}
		clienteRepository.deleteByUuid(uuid);

		return true;
	}
}
