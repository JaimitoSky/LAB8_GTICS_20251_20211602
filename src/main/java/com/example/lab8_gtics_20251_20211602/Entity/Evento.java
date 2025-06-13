package com.example.lab8_gtics_20251_20211602.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del evento no puede estar vacío")
    @Size(max = 255, message = "El nombre del evento no debe exceder 255 caracteres")
    @Column(name = "match_name")
    private String matchName;

    @NotBlank(message = "La fecha del evento no puede estar vacía")
    @Column(name = "event_date")
    private String eventDate;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(max = 100)
    @Column(name = "`location`")
    private String location;

    @NotBlank(message = "La condición climática no puede estar vacía")
    @Size(max = 100)
    @Column(name = "`condition`")
    private String condition;

    @NotNull(message = "La temperatura máxima no puede ser nula")
    @DecimalMin(value = "-100.0", message = "Temperatura máxima fuera de rango")
    @DecimalMax(value = "100.0", message = "Temperatura máxima fuera de rango")
    @Column(name = "max_temp_c")
    private Double maxTempC;

    @NotNull(message = "La temperatura mínima no puede ser nula")
    @DecimalMin(value = "-100.0", message = "Temperatura mínima fuera de rango")
    @DecimalMax(value = "100.0", message = "Temperatura mínima fuera de rango")
    @Column(name = "min_temp_c")
    private Double minTempC;
}

