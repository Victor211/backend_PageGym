package com.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    
    private Integer idUsuarios;
    private String nombres;
    private String apellidos;
    private String email;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String rol;
    private String membresia;
    private Integer membresiaId;
}
