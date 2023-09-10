package com.matheusksn.devspaceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.dtos.UserDTO;
import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired 
	AuthenticationManager authenticationManager;
	
	/*@PostMapping("/login")
	ResponseEntity login(@RequestBody  AuthDTO login) {
		var loginPassword = new loginPasswordAuthenticationToken(
				login.login(),
				login.password());
		var auth = this.authenticationManager.authenticate(loginPassword);
		return ResponseEntity.ok().build();
	} */
	

	
	 @PostMapping("/register")
	 public ResponseEntity<Usuario> register(@RequestBody @Validated UserDTO usuario) {
		    if (this.usuarioService.firstAccess(usuario.getLogin())) {
		        Usuario newUser = usuarioService.registerUser(usuario);
		        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		    } else {
		        return ResponseEntity.badRequest().build();
		    }
		}
	 

}
