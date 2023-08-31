package com.matheusksn.devspaceapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "type_user")
public class UserType {
	
    @Id
    private Long id;
    private String nome;
}
