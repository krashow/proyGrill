package com.magri.service;

import com.magri.entity.IncidenciaSeguimiento;
import java.util.List;

public interface IncidenciaSeguimientoService {
    
    IncidenciaSeguimiento guardarSeguimiento(IncidenciaSeguimiento seguimiento);

    List<IncidenciaSeguimiento> obtenerHistorialPorIncidencia(Long idIncidencia);
}