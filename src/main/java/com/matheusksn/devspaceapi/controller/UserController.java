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

import com.matheusksn.devspaceapi.entities.User;
import com.matheusksn.devspaceapi.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

 @Autowired
 private UserService usuarioService;
 

 @GetMapping("/active")
 public List<User> listActiveUsers() {
     return usuarioService.listActiveUsers();
 }

 @GetMapping("/inactive")
 public List<User> listInactiveUsers() {
     return usuarioService.listInactiveUsers();
 }

 @PutMapping("/update/{id}")
 public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
     return usuarioService.updateUser(id, updatedUser);
 }

 @PutMapping("/desactivate/{id}")
 public User deactivateUser(@PathVariable Long id) {
     return usuarioService.desactivateUser(id);
 }

@PostMapping("/completeProfile")
public User completeProfile(@RequestBody User usuario) {
  return usuarioService.completeProfile(usuario);
}

}
