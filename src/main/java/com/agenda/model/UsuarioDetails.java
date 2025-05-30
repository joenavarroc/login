package com.agenda.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // No se usan roles, por lo tanto devolvemos una lista vacía
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes agregar lógica adicional si lo deseas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puedes agregar lógica adicional si lo deseas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puedes agregar lógica adicional si lo deseas
    }

    @Override
    public boolean isEnabled() {
        return true; // Puedes agregar lógica adicional si lo deseas
    }
}
