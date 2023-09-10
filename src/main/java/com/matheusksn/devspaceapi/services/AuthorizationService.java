package com.matheusksn.devspaceapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	   @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return usuarioRepository.findByLogin(username);
	    }

}
