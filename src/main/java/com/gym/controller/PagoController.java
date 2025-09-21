package com.gym.controller;

import com.gym.dto.PagoRequest;
import com.gym.entity.Pago;
import com.gym.service.PagoService;
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
@RequestMapping("/pagos")
@Tag(name = "Pagos", description = "Endpoints para gestión de pagos de membresías")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "bearerAuth")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene la lista de todos los pagos (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Pago>> getAllPagos() {
        List<Pago> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Obtiene un pago específico por su ID")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @pagoService.getPagoById(#id).usuario.idUsuarios == authentication.principal.id")
    public ResponseEntity<Pago> getPagoById(@PathVariable Integer id) {
        try {
            Pago pago = pagoService.getPagoById(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener pagos por usuario", description = "Obtiene todos los pagos de un usuario específico")
    @PreAuthorize("hasRole('ADMINISTRADOR') or #usuarioId == authentication.principal.id")
    public ResponseEntity<List<Pago>> getPagosByUsuario(@PathVariable Integer usuarioId) {
        List<Pago> pagos = pagoService.getPagosByUsuario(usuarioId);
        return ResponseEntity.ok(pagos);
    }
    
    @PostMapping("/procesar")
    @Operation(summary = "Procesar pago", description = "Procesa un nuevo pago de membresía")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('CLIENTE')")
    public ResponseEntity<?> procesarPago(@Valid @RequestBody PagoRequest pagoRequest) {
        try {
            Pago pago = pagoService.procesarPago(pagoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener pagos por estado", description = "Obtiene todos los pagos con un estado específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Pago>> getPagosByEstado(@PathVariable String estado) {
        List<Pago> pagos = pagoService.getPagosByEstado(estado);
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/metodo/{metodoPago}")
    @Operation(summary = "Obtener pagos por método", description = "Obtiene todos los pagos realizados con un método específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Pago>> getPagosByMetodoPago(@PathVariable String metodoPago) {
        List<Pago> pagos = pagoService.getPagosByMetodoPago(metodoPago);
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/rango")
    @Operation(summary = "Obtener pagos por rango de fechas", description = "Obtiene pagos en un rango de fechas específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Pago>> getPagosByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Pago> pagos = pagoService.getPagosByFechaRange(fechaInicio, fechaFin);
        return ResponseEntity.ok(pagos);
    }
    
    @GetMapping("/ingresos")
    @Operation(summary = "Obtener total de ingresos", description = "Obtiene el total de ingresos en un rango de fechas (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Double> getTotalIngresos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Double total = pagoService.getTotalIngresosByFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(total);
    }
    
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de pago", description = "Actualiza el estado de un pago específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> actualizarEstadoPago(@PathVariable Integer id, @RequestParam String estado) {
        try {
            Pago pago = pagoService.actualizarEstadoPago(id, estado);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
