/* package com.agenda.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Recursos no encontrados
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("fecha", LocalDateTime.now());
        return "error";
    }

    // Acceso no autorizado
    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorized(UnauthorizedAccessException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("fecha", LocalDateTime.now());
        return "error";
    }

    // Manejador genérico con exclusión para Swagger y JSON
    @ExceptionHandler(Exception.class)
    public Object handleGeneralException(HttpServletRequest request, Exception ex) {
        String path = request.getRequestURI();

        // Si la ruta es de Swagger o espera JSON, dejamos que Spring maneje la excepción
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") || 
            request.getHeader("Accept") != null && request.getHeader("Accept").contains(MediaType.APPLICATION_JSON_VALUE)) {
            return null;
        }

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("mensaje", ex.getMessage());
        mav.addObject("fecha", LocalDateTime.now());
        return mav;
    }
} */
