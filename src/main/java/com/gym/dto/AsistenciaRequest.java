package com.gym.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaRequest {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;
    
    private String accion; // "ENTRADA" o "SALIDA"
}
