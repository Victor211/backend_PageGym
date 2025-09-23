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
    
    // Getters y setters manuales para asegurar compatibilidad
    public Integer getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
}
