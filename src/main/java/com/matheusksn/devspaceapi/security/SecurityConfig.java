package com.matheusksn.devspaceapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.POST, "api/auth/register" , "api/auth/login").permitAll()
				//	.requestMatchers(HttpMethod.POST, "api/auth/login").permitAll()
                    .anyRequest().authenticated()			
					)
				.build();
	}

  
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    /*@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(
                auth.antMatchers("/api/users/login", "/api/users/register").permitAll();
            	auth.requestMatchers("/", "/favicon.ico", "/api/users/login", "/api/users/register", "/error").permitAll();
                
                auth.anyRequest().authenticated();
            })
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/registration") 
            )
            .formLogin(form -> form
                .loginProcessingUrl("/api/users/login")
                .loginParameter("email")
                .passwordParameter("password")
            )
            .logout(logout -> logout
                .logoutUrl("/api/users/logout")
            );
        return http.build();
    } */
    
 /*S   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/login", "/api/users/register").permitAll()
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login");

        return http.build();
    }
    
    */
    
}
