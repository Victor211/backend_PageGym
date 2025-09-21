package com.gym.security;

import com.gym.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    
    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserPrincipal(Integer id, String nombres, String apellidos, String email, 
                        String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    
    public static UserPrincipal create(Usuario usuario) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getDescRol().toUpperCase());
        
        return new UserPrincipal(
                usuario.getIdUsuarios(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getPasswordHash(),
                Collections.singletonList(authority)
        );
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
