package com.matheusksn.devspaceapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "boleto")
public class Boleto {
    @Id
    private Long id;
    private String nome;
}
