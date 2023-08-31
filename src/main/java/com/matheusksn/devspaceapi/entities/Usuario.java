package com.matheusksn.devspaceapi.entities;

import jakarta.persistence.Entity;
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
    private Long id;
    private String nome;
    private String email;
    private Boolean isActive;
    private String cpfCnpj;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_user_type")
    private UserType userType;
}
