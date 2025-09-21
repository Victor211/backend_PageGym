package com.gym.service;

import com.gym.dto.PagoRequest;
import com.gym.entity.Membresia;
import com.gym.entity.Pago;
import com.gym.entity.Usuario;
import com.gym.repository.MembresiaRepository;
import com.gym.repository.PagoRepository;
import com.gym.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MembresiaRepository membresiaRepository;
    
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }
    
    public List<Pago> getPagosByUsuario(Integer usuarioId) {
        return pagoRepository.findByUsuarioIdUsuarios(usuarioId);
    }
    
    public Pago getPagoById(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }
    
    public Pago procesarPago(PagoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Membresia membresia = membresiaRepository.findById(request.getMembresiaId())
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
        
        Pago pago = new Pago();
        pago.setUsuario(usuario);
        pago.setMembresia(membresia);
        pago.setMetodoPago(request.getMetodoPago());
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstado("COMPLETADO");
        
        // Actualizar la membresía del usuario
        usuario.setMembresia(membresia);
        usuarioRepository.save(usuario);
        
        return pagoRepository.save(pago);
    }
    
    public List<Pago> getPagosByEstado(String estado) {
        return pagoRepository.findByEstado(estado);
    }
    
    public List<Pago> getPagosByMetodoPago(String metodoPago) {
        return pagoRepository.findByMetodoPago(metodoPago);
    }
    
    public List<Pago> getPagosByFechaRange(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pagoRepository.findByFechaRange(fechaInicio, fechaFin);
    }
    
    public Double getTotalIngresosByFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Double total = pagoRepository.getTotalIngresosByFecha(fechaInicio, fechaFin);
        return total != null ? total : 0.0;
    }
    
    public Pago actualizarEstadoPago(Integer pagoId, String nuevoEstado) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        
        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }
}
