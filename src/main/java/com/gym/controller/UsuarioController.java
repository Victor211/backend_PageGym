package com.gym.controller;

import com.gym.dto.UsuarioRequest;
import com.gym.dto.UsuarioResponse;
import com.gym.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene la lista de todos los usuarios (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico por su ID")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @usuarioService.getUsuarioById(#id).email == authentication.name")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Integer id) {
        try {
            UsuarioResponse usuario = usuarioService.getUsuarioById(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse usuario = usuarioService.createUsuario(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @usuarioService.getUsuarioById(#id).email == authentication.name")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse usuario = usuarioService.updateUsuario(id, usuarioRequest);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok().body("Usuario eliminado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/rol/{rolDesc}")
    @Operation(summary = "Obtener usuarios por rol", description = "Obtiene todos los usuarios de un rol específico (solo administradores)")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioResponse>> getUsuariosByRol(@PathVariable String rolDesc) {
        List<UsuarioResponse> usuarios = usuarioService.getUsuariosByRol(rolDesc);
        return ResponseEntity.ok(usuarios);
    }
}
