package com.example.libreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.libreria.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:query% OR l.autor LIKE %:query%")
    List<Libro> buscarPorQuery(@Param("query") String query);
}