package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPAGOS")
    private Integer idPagos;
    
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
    
    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;
    
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;
    
    // Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIOS_idUSUARIOS", nullable = false)
    private Usuario usuario;
    
    // Relación con membresía
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBRESIAS_idMEMBRESIAS", nullable = false)
    private Membresia membresia;
}
