package com.matheusksn.devspaceapi.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.dtos.UserDTO;
import com.matheusksn.devspaceapi.entities.UserType;
import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.enums.UserRole;
import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class UsuarioService {

 @Autowired
 private UsuarioRepository usuarioRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;


 public Usuario registerUser(UserDTO userDTO) {
     Usuario usuario = new Usuario();
     usuario.setNome(userDTO.getNome());
     usuario.setEmail(userDTO.getEmail());
     usuario.setCpfCnpj(userDTO.getCpfCnpj());
     usuario.setPhone(userDTO.getPhone());
     usuario.setLogin(userDTO.getLogin());
     usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
     usuario.setIsActive(true);
     usuario.setRole(UserRole.USER);
     usuario.setProvider("Local");
     usuario.setIsProfileComplete(true);
     Long userTypeId = 1L;
     UserType desiredUserType = new UserType();
     desiredUserType.setId(userTypeId);
     usuario.setUserType(desiredUserType);
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
 
 public boolean firstAccess(String login) {
	    return this.usuarioRepository.findByLogin(login) == null;
	}

}
