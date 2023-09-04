package com.matheusksn.devspaceapi.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.entities.UserType;
import com.matheusksn.devspaceapi.entities.Usuario;
import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class OAuth2UserService {

    @Autowired
    UsuarioRepository userRepository;

    public Usuario getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String phone = (String) attributes.get("phone");  // Capturando o telefone
            String provider = oauthToken.getAuthorizedClientRegistrationId();
            
            return handleOAuth2User(email, name, phone, provider); // Passando o telefone
        }
        
        return null; // ou lançar uma exceção
    }

    public Usuario handleOAuth2User(String email, String name, String phone, String provider) {
        Optional<Usuario> existingUser = userRepository.findByEmailAndProvider(email, provider);
        
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            Usuario newUser = new Usuario();
            newUser.setEmail(email);
            newUser.setNome(name);
            newUser.setPhone(phone);  
            newUser.setIsActive(true);
            newUser.setIsProfileComplete(false);
            newUser.setProvider(provider);
            newUser.setUserType(determineUserType(email, provider));
            userRepository.save(newUser);
            return newUser;
        }
    }

    private UserType determineUserType(String email, String provider) {
        UserType userType = new UserType();
        userType.setId(1L);
        
        if ("google".equalsIgnoreCase(provider) || "github".equalsIgnoreCase(provider) || "facebook".equalsIgnoreCase(provider)) {
            if (email.endsWith("@faculdadeimpacta.com.br")) {
                userType.setId(2L);
            }
        }
        
        return userType;
    }
}
