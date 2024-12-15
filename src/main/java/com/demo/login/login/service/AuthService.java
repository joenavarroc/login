package com.demo.login.login.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.login.login.auth.AuthResponse;
import com.demo.login.login.auth.LoginRequest;
import com.demo.login.login.auth.RegisterRequest;
import com.demo.login.login.repositories.UserRepository;
import com.demo.login.login.user.Role;
import com.demo.login.login.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public String register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .role(Role.USER)
            .build();
    
        userRepository.save(user);
    
        return "Usuario registrado exitosamente";
    }
    

}
