package com.matheusksn.devspaceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusksn.devspaceapi.services.OAuth2UserService;

@RestController
public class HomeController {
	
	@Autowired
	OAuth2UserService oAuth2UserService;
	
	@GetMapping("/")
	public String home() {
		return "hello,home";
	}
	
	
	@GetMapping("/secured")
	public String secured() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	System.out.println(oAuth2UserService.getCurrentUser());
    	System.out.println(oAuth2UserService.getCurrentUser());
    	System.out.println(oAuth2UserService.getCurrentUser());
    	System.out.println(oAuth2UserService.getCurrentUser());

    	System.out.println("vasco" +authentication.getName());
		return "hello,secured";

	}
}
