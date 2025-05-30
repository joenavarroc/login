package com.agenda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un contacto")
public class ContactoDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del contacto", example = "Juan Pérez")
    private String nombre;

    @Email(message = "Debe ser un email válido")
    @Schema(description = "Correo electrónico del contacto", example = "juan@example.com")
    private String email;

    @Schema(description = "Teléfono del contacto", example = "+123456789")
    private String telefono;
}
