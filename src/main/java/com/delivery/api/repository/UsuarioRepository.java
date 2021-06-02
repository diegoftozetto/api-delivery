package com.delivery.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delivery.api.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
	public Usuario findByUuid(String uuid);
	
	@Query("select usu from Usuario usu where usu.email like :email")
	public Usuario selectByEmail(@Param("email") String email);
}
