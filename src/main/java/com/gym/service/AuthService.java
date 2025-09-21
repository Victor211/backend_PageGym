package com.gym.service;

import com.gym.dto.LoginRequest;
import com.gym.dto.LoginResponse;
import com.gym.entity.Usuario;
import com.gym.repository.UsuarioRepository;
import com.gym.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setIdUsuario(usuario.getIdUsuarios());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol().getDescRol());
        response.setMembresia(usuario.getMembresia() != null ? usuario.getMembresia().getDescMembresia() : null);
        
        return response;
    }
}
