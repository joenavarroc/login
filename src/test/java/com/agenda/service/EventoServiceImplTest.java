package com.agenda.service;

import com.agenda.model.Evento;
import com.agenda.model.Usuario;
import com.agenda.repository.EventoRepository;
import com.agenda.repository.UsuarioRepository;
import com.agenda.service.enums.EliminarResultado;
import com.agenda.service.impl.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventoServiceImplTest {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private EventoServiceImpl eventoService;

    private Usuario bruno;

    @BeforeEach
    void setUp() {
        eventoService = new EventoServiceImpl(eventoRepository, usuarioRepository);

        bruno = new Usuario();
        bruno.setUsername("bruno");
        bruno.setPassword("123");
        usuarioRepository.save(bruno);
    }

    @Test
    void testGuardarEventoConHoraNull() {
        Evento evento = Evento.builder()
                .titulo("Clase de Matemática")
                .descripcion("Clase con los alumnos")
                .fecha(LocalDate.of(2025, 6, 1))
                .hora(null)
                .usuario(bruno)
                .build();

        Evento guardado = eventoService.guardar(evento);

        assertNotNull(guardado.getId());
        assertEquals("Clase de Matemática", guardado.getTitulo());
        assertNotNull(guardado.getHora());
        assertEquals("00:00", guardado.getHora().toString());
    }

    @Test
    void testObtenerEventoPorId() {
        Evento evento = eventoService.guardar(Evento.builder()
                .titulo("Reunión")
                .descripcion("Reunión de equipo")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        Evento resultado = eventoService.obtenerPorId(evento.getId());

        assertEquals("Reunión", resultado.getTitulo());
    }

    @Test
    void testEliminarEventoConUsuarioCorrecto() {
        Evento evento = eventoService.guardar(Evento.builder()
                .titulo("Entrenamiento")
                .descripcion("Gimnasio")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        EliminarResultado resultado = eventoService.eliminar(evento.getId(), "bruno");

        assertEquals(EliminarResultado.ELIMINADO, resultado);
        assertFalse(eventoRepository.findById(evento.getId()).isPresent());
    }

    @Test
    void testEliminarEventoConUsuarioIncorrecto() {
        Evento evento = eventoService.guardar(Evento.builder()
                .titulo("Entrenamiento")
                .descripcion("Gimnasio")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        EliminarResultado resultado = eventoService.eliminar(evento.getId(), "usuario_ajeno");

        assertEquals(EliminarResultado.NO_AUTORIZADO, resultado);
        assertTrue(eventoRepository.findById(evento.getId()).isPresent());
    }

    @Test
    void testEliminarEventoNoExistente() {
        EliminarResultado resultado = eventoService.eliminar(999L, "bruno");
        assertEquals(EliminarResultado.NO_ENCONTRADO, resultado);
    }


    @Test
    void testActualizarEvento() {
        Evento evento = eventoService.guardar(Evento.builder()
                .titulo("Antiguo Título")
                .descripcion("Desc.")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        Evento editado = Evento.builder()
                .titulo("Nuevo Título")
                .descripcion("Actualizado")
                .fecha(LocalDate.of(2025, 6, 30))
                .hora(null)
                .build();

        Evento actualizado = eventoService.actualizar(evento.getId(), editado, "bruno");

        assertEquals("Nuevo Título", actualizado.getTitulo());
        assertEquals("Actualizado", actualizado.getDescripcion());
        assertEquals(LocalDate.of(2025, 6, 30), actualizado.getFecha());
        assertNotNull(actualizado.getHora());
    }

    @Test
    void testObtenerEventosPorUsuario() {
        eventoService.guardar(Evento.builder()
                .titulo("Evento 1")
                .descripcion("A")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        eventoService.guardar(Evento.builder()
                .titulo("Evento 2")
                .descripcion("B")
                .fecha(LocalDate.now())
                .usuario(bruno)
                .build());

        List<Evento> eventos = eventoService.obtenerEventosPorUsuario("bruno");

        assertEquals(2, eventos.size());
    }
}
