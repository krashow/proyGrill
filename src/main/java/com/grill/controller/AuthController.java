package com.magri.controller;

import com.magri.entity.Usuario;
import com.magri.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Usuario user = usuarioRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();
        if (user != null && user.getContrasena().equals(password)) {
            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("usuario", user);
        } else {
            response.put("success", false);
            response.put("message", "Credenciales inválidas");
        }
        return response;
    }
}
