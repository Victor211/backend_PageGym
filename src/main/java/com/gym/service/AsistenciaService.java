package com.gym.service;

import com.gym.dto.AsistenciaRequest;
import com.gym.entity.Asistencia;
import com.gym.entity.Usuario;
import com.gym.repository.AsistenciaRepository;
import com.gym.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsistenciaService {
    
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Asistencia> getAllAsistencias() {
        return asistenciaRepository.findAll();
    }
    
    public List<Asistencia> getAsistenciasByUsuario(Integer usuarioId) {
        return asistenciaRepository.findByUsuarioIdUsuarios(usuarioId);
    }
    
    public Asistencia registrarEntrada(AsistenciaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Verificar si ya tiene una entrada activa (sin salida)
        Optional<Asistencia> asistenciaActiva = asistenciaRepository.findActiveAsistenciaByUsuario(request.getUsuarioId());
        if (asistenciaActiva.isPresent()) {
            throw new RuntimeException("El usuario ya tiene una entrada activa. Debe registrar salida primero.");
        }
        
        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setFechaHoraEntrada(LocalDateTime.now());
        
        return asistenciaRepository.save(asistencia);
    }
    
    public Asistencia registrarSalida(AsistenciaRequest request) {
        Optional<Asistencia> asistenciaActiva = asistenciaRepository.findActiveAsistenciaByUsuario(request.getUsuarioId());
        
        if (asistenciaActiva.isEmpty()) {
            throw new RuntimeException("No se encontró una entrada activa para este usuario");
        }
        
        Asistencia asistencia = asistenciaActiva.get();
        asistencia.setFechaHoraSalida(LocalDateTime.now());
        
        return asistenciaRepository.save(asistencia);
    }
    
    public Asistencia procesarAsistencia(AsistenciaRequest request) {
        if ("ENTRADA".equalsIgnoreCase(request.getAccion())) {
            return registrarEntrada(request);
        } else if ("SALIDA".equalsIgnoreCase(request.getAccion())) {
            return registrarSalida(request);
        } else {
            // Auto-detectar: si no tiene entrada activa, registrar entrada; si la tiene, registrar salida
            Optional<Asistencia> asistenciaActiva = asistenciaRepository.findActiveAsistenciaByUsuario(request.getUsuarioId());
            
            if (asistenciaActiva.isEmpty()) {
                return registrarEntrada(request);
            } else {
                return registrarSalida(request);
            }
        }
    }
    
    public List<Asistencia> getAsistenciasHoy() {
        return asistenciaRepository.findTodayAsistencias();
    }
    
    public List<Asistencia> getAsistenciasByFechaRange(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return asistenciaRepository.findByFechaRange(fechaInicio, fechaFin);
    }
    
    public Long getCountAsistenciasByUsuarioAndFecha(Integer usuarioId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return asistenciaRepository.countAsistenciasByUsuarioAndFecha(usuarioId, fechaInicio, fechaFin);
    }
}
