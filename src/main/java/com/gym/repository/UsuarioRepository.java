package com.gym.repository;

import com.gym.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.rol.descRol = :rolDesc")
    List<Usuario> findByRolDesc(@Param("rolDesc") String rolDesc);
    
    @Query("SELECT u FROM Usuario u WHERE u.membresia.idMembresias = :membresiaId")
    List<Usuario> findByMembresiaId(@Param("membresiaId") Integer membresiaId);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombres LIKE %:nombre% OR u.apellidos LIKE %:apellido%")
    List<Usuario> findByNombresOrApellidos(@Param("nombre") String nombre, @Param("apellido") String apellido);
}
