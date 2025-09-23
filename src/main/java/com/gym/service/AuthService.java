package com.gym.service;

import com.gym.dto.LoginRequest;
import com.gym.dto.LoginResponse;
import com.gym.entity.Usuario;
import com.gym.repository.UsuarioRepository;
import com.gym.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        logger.info("🔐 Intentando autenticar usuario: {}", loginRequest.getEmail());
        
        try {
            // Verificar si el usuario existe
            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + loginRequest.getEmail()));
            
            logger.info("✅ Usuario encontrado: {} - Rol: {}", usuario.getEmail(), usuario.getRol().getDescRol());
            
            // Autenticar
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            
            logger.info("✅ Autenticación exitosa para: {}", loginRequest.getEmail());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            logger.info("✅ Token JWT generado para: {}", loginRequest.getEmail());
            
            LoginResponse response = new LoginResponse();
            response.setToken(jwt);
            response.setIdUsuario(usuario.getIdUsuarios());
            response.setNombres(usuario.getNombres());
            response.setApellidos(usuario.getApellidos());
            response.setEmail(usuario.getEmail());
            response.setRol(usuario.getRol().getDescRol());
            response.setMembresia(usuario.getMembresia() != null ? usuario.getMembresia().getDescMembresia() : null);
            
            logger.info("✅ LoginResponse creado exitosamente");
            return response;
            
        } catch (Exception e) {
            logger.error("❌ Error en autenticación: {}", e.getMessage(), e);
            throw new RuntimeException("Error de autenticación: " + e.getMessage());
        }
    }
}
