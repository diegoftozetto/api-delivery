package com.delivery.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.Produto;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.ProdutoRepository;
import com.delivery.api.utils.Utils;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	public List<Produto> listarPorRestaurante(String uuid) {
		return produtoRepository.selectByRestauranteUuid(uuid);
	}

	public Produto buscarPorUUID(String uuid) {
		Optional<Produto> produto = produtoRepository.findByUuid(uuid);

		if (!produto.isPresent()) {
			throw new NotFoundException("Produto não encontrado!");
		}
		return produto.get();
	}

	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto atualizar(String uuid, Produto produto) {
		Produto produtoAtual = this.buscarPorUUID(uuid);

		BeanUtils.copyProperties(produto, produtoAtual, "id", "uuid");

		return this.salvar(produtoAtual);
	}

	public Produto ajustar(String uuid, Map<String, Object> campos) {

		Produto produtoAtual = this.buscarPorUUID(uuid);

		Utils.merge(produtoAtual, campos);

		produtoAtual = this.salvar(produtoAtual);

		return produtoAtual;
	}

	public boolean excluir(String uuid) {
		if (!produtoRepository.existsByUuid(uuid)) {
			throw new NotFoundException("Produto não encontrado!");
		}
		produtoRepository.deleteByUuid(uuid);

		return true;
	}

}
