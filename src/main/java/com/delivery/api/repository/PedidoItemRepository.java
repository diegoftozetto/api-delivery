package com.delivery.api.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delivery.api.entity.PedidoItem;

public interface PedidoItemRepository extends  JpaRepository<PedidoItem, Long>  {	
	public Optional<PedidoItem> findByUuid(String uuid);
		
	@Query("select pedItem from PedidoItem pedItem where pedItem.pedido.uuid = :uuid")
	public List<PedidoItem> selectByItensDoPedidoUuid(@Param("uuid") String uuid);
	
	public boolean existsByUuid(String uuid);

	@Transactional
	@Modifying
	public void deleteByUuid(String uuid);
}
