package com.agenda.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.model.Evento;
import com.agenda.model.Usuario;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Obtener eventos por el nombre de usuario
    List<Evento> findByUsuario_Username(String username);

    // Obtener eventos por el objeto Usuario
    List<Evento> findByUsuario(Usuario usuario);

    // Método para paginar resultados (opcional si esperas muchos eventos)
    Page<Evento> findByUsuario(Usuario usuario, Pageable pageable);
}
