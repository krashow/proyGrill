package com.magri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Base64;

@SpringBootApplication
@RestController
public class SecuritySimulationController {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySimulationController.class, args);
    }

    private final Map<String, String> users = new HashMap<>() {{
        put("admin", "1234");
        put("user", "1234");
    }};

    private final Map<String, String> roles = new HashMap<>() {{
        put("admin", "ADMIN");
        put("user", "USER");
    }};

    private final Map<String, Long> activeTokens = new ConcurrentHashMap<>();

    private final long EXPIRATION = 1000 * 60 * 30;

    private String generateToken(String username) {
        String raw = username + ":" + System.currentTimeMillis();
        String token = Base64.getEncoder().encodeToString(raw.getBytes());
        activeTokens.put(token, System.currentTimeMillis() + EXPIRATION);
        return token;
    }

    private boolean validateToken(String token) {
        if (!activeTokens.containsKey(token)) return false;
        long exp = activeTokens.get(token);
        if (System.currentTimeMillis() > exp) {
            activeTokens.remove(token);
            return false;
        }
        return true;
    }

    private String getUserFromToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split(":")[0];
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username,
                                     @RequestParam String password) {

        if (!users.containsKey(username) || !users.get(username).equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = generateToken(username);

        return Map.of(
                "token", token,
                "role", roles.get(username)
        );
    }

    @GetMapping("/secure")
    public String secure(@RequestHeader(value = "Authorization", required = false) String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token requerido");
        }

        String token = header.substring(7);

        if (!validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        String user = getUserFromToken(token);

        return "Acceso permitido a: " + user;
    }

    @GetMapping("/admin")
    public String admin(@RequestHeader(value = "Authorization", required = false) String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token requerido");
        }

        String token = header.substring(7);

        if (!validateToken(token)) {
            throw new RuntimeException("Token inválido");
        }

        String user = getUserFromToken(token);
        String role = roles.get(user);

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Acceso denegado");
        }

        return "Panel administrador: " + user;
    }

    @GetMapping("/logout")
    public String logout(@RequestHeader(value = "Authorization", required = false) String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token requerido");
        }

        String token = header.substring(7);
        activeTokens.remove(token);

        return "Sesión cerrada";
    }
}