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
    
    // Getters y setters manuales para asegurar compatibilidad
    public Integer getIdUsuarios() {
        return idUsuarios;
    }
    
    public void setIdUsuarios(Integer idUsuarios) {
        this.idUsuarios = idUsuarios;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getMembresia() {
        return membresia;
    }
    
    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }
    
    public Integer getMembresiaId() {
        return membresiaId;
    }
    
    public void setMembresiaId(Integer membresiaId) {
        this.membresiaId = membresiaId;
    }
}
