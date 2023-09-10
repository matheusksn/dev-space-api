package com.matheusksn.devspaceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.dtos.AuthDTO;
import com.matheusksn.devspaceapi.dtos.UserDTO;
import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.services.UsuarioService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired 
	AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data) {

		   var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		   System.out.println(usernamePassword);

	    try {
	        var auth = this.authenticationManager.authenticate(usernamePassword);


	        SecurityContextHolder.getContext().setAuthentication(auth);
	        return ResponseEntity.ok().build();
	    } catch (AuthenticationException e) {
	        System.out.println("Error during authentication: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	    }
	}

	

	
	 @PostMapping("/register")
	 public ResponseEntity<Usuario> register(@RequestBody @Valid  UserDTO usuario) {
		    if (this.usuarioService.firstAccess(usuario.getLogin())) {
		        Usuario newUser = usuarioService.registerUser(usuario);
		        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		    } else {
		        return ResponseEntity.badRequest().build();
		    }
		}
	 

}
