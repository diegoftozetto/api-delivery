package com.delivery.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.Restaurante;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.RestauranteRepository;
import com.delivery.api.utils.Utils;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscarPorUUID(String uuid) {
		Optional<Restaurante> restaurante = restauranteRepository.findByUuid(uuid);

		if (!restaurante.isPresent()) {
			throw new NotFoundException("Restaurante não encontrado!");
		}
		return restaurante.get();
	}

	public Restaurante salvar(Restaurante restaurante) {
		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(String uuid, Restaurante restaurante) {
		Restaurante restauranteAtual = this.buscarPorUUID(uuid);

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "uuid");

		return this.salvar(restauranteAtual);
	}

	public Restaurante ajustar(String uuid, Map<String, Object> changes) {
		Restaurante restauranteAtual = this.buscarPorUUID(uuid);

		Utils.merge(restauranteAtual, changes);

		restauranteAtual = this.salvar(restauranteAtual);

		return restauranteAtual;
	}

	public boolean excluir(String uuid) {
		if (!restauranteRepository.existsByUuid(uuid)) {
			throw new NotFoundException("Restaurante não encontrado!");
		}
		restauranteRepository.deleteByUuid(uuid);

		return true;
	}
}
