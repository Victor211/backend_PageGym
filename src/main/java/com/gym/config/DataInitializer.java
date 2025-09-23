package com.gym.config;

import com.gym.entity.Membresia;
import com.gym.entity.Rol;
import com.gym.entity.Usuario;
import com.gym.repository.MembresiaRepository;
import com.gym.repository.RolRepository;
import com.gym.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private MembresiaRepository membresiaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Iniciando DataInitializer...");
        
        // Inicializar roles si no existen
        Rol adminRole = null;
        Rol clienteRole = null;
        
        if (rolRepository.count() == 0) {
            System.out.println("📝 Creando roles...");
            adminRole = new Rol();
            adminRole.setDescRol("ADMINISTRADOR");
            adminRole.setEstado(1);
            adminRole = rolRepository.save(adminRole);
            System.out.println("✅ Rol ADMINISTRADOR creado con ID: " + adminRole.getIdRoles());
            
            clienteRole = new Rol();
            clienteRole.setDescRol("CLIENTE");
            clienteRole.setEstado(1);
            clienteRole = rolRepository.save(clienteRole);
            System.out.println("✅ Rol CLIENTE creado con ID: " + clienteRole.getIdRoles());
        } else {
            System.out.println("📋 Roles ya existen, buscando...");
            adminRole = rolRepository.findByDescRol("ADMINISTRADOR").orElse(null);
            clienteRole = rolRepository.findByDescRol("CLIENTE").orElse(null);
            System.out.println("📋 Rol ADMINISTRADOR encontrado: " + (adminRole != null ? adminRole.getIdRoles() : "NO ENCONTRADO"));
        }
        
        // Inicializar membresías si no existen
        if (membresiaRepository.count() == 0) {
            Membresia membresia1 = new Membresia();
            membresia1.setDescMembresia("Membresía Básica");
            membresia1.setPrecio(new BigDecimal("29.99"));
            membresia1.setDuracionDias(30);
            membresiaRepository.save(membresia1);
            
            Membresia membresia2 = new Membresia();
            membresia2.setDescMembresia("Membresía Premium");
            membresia2.setPrecio(new BigDecimal("49.99"));
            membresia2.setDuracionDias(30);
            membresiaRepository.save(membresia2);
            
            Membresia membresia3 = new Membresia();
            membresia3.setDescMembresia("Membresía VIP");
            membresia3.setPrecio(new BigDecimal("79.99"));
            membresia3.setDuracionDias(30);
            membresiaRepository.save(membresia3);
            
            Membresia membresia4 = new Membresia();
            membresia4.setDescMembresia("Membresía Anual Básica");
            membresia4.setPrecio(new BigDecimal("299.99"));
            membresia4.setDuracionDias(365);
            membresiaRepository.save(membresia4);
            
            Membresia membresia5 = new Membresia();
            membresia5.setDescMembresia("Membresía Anual Premium");
            membresia5.setPrecio(new BigDecimal("499.99"));
            membresia5.setDuracionDias(365);
            membresiaRepository.save(membresia5);
            
            Membresia membresia6 = new Membresia();
            membresia6.setDescMembresia("Membresía Anual VIP");
            membresia6.setPrecio(new BigDecimal("799.99"));
            membresia6.setDuracionDias(365);
            membresiaRepository.save(membresia6);
        }
        
        // Inicializar usuario administrador si no existe
        if (!usuarioRepository.existsByEmail("admin@gimnasio.com") && adminRole != null) {
            System.out.println("📝 Creando usuario administrador...");
            Usuario admin = new Usuario();
            admin.setNombres("Administrador");
            admin.setApellidos("Sistema");
            admin.setEmail("admin@gimnasio.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRol(adminRole);
            
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador creado: admin@gimnasio.com / admin123");
            System.out.println("✅ ID del usuario administrador: " + admin.getIdUsuarios());
        }
    }
}
