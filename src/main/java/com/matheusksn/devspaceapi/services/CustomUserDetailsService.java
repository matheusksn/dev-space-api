package com.matheusksn.devspaceapi.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .disabled(!usuario.getIsActive())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole())))
            .build();
    }
}
