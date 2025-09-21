package com.gym.service;

import com.gym.entity.Membresia;
import com.gym.repository.MembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MembresiaService {
    
    @Autowired
    private MembresiaRepository membresiaRepository;
    
    public List<Membresia> getAllMembresias() {
        return membresiaRepository.findAll();
    }
    
    public Membresia getMembresiaById(Integer id) {
        return membresiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada con ID: " + id));
    }
    
    public Membresia createMembresia(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }
    
    public Membresia updateMembresia(Integer id, Membresia membresiaDetails) {
        Membresia membresia = getMembresiaById(id);
        
        membresia.setDescMembresia(membresiaDetails.getDescMembresia());
        membresia.setPrecio(membresiaDetails.getPrecio());
        membresia.setDuracionDias(membresiaDetails.getDuracionDias());
        
        return membresiaRepository.save(membresia);
    }
    
    public void deleteMembresia(Integer id) {
        if (!membresiaRepository.existsById(id)) {
            throw new RuntimeException("Membresía no encontrada");
        }
        membresiaRepository.deleteById(id);
    }
    
    public List<Membresia> getMembresiasByPrecioRange(BigDecimal precioMin, BigDecimal precioMax) {
        return membresiaRepository.findByPrecioRange(precioMin, precioMax);
    }
    
    public List<Membresia> getMembresiasByDuracionMinima(Integer dias) {
        return membresiaRepository.findByDuracionMinima(dias);
    }
    
    public List<Membresia> getMembresiasByDescripcion(String descripcion) {
        return membresiaRepository.findByDescMembresiaContainingIgnoreCase(descripcion);
    }
    
    public List<Membresia> getMembresiasByPrecioAsc() {
        return membresiaRepository.findByOrderByPrecioAsc();
    }
    
    public List<Membresia> getMembresiasByDuracionDesc() {
        return membresiaRepository.findByOrderByDuracionDiasDesc();
    }
}
