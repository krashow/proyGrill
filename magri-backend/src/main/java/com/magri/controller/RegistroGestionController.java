package com.magri.controller;

import com.magri.entity.IncidenciaSeguimiento;
import com.magri.service.IncidenciaSeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestion")
@CrossOrigin(origins = "http://localhost:5173") 
public class RegistroGestionController {

    @Autowired
    private IncidenciaSeguimientoService seguimientoService; 

    /**
     * POST /api/gestion/registrar
     * GUARDA el registro en la tabla incidencia_seguimientos.
     */
    @PostMapping("/registrar")
    public ResponseEntity<IncidenciaSeguimiento> registrarGestion(@RequestBody IncidenciaSeguimiento nuevoRegistro) {
        
        IncidenciaSeguimiento registroGuardado = seguimientoService.guardarSeguimiento(nuevoRegistro);

        return new ResponseEntity<>(registroGuardado, HttpStatus.CREATED);
    }

    @GetMapping("/historial/{idIncidencia}")
    public ResponseEntity<List<IncidenciaSeguimiento>> obtenerHistorial(
            @PathVariable("idIncidencia") Long idIncidencia) { 
            
            List<IncidenciaSeguimiento> historial = seguimientoService.obtenerHistorialPorIncidencia(idIncidencia);
            return new ResponseEntity<>(historial, HttpStatus.OK);
        }
}