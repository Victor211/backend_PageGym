package com.gym.repository;

import com.gym.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    List<Pago> findByUsuarioIdUsuarios(Integer usuarioId);
    
    List<Pago> findByEstado(String estado);
    
    List<Pago> findByMetodoPago(String metodoPago);
    
    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<Pago> findByFechaRange(@Param("fechaInicio") LocalDateTime fechaInicio, 
                               @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pago p WHERE p.usuario.idUsuarios = :usuarioId AND p.estado = :estado")
    List<Pago> findByUsuarioAndEstado(@Param("usuarioId") Integer usuarioId, 
                                     @Param("estado") String estado);
    
    @Query("SELECT SUM(p.membresia.precio) FROM Pago p WHERE p.estado = 'COMPLETADO' AND " +
           "p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    Double getTotalIngresosByFecha(@Param("fechaInicio") LocalDateTime fechaInicio,
                                  @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT p FROM Pago p WHERE p.membresia.idMembresias = :membresiaId")
    List<Pago> findByMembresiaId(@Param("membresiaId") Integer membresiaId);
}
