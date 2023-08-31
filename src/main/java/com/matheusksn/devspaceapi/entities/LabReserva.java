package com.matheusksn.devspaceapi.entities;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "lab_reserva")
public class LabReserva {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_laboratorio")
    private Lab laboratorio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraReserva;

    private Boolean isActive;

}
