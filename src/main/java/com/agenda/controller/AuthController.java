package com.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agenda.service.UsuarioService;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService userService;

    @GetMapping("/")
    public String home() {
        return "index"; // Vista principal
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(OAuth2AuthenticationToken authentication) {
        // Lógica de login OAuth
        return "success"; // Vista de éxito al iniciar sesión
    }

    // Página HTML del formulario para enviar email
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "forgot-password"; // Vista con formulario <form action="/forgot-password" method="post">
    }

    // Página HTML para restablecer contraseña
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, org.springframework.ui.Model model) {
        model.addAttribute("token", token);
        return "reset-password"; // Vista con formulario <form action="/reset-password" method="post">
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userService.createPasswordResetToken(email);
        redirectAttributes.addFlashAttribute("mensaje", "Si el correo existe, se ha enviado un enlace.");
        return "redirect:/login";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {
        boolean result = userService.resetPassword(token, password);
        if (result) {
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña restablecida correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Token inválido o expirado.");
        }
        return "redirect:/login";
    }

}
