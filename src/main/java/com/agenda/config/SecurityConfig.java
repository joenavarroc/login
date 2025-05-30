package com.agenda.config;

import com.agenda.model.UsuarioDetails;
import com.agenda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository; // Inyección del repositorio de usuarios

    // Configuración de seguridad HTTP: rutas públicas, login, logout
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**", "/login", "/forgot-password", "/reset-password", "/registro/**",
                                "/formulario/**", "/agenda/**", "/documentacion.html", "/css/**", "/js/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/eventos/**", "/favicon.ico",
                                "/error")
                        .permitAll()
                        .requestMatchers("/eventos/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/contactos/agenda", true)
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Usa el mismo formulario personalizado
                        .defaultSuccessUrl("/contactos/agenda", true) // Redirección luego de login exitoso
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }

    // Servicio que carga los detalles del usuario desde la base de datos
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByUsername(username)
                .map(UsuarioDetails::new)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Proveedor de autenticación que usa el userDetailsService y el codificador de
    // contraseñas
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Permite que Spring obtenga el AuthenticationManager automáticamente
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    // Codificador de contraseñas usando BCrypt (seguro y recomendado)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
