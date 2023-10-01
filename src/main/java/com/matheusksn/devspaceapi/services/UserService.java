package com.matheusksn.devspaceapi.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.dtos.UserDTO;
import com.matheusksn.devspaceapi.entities.User;
import com.matheusksn.devspaceapi.entities.UserType;
import com.matheusksn.devspaceapi.enums.UserRole;
import com.matheusksn.devspaceapi.repositories.UserRepository;

@Service
public class UserService {

 @Autowired
 private UserRepository usuarioRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;


 public User registerUser(UserDTO userDTO) {
     User usuario = new User();
     usuario.setName(userDTO.getName());
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
 
 public List<User> listActiveUsers() {
     return usuarioRepository.findByIsActive(true);
 }

 public List<User> listInactiveUsers() {
     return usuarioRepository.findByIsActive(false);
 }

 public User updateUser(Long id, User updatedUser) {
     User usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     usuario.setName(updatedUser.getName());
     usuario.setEmail(updatedUser.getEmail());
     usuario.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
     usuario.setIsActive(updatedUser.getIsActive());
     return usuarioRepository.save(usuario);
 }

 public User desactivateUser(Long id) {
     User usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
     usuario.setIsActive(false);
     return usuarioRepository.save(usuario);
 }
 
 public User completeProfile(User updatedUser) {
	    return usuarioRepository.save(updatedUser);
	}
 
 public boolean firstAccess(String login) {
	    return this.usuarioRepository.findByLogin(login) == null;
	}

}
