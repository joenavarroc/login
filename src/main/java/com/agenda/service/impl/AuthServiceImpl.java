package com.agenda.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agenda.dto.AuthResponse;
import com.agenda.dto.LoginRequest;
import com.agenda.dto.RegisterRequest;
import com.agenda.model.Usuario;
import com.agenda.repository.UsuarioRepository;
import com.agenda.security.JwtService;
import com.agenda.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        
        usuarioRepository.save(user);

        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Usuario user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }
}
