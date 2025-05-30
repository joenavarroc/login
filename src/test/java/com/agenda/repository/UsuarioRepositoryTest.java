package com.agenda.repository;

import com.agenda.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testBuscarPorUsername() {
        // Arrange: crear y guardar usuario
        Usuario usuario = new Usuario();
        usuario.setUsername("bruno");
        usuario.setPassword("1234");

        usuarioRepository.save(usuario);

        // Act: buscar usuario
        Optional<Usuario> resultado = usuarioRepository.findByUsername("bruno");

        // Assert: verificar resultados
        assertTrue(resultado.isPresent());
        assertEquals("bruno", resultado.get().getUsername());
    }
}
