package com.gym.controller;

import com.gym.entity.Membresia;
import com.gym.service.MembresiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/membresias")
@Tag(name = "Membresías", description = "Endpoints para gestión de membresías del gimnasio")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "bearerAuth")
public class MembresiaController {
    
    @Autowired
    private MembresiaService membresiaService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las membresías", description = "Obtiene la lista de todas las membresías disponibles")
    public ResponseEntity<List<Membresia>> getAllMembresias() {
        List<Membresia> membresias = membresiaService.getAllMembresias();
        return ResponseEntity.ok(membresias);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener membresía por ID", description = "Obtiene una membresía específica por su ID")
    public ResponseEntity<Membresia> getMembresiaById(@PathVariable Integer id) {
        try {
            Membresia membresia = membresiaService.getMembresiaById(id);
            return ResponseEntity.ok(membresia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva membresía", description = "Crea una nueva membresía en el sistema (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> createMembresia(@Valid @RequestBody Membresia membresia) {
        try {
            Membresia nuevaMembresia = membresiaService.createMembresia(membresia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMembresia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar membresía", description = "Actualiza los datos de una membresía existente (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> updateMembresia(@PathVariable Integer id, @Valid @RequestBody Membresia membresiaDetails) {
        try {
            Membresia membresia = membresiaService.updateMembresia(id, membresiaDetails);
            return ResponseEntity.ok(membresia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar membresía", description = "Elimina una membresía del sistema (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> deleteMembresia(@PathVariable Integer id) {
        try {
            membresiaService.deleteMembresia(id);
            return ResponseEntity.ok().body("Membresía eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/precio-rango")
    @Operation(summary = "Obtener membresías por rango de precio", description = "Obtiene membresías dentro de un rango de precios específico")
    public ResponseEntity<List<Membresia>> getMembresiasByPrecioRange(
            @RequestParam BigDecimal precioMin,
            @RequestParam BigDecimal precioMax) {
        List<Membresia> membresias = membresiaService.getMembresiasByPrecioRange(precioMin, precioMax);
        return ResponseEntity.ok(membresias);
    }
    
    @GetMapping("/duracion-minima/{dias}")
    @Operation(summary = "Obtener membresías por duración mínima", description = "Obtiene membresías con una duración mínima específica")
    public ResponseEntity<List<Membresia>> getMembresiasByDuracionMinima(@PathVariable Integer dias) {
        List<Membresia> membresias = membresiaService.getMembresiasByDuracionMinima(dias);
        return ResponseEntity.ok(membresias);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar membresías por descripción", description = "Busca membresías que contengan una descripción específica")
    public ResponseEntity<List<Membresia>> getMembresiasByDescripcion(@RequestParam String descripcion) {
        List<Membresia> membresias = membresiaService.getMembresiasByDescripcion(descripcion);
        return ResponseEntity.ok(membresias);
    }
    
    @GetMapping("/ordenar-precio")
    @Operation(summary = "Obtener membresías ordenadas por precio", description = "Obtiene todas las membresías ordenadas por precio ascendente")
    public ResponseEntity<List<Membresia>> getMembresiasByPrecioAsc() {
        List<Membresia> membresias = membresiaService.getMembresiasByPrecioAsc();
        return ResponseEntity.ok(membresias);
    }
    
    @GetMapping("/ordenar-duracion")
    @Operation(summary = "Obtener membresías ordenadas por duración", description = "Obtiene todas las membresías ordenadas por duración descendente")
    public ResponseEntity<List<Membresia>> getMembresiasByDuracionDesc() {
        List<Membresia> membresias = membresiaService.getMembresiasByDuracionDesc();
        return ResponseEntity.ok(membresias);
    }
}
