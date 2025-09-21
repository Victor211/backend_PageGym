package com.gym.repository;

import com.gym.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    
    List<Asistencia> findByUsuarioIdUsuarios(Integer usuarioId);
    
    @Query("SELECT a FROM Asistencia a WHERE a.usuario.idUsuarios = :usuarioId AND a.fechaHoraSalida IS NULL")
    Optional<Asistencia> findActiveAsistenciaByUsuario(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT a FROM Asistencia a WHERE a.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin")
    List<Asistencia> findByFechaRange(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                     @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.usuario.idUsuarios = :usuarioId AND " +
           "a.fechaHoraEntrada BETWEEN :fechaInicio AND :fechaFin")
    Long countAsistenciasByUsuarioAndFecha(@Param("usuarioId") Integer usuarioId,
                                          @Param("fechaInicio") LocalDateTime fechaInicio,
                                          @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT a FROM Asistencia a WHERE DATE(a.fechaHoraEntrada) = CURRENT_DATE")
    List<Asistencia> findTodayAsistencias();
}
