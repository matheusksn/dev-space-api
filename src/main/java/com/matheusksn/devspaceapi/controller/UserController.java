package com.matheusksn.devspaceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.services.UsuarioService;

@RestController
@RequestMapping("/api/users")
public class UserController {

 @Autowired
 private UsuarioService usuarioService;

 @PostMapping("/register")
 public Usuario registerUser(@RequestBody Usuario usuario) {
     return usuarioService.registerUser(usuario);
 }

 @GetMapping("/active")
 public List<Usuario> listActiveUsers() {
     return usuarioService.listActiveUsers();
 }

 @GetMapping("/inactive")
 public List<Usuario> listInactiveUsers() {
     return usuarioService.listInactiveUsers();
 }

 @PutMapping("/update/{id}")
 public Usuario updateUser(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
     return usuarioService.updateUser(id, updatedUsuario);
 }

 @PutMapping("/desactivate/{id}")
 public Usuario deactivateUser(@PathVariable Long id) {
     return usuarioService.desactivateUser(id);
 }

@PostMapping("/completeProfile")
public Usuario completeProfile(@RequestBody Usuario usuario) {
  return usuarioService.completeProfile(usuario);
}

}
