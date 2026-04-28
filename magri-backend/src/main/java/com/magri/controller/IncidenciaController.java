package com.magri.controller;

import com.magri.entity.Incidencia;
import com.magri.service.IncidenciaService;
import com.magri.service.UsuarioService;
import com.magri.entity.Usuario;
import com.magri.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import com.magri.dto.IncidenciaRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public IncidenciaController(IncidenciaService incidenciaService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.incidenciaService = incidenciaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Incidencia> listar() {
        return incidenciaService.listar();
    }

    @GetMapping("/detalle")
    public Incidencia buscarPorId(@RequestParam("id") Long id) {
        return incidenciaService.buscarPorId(id);
    }

    @PostMapping
    public Incidencia guardar(@RequestBody Incidencia incidencia) {
        return incidenciaService.guardar(incidencia);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        incidenciaService.eliminar(id);
    }
    
    @PostMapping("/registrar")
    public Map<String, Object> registrarIncidencia(@RequestBody IncidenciaRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            incidenciaService.registrarIncidencia(request);
            response.put("success", true);
            response.put("message", "Incidencia registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error al registrar la incidencia: " + e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/asignar-responsable")
    public ResponseEntity<Incidencia> asignarResponsable(@RequestBody Map<String, Object> asignacionData) {
        
        Integer idIncidencia = ((Number) asignacionData.get("idIncidencia")).intValue();
        Integer idResponsable = ((Number) asignacionData.get("idResponsable")).intValue();
        String observacion = (String) asignacionData.get("observacion");

        if (idIncidencia == null || idResponsable == null) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Incidencia incidenciaActualizada = incidenciaService.asignarResponsable(
                idIncidencia, 
                idResponsable, 
                observacion
            );
            return ResponseEntity.ok(incidenciaActualizada);
            
        } catch (IllegalStateException e) {
            System.err.println("Error de lógica de negocio al asignar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
            
        } catch (RuntimeException e) {
            System.err.println("Error al asignar responsable (Entidad no encontrada): " + e.getMessage());
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error inesperado al asignar responsable: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   
    @GetMapping("/responsable/asignadas")
    public ResponseEntity<List<Incidencia>> getIncidenciasAsignadas(
        @RequestParam("idResponsable") Integer idResponsable 
    ) { 
        try {
            Usuario responsable = usuarioRepository.findById(idResponsable)
                .orElseThrow(() -> new RuntimeException("Responsable con ID " + idResponsable + " no encontrado."));
            
            List<Incidencia> asignaciones = incidenciaService.listarPorResponsable(responsable); 
            
            return ResponseEntity.ok(asignaciones);
            
        } catch (RuntimeException e) {
            System.err.println("Error al obtener asignaciones: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error inesperado al obtener asignaciones: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}