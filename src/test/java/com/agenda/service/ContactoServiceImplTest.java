package com.agenda.service;

import com.agenda.model.Contacto;
import com.agenda.repository.ContactoRepository;
import com.agenda.service.impl.ContactoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContactoServiceImplTest {

    private ContactoRepository contactoRepository;
    private ContactoServiceImpl contactoService;

    @BeforeEach
    void setUp() {
        contactoRepository = mock(ContactoRepository.class);
        contactoService = new ContactoServiceImpl(contactoRepository);
    }

    @Test
    void testObtenerTodos() {
        Contacto contacto1 = new Contacto();
        Contacto contacto2 = new Contacto();

        when(contactoRepository.findAll()).thenReturn(Arrays.asList(contacto1, contacto2));

        List<Contacto> resultado = contactoService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(contactoRepository, times(1)).findAll();
    }

    @Test
    void testGuardarContacto() {
        Contacto contacto = new Contacto();
        contacto.setNombre("Juan");

        when(contactoRepository.save(contacto)).thenReturn(contacto);

        Contacto guardado = contactoService.guardar(contacto);

        assertNotNull(guardado);
        assertEquals("Juan", guardado.getNombre());
        verify(contactoRepository).save(contacto);
    }

    @Test
    void testObtenerPorId() {
        Contacto contacto = new Contacto();
        contacto.setId(1L);

        when(contactoRepository.findById(1L)).thenReturn(Optional.of(contacto));

        Contacto resultado = contactoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(contactoRepository).findById(1L);
    }

    @Test
    void testEliminar() {
        Long id = 5L;

        contactoService.eliminar(id);

        verify(contactoRepository).deleteById(id);
    }
}
