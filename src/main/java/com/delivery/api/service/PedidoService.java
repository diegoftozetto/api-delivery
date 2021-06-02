package com.delivery.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.Pedido;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.PedidoRepository;
import com.delivery.api.utils.Utils;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}

	public List<Pedido> listarPorCliente(String uuid) {
		return pedidoRepository.selectByClienteUuid(uuid);
	}

	public List<Pedido> listarPorRestaurante(String uuid) {
		return pedidoRepository.selectByRestauranteUuid(uuid);
	}

	public Pedido buscarPorUUID(String uuid) {
		Optional<Pedido> pedido = pedidoRepository.findByUuid(uuid);

		if (!pedido.isPresent()) {
			throw new NotFoundException("Pedido não encontrado!");
		}
		return pedido.get();
	}

	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	public Pedido atualizar(String uuid, Pedido pedido) {
		Pedido pedidoAtual = this.buscarPorUUID(uuid);

		BeanUtils.copyProperties(pedido, pedidoAtual, "id", "uuid");

		return this.salvar(pedidoAtual);
	}

	public Pedido ajustar(String uuid, Map<String, Object> campos) {

		Pedido pedidoAtual = this.buscarPorUUID(uuid);

		Utils.merge(pedidoAtual, campos);

		pedidoAtual = this.salvar(pedidoAtual);

		return pedidoAtual;
	}

	public boolean excluir(String uuid) {
		if (!pedidoRepository.existsByUuid(uuid)) {
			throw new NotFoundException("Pedido não encontrado!");
		}
		pedidoRepository.deleteByUuid(uuid);

		return true;
	}

}
