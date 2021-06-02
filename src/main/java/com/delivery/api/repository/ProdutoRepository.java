package com.delivery.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.api.entity.Produto;

public interface ProdutoRepository extends  JpaRepository<Produto, Long>  {
	public List<Produto> findByRestauranteId(Long id);
	
	public Optional<Produto> findByUuid(String uuid);
	
	@Query("select pro from Produto pro where pro.restaurante.uuid = :uuid")
	public List<Produto> selectByRestauranteUuid(@Param("uuid") String uuid);
	
	public boolean existsByUuid(String uuid);
	
	@Transactional
	@Modifying
	public void deleteByUuid(String uuid);
}
