package com.matheusksn.devspaceapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusksn.devspaceapi.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmailAndProvider(String email, String provider);

    Usuario findByEmail(String email);
    
    List<Usuario> findByIsActive(Boolean isActive);


}
