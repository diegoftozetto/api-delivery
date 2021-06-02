package com.delivery.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.api.entity.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	public Optional<Restaurante> findByUuid(String uuid);

	public boolean existsByUuid(String uuid);

	@Transactional
	@Modifying
	public void deleteByUuid(String uuid);
}
