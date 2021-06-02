package com.delivery.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.api.entity.Pedido;

public interface PedidoRepository extends  JpaRepository<Pedido, Long>  {//	
	public Optional<Pedido> findByUuid(String uuid);
	
	@Query("select ped from Pedido ped where ped.cliente.uuid = :uuid")
	public List<Pedido> selectByClienteUuid(@Param("uuid") String uuid);
	
	@Query("select ped from Pedido ped where ped.restaurante.uuid = :uuid")
	public List<Pedido> selectByRestauranteUuid(@Param("uuid") String uuid);
	
	public boolean existsByUuid(String uuid);
	
	@Transactional
	@Modifying
	public void deleteByUuid(String uuid);
}
