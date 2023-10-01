package com.matheusksn.devspaceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.dtos.AuthDTO;
import com.matheusksn.devspaceapi.dtos.LoginResponseDTO;
import com.matheusksn.devspaceapi.dtos.UserDTO;
import com.matheusksn.devspaceapi.entities.User;
import com.matheusksn.devspaceapi.security.TokenService;
import com.matheusksn.devspaceapi.services.UserService;

import jakarta.validation.Valid;
import lombok.experimental.var;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	UserService usuarioService;
	
	@Autowired 
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        System.out.println(auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
	
	 @PostMapping("/register")
	 public ResponseEntity<User> register(@RequestBody @Valid  UserDTO usuario) {
		    if (this.usuarioService.firstAccess(usuario.getLogin())) {
		        User newUser = usuarioService.registerUser(usuario);
		        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		    } else {
		        return ResponseEntity.badRequest().build();
		    }
		}
	 

}
