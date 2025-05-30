package com.agenda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un evento")
public class EventoDTO {

    @Schema(description = "ID del evento (opcional para creación)", example = "1")
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Schema(description = "Título del evento", example = "Cumpleaños")
    private String titulo;

    @Schema(description = "Descripción del evento", example = "Fiesta sorpresa para Juan")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha del evento en formato yyyy-MM-dd", example = "2025-05-26")
    private String fecha;

    @NotNull(message = "La hora es obligatoria")
    @Schema(description = "Hora del evento en formato HH:mm", example = "14:30")
    private String hora;

}
