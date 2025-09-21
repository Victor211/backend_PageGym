package com.gym.repository;

import com.gym.entity.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {
    
    List<Membresia> findByDescMembresiaContainingIgnoreCase(String descripcion);
    
    @Query("SELECT m FROM Membresia m WHERE m.precio BETWEEN :precioMin AND :precioMax")
    List<Membresia> findByPrecioRange(@Param("precioMin") BigDecimal precioMin, 
                                     @Param("precioMax") BigDecimal precioMax);
    
    @Query("SELECT m FROM Membresia m WHERE m.duracionDias >= :dias")
    List<Membresia> findByDuracionMinima(@Param("dias") Integer dias);
    
    List<Membresia> findByOrderByPrecioAsc();
    
    List<Membresia> findByOrderByDuracionDiasDesc();
}
