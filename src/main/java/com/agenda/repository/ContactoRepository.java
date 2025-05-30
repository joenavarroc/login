package com.agenda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.model.Contacto;
import com.agenda.model.Usuario;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByUsuario_Username(String username);
    
    List<Contacto> findByUsuarioUsernameAndNombreContainingIgnoreCase(String username, String nombre);

    List<Contacto> findByUsuarioUsernameAndNombreStartingWithIgnoreCase(String username, String letra);

    Page<Contacto> findByUsuario_Username(String username, Pageable pageable);

    Page<Contacto> findByUsuarioUsernameAndNombreContainingIgnoreCase(String username, String nombre, Pageable pageable);
    
    Page<Contacto> findByUsuarioUsernameAndNombreStartingWithIgnoreCase(String username, String letra, Pageable pageable);
    
    Optional<Contacto> findByUsuarioIdAndNombreAndTelefonoAndEmail(Long usuarioId, String nombre, String telefono, String email);

    boolean existsByUsuarioAndNombreAndTelefonoAndEmail(Usuario usuario, String nombre, String telefono, String email);
    
}
