package com.matheusksn.devspaceapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheusksn.devspaceapi.repositories.UsuarioRepository;

@Service
public class OAuth2UserService {

    @Autowired
    UsuarioRepository userRepository;

  /*  public Usuario getCurrentUser() {
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
        
        return null; 
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
    } /*/
}
