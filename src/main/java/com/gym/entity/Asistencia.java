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
    
    // Getters y setters manuales para asegurar compatibilidad
    public Integer getIdAsistencias() {
        return idAsistencias;
    }
    
    public void setIdAsistencias(Integer idAsistencias) {
        this.idAsistencias = idAsistencias;
    }
    
    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }
    
    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }
    
    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }
    
    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
