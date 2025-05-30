package com.agenda.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agenda.repository.ContactoRepository;
import com.agenda.model.Contacto;
import com.agenda.service.ContactoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactoServiceImpl implements ContactoService {

    private final ContactoRepository contactoRepository;

    @Override
    public List<Contacto> obtenerTodos() {
        return contactoRepository.findAll();
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        Long usuarioId = contacto.getUsuario().getId();
        Optional<Contacto> contactoExistente = contactoRepository
            .findByUsuarioIdAndNombreAndTelefonoAndEmail(usuarioId, contacto.getNombre(), contacto.getTelefono(), contacto.getEmail());

        if (contactoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un contacto con el mismo nombre, teléfono y correo electrónico.");
        }

        return contactoRepository.save(contacto);
    }

    @Override
    public Contacto obtenerPorId(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}
