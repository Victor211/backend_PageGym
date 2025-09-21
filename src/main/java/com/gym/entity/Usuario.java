package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUSUARIOS")
    private Integer idUsuarios;
    
    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;
    
    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;
    
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    // Relación con rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLES_idROLES", nullable = false)
    private Rol rol;
    
    // Relación con membresía
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBRESIAS_idMEMBRESIAS")
    private Membresia membresia;
    
    // Relación con asistencias
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;
    
    // Relación con pagos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;
}
