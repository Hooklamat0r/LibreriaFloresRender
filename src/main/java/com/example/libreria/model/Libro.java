package com.example.libreria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String autor;
    
    @Min(value = 1000, message = "El año debe ser mayor a 1000")
    @Max(value = 2100, message = "El año no puede ser futuro")
    private int año;
    
    @NotBlank(message = "El género es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String genero;
    
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private int puntuacion;

    public Libro() {}

    public Libro(String titulo, String autor, int año, String genero, int puntuacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
        this.genero = genero;
        this.puntuacion = puntuacion;
    }
}