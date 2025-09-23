package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "membresias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membresia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMEMBRESIAS")
    private Integer idMembresias;
    
    @Column(name = "desc_membresia", length = 50, nullable = false)
    private String descMembresia;
    
    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;
    
    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;
    
    // Relación con usuarios
    @OneToMany(mappedBy = "membresia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    
    // Relación con pagos
    @OneToMany(mappedBy = "membresia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;
    
    // Getters y setters manuales para asegurar compatibilidad
    public Integer getIdMembresias() {
        return idMembresias;
    }
    
    public void setIdMembresias(Integer idMembresias) {
        this.idMembresias = idMembresias;
    }
    
    public String getDescMembresia() {
        return descMembresia;
    }
    
    public void setDescMembresia(String descMembresia) {
        this.descMembresia = descMembresia;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public Integer getDuracionDias() {
        return duracionDias;
    }
    
    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<Pago> getPagos() {
        return pagos;
    }
    
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
}
