package com.matheusksn.devspaceapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.matheusksn.devspaceapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndProvider(String email, String provider);

    User findByEmail(String email);
    
    List<User> findByIsActive(Boolean isActive);
    
    UserDetails findByLogin(String nickName);


}
