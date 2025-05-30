package com.agenda.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora = LocalTime.MIDNIGHT; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore  // Para evitar la recursión
    private Usuario usuario;

}
