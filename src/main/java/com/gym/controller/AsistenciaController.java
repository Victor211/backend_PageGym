package com.gym.controller;

import com.gym.dto.AsistenciaRequest;
import com.gym.entity.Asistencia;
import com.gym.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/asistencias")
@Tag(name = "Asistencias", description = "Endpoints para gestión de asistencias al gimnasio")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "bearerAuth")
public class AsistenciaController {
    
    @Autowired
    private AsistenciaService asistenciaService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las asistencias", description = "Obtiene la lista de todas las asistencias (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Asistencia>> getAllAsistencias() {
        List<Asistencia> asistencias = asistenciaService.getAllAsistencias();
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener asistencias por usuario", description = "Obtiene las asistencias de un usuario específico")
    @PreAuthorize("hasRole('ADMINISTRADOR') or #usuarioId == authentication.principal.id")
    public ResponseEntity<List<Asistencia>> getAsistenciasByUsuario(@PathVariable Integer usuarioId) {
        List<Asistencia> asistencias = asistenciaService.getAsistenciasByUsuario(usuarioId);
        return ResponseEntity.ok(asistencias);
    }
    
    @PostMapping("/entrada")
    @Operation(summary = "Registrar entrada", description = "Registra la entrada de un usuario al gimnasio")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    public ResponseEntity<?> registrarEntrada(@Valid @RequestBody AsistenciaRequest request) {
        try {
            Asistencia asistencia = asistenciaService.registrarEntrada(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(asistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/salida")
    @Operation(summary = "Registrar salida", description = "Registra la salida de un usuario del gimnasio")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    public ResponseEntity<?> registrarSalida(@Valid @RequestBody AsistenciaRequest request) {
        try {
            Asistencia asistencia = asistenciaService.registrarSalida(request);
            return ResponseEntity.ok(asistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/procesar")
    @Operation(summary = "Procesar asistencia automática", description = "Procesa automáticamente entrada o salida según el estado del usuario")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    public ResponseEntity<?> procesarAsistencia(@Valid @RequestBody AsistenciaRequest request) {
        try {
            Asistencia asistencia = asistenciaService.procesarAsistencia(request);
            return ResponseEntity.ok(asistencia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/hoy")
    @Operation(summary = "Obtener asistencias de hoy", description = "Obtiene todas las asistencias del día actual (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Asistencia>> getAsistenciasHoy() {
        List<Asistencia> asistencias = asistenciaService.getAsistenciasHoy();
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/rango")
    @Operation(summary = "Obtener asistencias por rango de fechas", description = "Obtiene asistencias en un rango de fechas específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Asistencia>> getAsistenciasByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Asistencia> asistencias = asistenciaService.getAsistenciasByFechaRange(fechaInicio, fechaFin);
        return ResponseEntity.ok(asistencias);
    }
    
    @GetMapping("/count/{usuarioId}")
    @Operation(summary = "Contar asistencias por usuario y fecha", description = "Cuenta las asistencias de un usuario en un rango de fechas")
    @PreAuthorize("hasRole('ADMINISTRADOR') or #usuarioId == authentication.principal.id")
    public ResponseEntity<Long> getCountAsistencias(
            @PathVariable Integer usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Long count = asistenciaService.getCountAsistenciasByUsuarioAndFecha(usuarioId, fechaInicio, fechaFin);
        return ResponseEntity.ok(count);
    }
}
