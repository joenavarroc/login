package com.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agenda.exception.ResourceNotFoundException;
import com.agenda.exception.UnauthorizedAccessException;
import com.agenda.model.Contacto;
import com.agenda.repository.ContactoRepository;
import com.agenda.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contactos")
@Tag(name = "Contactos", description = "Operaciones relacionadas con los contactos")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar formulario para crear un nuevo contacto
    @GetMapping("/nuevo")
    @Operation(summary = "Mostrar formulario para crear un contacto", description = "Muestra el formulario para agregar un nuevo contacto")
    public String formularioNuevo(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "formulario";  // Este es el formulario Thymeleaf para crear un nuevo contacto
    }

    // Guardar el nuevo contacto
    @PostMapping
    @Operation(summary = "Guardar un nuevo contacto", description = "Guarda un contacto en la agenda del usuario")
    public String guardar(@ModelAttribute @Valid Contacto contacto, BindingResult result, Model model,
                        @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "formulario";
        }

        String username = userDetails.getUsername();

        usuarioRepository.findByUsername(username).ifPresent(usuario -> {
            contacto.setUsuario(usuario);

            // Solo para creación de nuevo contacto (no edición)
            if (contacto.getId() == null) {
                boolean existeDuplicado = contactoRepository.existsByUsuarioAndNombreAndTelefonoAndEmail(
                        usuario, contacto.getNombre(), contacto.getTelefono(), contacto.getEmail());

                if (existeDuplicado) {
                    model.addAttribute("errorMensaje", "Ya existe un contacto con el mismo nombre, teléfono y email.");
                    model.addAttribute("usuario", usuario);
                    return;  // Interrumpe el guardado y regresa al formulario
                }

                contactoRepository.save(contacto);
            } else {
                // Edición de contacto existente
                Contacto existente = contactoRepository.findById(contacto.getId()).orElse(null);
                if (existente != null && existente.getUsuario().getId().equals(usuario.getId())) {
                    // Solo actualiza si es del mismo usuario
                    existente.setNombre(contacto.getNombre());
                    existente.setTelefono(contacto.getTelefono());
                    existente.setEmail(contacto.getEmail());
                    contactoRepository.save(existente);
                }
            }
        });

        return "redirect:/contactos/agenda";
    }

    @GetMapping("/formulario")
    @Operation(summary = "Formulario para agregar un nuevo contacto", description = "Muestra el formulario vacío para crear un contacto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        // Devuelve el fragmento "formularioContacto" del template formulario.html
        return "formulario :: formularioContacto";
    }

    // Editar un contacto
    @GetMapping("/editar/{id}")
    @Operation(summary = "Editar un contacto", description = "Muestra el formulario con los datos del contacto para editar")
    public String editar(
        @PathVariable @Parameter(description = "ID del contacto a editar", example = "123") Long id,
        Model model,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        Contacto contacto = contactoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el contacto con ID: " + id));

        if (!contacto.getUsuario().getUsername().equals(userDetails.getUsername())) {
            throw new UnauthorizedAccessException("No tienes permisos para editar este contacto.");
        }

        model.addAttribute("contacto", contacto);
        // Devuelve solo el fragmento para cargar en modal
        return "formulario :: formularioContacto";
    }

    // Eliminar un contacto
    @GetMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un contacto", description = "Elimina un contacto de la agenda del usuario")
    public String eliminar(@PathVariable Long id) {
        Contacto contacto = contactoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el contacto con ID: " + id));
        
        contactoRepository.delete(contacto);
        return "redirect:/contactos/agenda";
    }
        

}
