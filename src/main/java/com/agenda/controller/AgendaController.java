package com.agenda.controller;

import com.agenda.model.Contacto;
import com.agenda.repository.ContactoRepository;
import com.agenda.repository.UsuarioRepository;
import com.agenda.service.EventoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/contactos/agenda")
public class AgendaController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Mostrar la agenda con eventos y contactos
    @GetMapping
    public String mostrarAgendaCompleta(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String letra,
            @RequestParam(required = false, defaultValue = "true") boolean mostrarAcciones  // <--- Nueva variable
    ) throws JsonProcessingException {
        String username = userDetails.getUsername();
        var usuario = usuarioRepository.findByUsername(username).orElse(null);

        // Inicialización de eventos
        String eventosJson = "[]";

        // Inicialización de contactos paginados
        Pageable pageable = PageRequest.of(page, size);
        Page<Contacto> contactoPage = Page.empty();
        List<Contacto> contactos = new ArrayList<>();

        if (usuario != null) {
            // Obtener eventos y mapear a JSON para FullCalendar
            List<Map<String, Object>> eventosMap = eventoService.obtenerEventosPorUsuario(username)
                .stream().map(evento -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", evento.getId());
                    map.put("title", evento.getTitulo());
                    map.put("start", evento.getFecha() + "T" + evento.getHora());
                    map.put("descripcion", evento.getDescripcion());
                    return map;
                }).collect(Collectors.toList());

            eventosJson = objectMapper.writeValueAsString(eventosMap);

            // Contactos con búsqueda y/o letra inicial
            if (busqueda != null && !busqueda.isBlank()) {
                contactoPage = contactoRepository.findByUsuarioUsernameAndNombreContainingIgnoreCase(username, busqueda, pageable);
            } else if (letra != null && !letra.isBlank()) {
                contactoPage = contactoRepository.findByUsuarioUsernameAndNombreStartingWithIgnoreCase(username, letra, pageable);
            } else {
                contactoPage = contactoRepository.findByUsuario_Username(username, pageable);
            }

            contactos = contactoPage.getContent();
        }

        // Atributos para la vista
        model.addAttribute("eventosJson", eventosJson);
        model.addAttribute("contactos", contactos);
        model.addAttribute("contactoPage", contactoPage);
        model.addAttribute("totalPages", contactoPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("letra", letra);
        model.addAttribute("letras", obtenerLetrasAbecedario());
        model.addAttribute("mostrarAcciones", mostrarAcciones);

        return "agenda";
    }

    // Método para enviar abecedario (A-Z) a la vista
    @ModelAttribute("letras")
    public List<String> obtenerLetrasAbecedario() {
        List<String> letras = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            letras.add(String.valueOf(c));
        }
        return letras;
    }
}
