package com.matheusksn.devspaceapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nome;
    private String email;
    private String password;
    private String cpfCnpj;
    private String phone;

}

