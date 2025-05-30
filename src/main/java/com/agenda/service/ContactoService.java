package com.agenda.service;

import java.util.List;
import com.agenda.model.Contacto;


public interface ContactoService {
    List<Contacto> obtenerTodos();
    Contacto guardar(Contacto contacto);
    Contacto obtenerPorId(Long id);
    void eliminar(Long id);
}
