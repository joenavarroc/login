package com.agenda.service;

import com.agenda.dto.AuthResponse;
import com.agenda.dto.LoginRequest;
import com.agenda.dto.RegisterRequest;



public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
