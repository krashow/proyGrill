package com.magri.controller;

import com.magri.entity.PreguntaIncidencia;
import com.magri.repository.PreguntaIncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "http://localhost:5173") // para Vue
public class PreguntaIncidenciaController {

    @Autowired
    private PreguntaIncidenciaRepository repo;

    // Obtener preguntas por categoría
    @GetMapping("/preguntas/{id}")
    public List<PreguntaIncidencia> getPreguntasByCategoria(@PathVariable("id") Long id) {
        return repo.findByCategoriaId(id);
    }
}
