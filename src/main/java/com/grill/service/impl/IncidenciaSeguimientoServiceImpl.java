package com.magri.service.impl;

import com.magri.entity.IncidenciaSeguimiento;
import com.magri.repository.IncidenciaSeguimientoRepository;
import com.magri.service.IncidenciaSeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaSeguimientoServiceImpl implements IncidenciaSeguimientoService {

    @Autowired
    private IncidenciaSeguimientoRepository seguimientoRepository;
    @Override
    public IncidenciaSeguimiento guardarSeguimiento(IncidenciaSeguimiento seguimiento) {
        
        if (seguimiento.getFechaRegistro() == null) {
            seguimiento.setFechaRegistro(LocalDateTime.now()); 
        }

        return seguimientoRepository.save(seguimiento);
    }

    @Override
    public List<IncidenciaSeguimiento> obtenerHistorialPorIncidencia(Long idIncidencia) {
        return seguimientoRepository.findByIdIncidenciaOrderByFechaRegistroAsc(idIncidencia);
    }
}