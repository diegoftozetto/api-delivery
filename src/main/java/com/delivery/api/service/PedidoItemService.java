package com.delivery.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.PedidoItem;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.PedidoItemRepository;
import com.delivery.api.utils.Utils;

@Service
public class PedidoItemService {

	@Autowired
	private PedidoItemRepository pedidoItemRepository;

	public List<PedidoItem> listarItensDoPedido(String uuid) {
		return pedidoItemRepository.selectByItensDoPedidoUuid(uuid);
	}

	public PedidoItem buscarPorUUID(String uuid) {
		Optional<PedidoItem> pedidoItem = pedidoItemRepository.findByUuid(uuid);

		if (!pedidoItem.isPresent()) {
			throw new NotFoundException("Item do pedido não encontrado!");
		}
		return pedidoItem.get();
	}

	public PedidoItem salvar(PedidoItem pedidoItem) {
		return pedidoItemRepository.save(pedidoItem);
	}

	public PedidoItem atualizar(String uuid, PedidoItem pedidoItem) {
		PedidoItem pedidoItemAtual = this.buscarPorUUID(uuid);

		BeanUtils.copyProperties(pedidoItem, pedidoItemAtual, "id", "uuid", "pedido");

		return this.salvar(pedidoItemAtual);
	}

	public PedidoItem ajustar(String uuid, Map<String, Object> campos) {
		PedidoItem pedidoItemAtual = this.buscarPorUUID(uuid);

		Utils.merge(pedidoItemAtual, campos);

		pedidoItemAtual = this.salvar(pedidoItemAtual);

		return pedidoItemAtual;
	}

	public boolean excluir(String uuid) {
		if (!pedidoItemRepository.existsByUuid(uuid)) {
			throw new NotFoundException("Item do pedido não encontrado!");
		}
		pedidoItemRepository.deleteByUuid(uuid);

		return true;
	}

}
