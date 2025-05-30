package com.agenda.service;

import java.util.List;
import com.agenda.model.Evento;
import com.agenda.service.enums.EliminarResultado;

public interface EventoService {
    List<Evento> obtenerTodos();
    Evento guardar(Evento evento);
    Evento obtenerPorId(Long id);
    Evento actualizar(Long id, Evento eventoEditado, String username);
    List<Evento> obtenerEventosPorUsuario(String username);
    EliminarResultado eliminar(Long id, String username);
}
