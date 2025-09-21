package com.gym.repository;

import com.gym.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Optional<Rol> findByDescRol(String descRol);
    
    @Query("SELECT r FROM Rol r WHERE r.estado = 1")
    List<Rol> findActiveRoles();
    
    boolean existsByDescRol(String descRol);
}
