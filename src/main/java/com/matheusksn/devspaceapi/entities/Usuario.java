package com.matheusksn.devspaceapi.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    private String nome;
    private String email;
    private Boolean isActive;
    @Nullable
    private String cpfCnpj;
    
    @Nullable
    private String phone;
    private String provider;


    @ManyToOne
    @JoinColumn(name = "id_user_type")
    private UserType userType;
}
