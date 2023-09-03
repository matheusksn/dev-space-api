package com.matheusksn.devspaceapi.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

 @Autowired
 private UsuarioRepository usuarioRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;


 public Usuario registerUser(Usuario usuario) {
     usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
     usuario.setIsActive(true);
     usuario.setRole("USER");
     return usuarioRepository.save(usuario);
 }

 public List<Usuario> listActiveUsers() {
     return usuarioRepository.findByIsActive(true);
 }

 public List<Usuario> listInactiveUsers() {
     return usuarioRepository.findByIsActive(false);
 }

 public Usuario updateUser(Long id, Usuario updatedUsuario) {
     Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     usuario.setNome(updatedUsuario.getNome());
     usuario.setEmail(updatedUsuario.getEmail());
     usuario.setPassword(passwordEncoder.encode(updatedUsuario.getPassword()));
     usuario.setIsActive(updatedUsuario.getIsActive());
     return usuarioRepository.save(usuario);
 }

 public Usuario desactivateUser(Long id) {
     Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     usuario.setIsActive(false);
     return usuarioRepository.save(usuario);
 }
 
 public Usuario completeProfile(Usuario updatedUsuario) {
	    return usuarioRepository.save(updatedUsuario);
	}
}
