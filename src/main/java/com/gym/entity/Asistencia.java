package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idASISTENCIAS")
    private Integer idAsistencias;
    
    @Column(name = "fecha_hora_entrada", nullable = false)
    private LocalDateTime fechaHoraEntrada;
    
    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;
    
    // Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIOS_idUSUARIOS", nullable = false)
    private Usuario usuario;
}
