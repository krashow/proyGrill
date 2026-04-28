package com.magri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                
                // === REGLAS PÚBLICAS ESPECÍFICAS (DEBEN IR PRIMERO) ===
                
                // 1. Permitir la asignación de incidencias (POST)
            	.requestMatchers(HttpMethod.POST, "/api/incidencias/asignar-responsable").permitAll() 
                
                // 2. Permitir la carga de responsables (GET)
                .requestMatchers(HttpMethod.GET, "/api/usuarios/responsables").permitAll()
                
                // 3. Permitir la lectura de incidencias (GET)
                .requestMatchers(HttpMethod.GET, "/api/incidencias/detalle").permitAll()
                
                // 4. Permitir rutas de autenticación
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/prioridades").permitAll()
                
                // 5. Permitir acceso SÓLO a usuarios autenticados para ver sus asignaciones.
                .requestMatchers(HttpMethod.GET, "/api/incidencias/responsable/asignadas").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/incidencias/*/seguimientos").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/incidencias/*/seguimientos").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/gestion/registrar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/gestion/historial/*").permitAll()
                
                // === REGLAS AMPLIAS ===
                
                // 5. Permitir todo el resto del CRUD de Incidencias
                .requestMatchers("/api/incidencias/**").permitAll()
                
                // 6. Proteger lista de usuarios
                .requestMatchers("/api/usuarios/**").authenticated() 
                
                // 7. EL RESTO DEL API DEBE ESTAR AUTENTICADO
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
            "http://localhost:4200", 
            "http://localhost:5173", 
            "http://127.0.0.1:5500", 
            "http://localhost:8080", 
            "http://localhost:8081" 
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
