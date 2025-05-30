package com.agenda.service;

import com.agenda.model.Usuario;

public interface UsuarioService {
    void createPasswordResetToken(String email);
    boolean resetPassword(String token, String newPassword);
    boolean existsByUsername(String username);
    void registrar(Usuario usuario);
    
}
