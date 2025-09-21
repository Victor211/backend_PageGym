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
}
