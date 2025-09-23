package com.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;
    
    @NotNull(message = "El ID de la membresía es obligatorio")
    private Integer membresiaId;
    
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
    
    // Getters y setters manuales para asegurar compatibilidad
    public Integer getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public Integer getMembresiaId() {
        return membresiaId;
    }
    
    public void setMembresiaId(Integer membresiaId) {
        this.membresiaId = membresiaId;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
