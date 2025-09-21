package com.gym.controller;

import com.gym.entity.Rol;
import com.gym.repository.RolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Endpoints para gestión de roles de usuario")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "bearerAuth")
public class RolController {
    
    @Autowired
    private RolRepository rolRepository;
    
    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtiene la lista de todos los roles disponibles")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/activos")
    @Operation(summary = "Obtener roles activos", description = "Obtiene la lista de roles activos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Rol>> getActiveRoles() {
        List<Rol> roles = rolRepository.findActiveRoles();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol específico por su ID")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        return rolRepository.findById(id)
                .map(rol -> ResponseEntity.ok().body(rol))
                .orElse(ResponseEntity.notFound().build());
    }
}
