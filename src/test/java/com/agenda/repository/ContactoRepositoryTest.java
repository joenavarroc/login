package com.agenda.repository;

import com.agenda.model.Contacto;
import com.agenda.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ContactoRepositoryTest {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsername("bruno");
        usuario.setPassword("1234");
        usuario = usuarioRepository.save(usuario);

        contactoRepository.saveAll(List.of(
            crearContacto("Carlos", "carlos@mail.com"),
            crearContacto("Camila", "camila@mail.com"),
            crearContacto("Pedro", "pedro@mail.com"),
            crearContacto("Carmen", "carmen@mail.com"),
            crearContacto("Ana", "ana@mail.com")
        ));
    }

    private Contacto crearContacto(String nombre, String email) {
        Contacto c = new Contacto();
        c.setNombre(nombre);
        c.setEmail(email);
        c.setTelefono("123456");
        c.setUsuario(usuario);
        return c;
    }

    @Test
    void testBuscarPorNombreConteniendo() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nombre"));

        Page<Contacto> resultados = contactoRepository
            .findByUsuarioUsernameAndNombreContainingIgnoreCase("bruno", "ca", pageable);

        assertEquals(3, resultados.getTotalElements()); // Carlos, Camila, Carmen
    }

    @Test
    void testBuscarPorNombreQueEmpiezaConLetra() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nombre"));

        Page<Contacto> resultados = contactoRepository
            .findByUsuarioUsernameAndNombreStartingWithIgnoreCase("bruno", "c", pageable);

        assertEquals(3, resultados.getTotalElements()); // Carlos, Camila, Carmen
    }
}
