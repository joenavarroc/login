package com.agenda.controller;

import com.agenda.model.Usuario;
import com.agenda.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Muestra el formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";  // Se debe crear un formulario registro.html
    }

    // Maneja el formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {

        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya está registrado.");
            return "registro"; // vuelve al formulario con mensaje
        }

        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardar en base de datos
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }
}
