package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String tipo = "Bearer";
    private Integer idUsuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String rol;
    private String membresia;
}
