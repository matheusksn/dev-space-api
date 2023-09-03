package com.matheusksn.devspaceapi.controller;

//src/main/java/com/matheusksn/devspaceapi/controller/OAuth2Controller.java

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

 @GetMapping("/authorization/registration")
 public String registration() {
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     return "Redirect to profile completion or home page";
 }
}
