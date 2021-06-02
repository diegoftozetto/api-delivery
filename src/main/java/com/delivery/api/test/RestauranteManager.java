package com.delivery.api.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.delivery.api.entity.Restaurante;

@Component
public class RestauranteManager {

	@PersistenceContext
	private EntityManager manager;

	public List<Restaurante> listar() {
		return manager.createQuery("SELECT r FROM Restaurante r", Restaurante.class).getResultList();
	}
}
