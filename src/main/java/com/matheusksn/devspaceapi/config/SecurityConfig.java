package com.matheusksn.devspaceapi.config;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig {

  /*/  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers
                ("/", "/home", "/login**").permitAll()
                .anyRequest().authenticated()
            .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
                .successHandler(oAuth2LoginSuccessHandler);
        return http.build();
    } /*/
}
