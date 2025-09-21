package com.gym.service;

import com.gym.dto.UsuarioRequest;
import com.gym.dto.UsuarioResponse;
import com.gym.entity.Membresia;
import com.gym.entity.Rol;
import com.gym.entity.Usuario;
import com.gym.repository.MembresiaRepository;
import com.gym.repository.RolRepository;
import com.gym.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private MembresiaRepository membresiaRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<UsuarioResponse> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public UsuarioResponse getUsuarioById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertToResponse(usuario);
    }
    
    public UsuarioResponse createUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);
        
        if (request.getMembresiaId() != null) {
            Membresia membresia = membresiaRepository.findById(request.getMembresiaId())
                    .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
            usuario.setMembresia(membresia);
        }
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToResponse(savedUsuario);
    }
    
    public UsuarioResponse updateUsuario(Integer id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!usuario.getEmail().equals(request.getEmail()) && 
            usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }
        
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }
        
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);
        
        if (request.getMembresiaId() != null) {
            Membresia membresia = membresiaRepository.findById(request.getMembresiaId())
                    .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
            usuario.setMembresia(membresia);
        } else {
            usuario.setMembresia(null);
        }
        
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToResponse(updatedUsuario);
    }
    
    public void deleteUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
    
    public List<UsuarioResponse> getUsuariosByRol(String rolDesc) {
        return usuarioRepository.findByRolDesc(rolDesc).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private UsuarioResponse convertToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setIdUsuarios(usuario.getIdUsuarios());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setEmail(usuario.getEmail());
        response.setFechaCreacion(usuario.getFechaCreacion());
        response.setFechaModificacion(usuario.getFechaModificacion());
        response.setRol(usuario.getRol().getDescRol());
        response.setMembresia(usuario.getMembresia() != null ? usuario.getMembresia().getDescMembresia() : null);
        response.setMembresiaId(usuario.getMembresia() != null ? usuario.getMembresia().getIdMembresias() : null);
        return response;
    }
}
