package com.delivery.api.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.delivery.api.entity.Usuario;
import com.delivery.api.exception.NotFoundException;
import com.delivery.api.repository.UsuarioRepository;

import lombok.Getter;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.selectByEmail(username);

		if (usuario == null) {
			throw new NotFoundException("Usuário não encontrado");
		}

		return new AuthUser(usuario, Collections.emptyList());
	}

	@Getter
	public static class AuthUser extends User {
		private static final long serialVersionUID = 1L;

		private String nome;
		private String uuid;

		public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
			super(usuario.getNome(), usuario.getPassword(), authorities);
			this.nome = usuario.getNome();
			this.uuid = usuario.getUuid();
		}
	}
}
