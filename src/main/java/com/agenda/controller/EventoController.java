package com.agenda.controller;

import com.agenda.dto.EventoDTO;
import com.agenda.model.Evento;
import com.agenda.model.Usuario;
import com.agenda.repository.UsuarioRepository;
import com.agenda.service.EventoService;
import com.agenda.service.enums.EliminarResultado;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioRepository usuarioRepository;



    // Obtener eventos del usuario autenticado
    @GetMapping
    @ResponseBody
    public List<Map<String, Object>> listarEventos(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Evento> eventos = eventoService.obtenerEventosPorUsuario(username);

        return eventos.stream()
                .map(evento -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", evento.getId());
                    map.put("title", evento.getTitulo());
                    map.put("start", evento.getFecha() + "T" + evento.getHora());
                    map.put("descripcion", evento.getDescripcion());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Crear o actualizar evento
    @PostMapping
    public ResponseEntity<Evento> guardarEvento(
            @RequestBody @Valid EventoDTO eventoDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        // Buscar usuario por username, si no existe devolver 401
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));

        Evento evento = new Evento();
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(LocalDate.parse(eventoDTO.getFecha()));
        evento.setHora(LocalTime.parse(eventoDTO.getHora()));
        evento.setUsuario(usuario);

        Evento eventoGuardado = eventoService.guardar(evento);
        return ResponseEntity.ok(eventoGuardado);
    }

    // Eliminar evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EliminarResultado resultado = eventoService.eliminar(id, username);

        switch (resultado) {
            case ELIMINADO:
                return ResponseEntity.noContent().build();
            case NO_ENCONTRADO:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            case NO_AUTORIZADO:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(
            @PathVariable Long id,
            @RequestBody @Valid EventoDTO eventoDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        // Crear entidad Evento desde DTO
        Evento eventoEditado = new Evento();
        eventoEditado.setId(id);  // Usar el id de la URL para evitar inconsistencias
        eventoEditado.setTitulo(eventoDTO.getTitulo());
        eventoEditado.setDescripcion(eventoDTO.getDescripcion());
        eventoEditado.setFecha(LocalDate.parse(eventoDTO.getFecha()));
        eventoEditado.setHora(LocalTime.parse(eventoDTO.getHora()));

        // Setear usuario
        usuarioRepository.findByUsername(username).ifPresent(eventoEditado::setUsuario);

        Evento actualizado = eventoService.actualizar(id, eventoEditado, username);
        return ResponseEntity.ok(actualizado);
    }

}
