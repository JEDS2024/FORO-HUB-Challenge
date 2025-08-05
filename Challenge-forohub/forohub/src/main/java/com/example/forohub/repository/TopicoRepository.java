package com.example.forohub.repository;

import com.example.forohub.domain.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByCursoNombreAndFechaCreacionYear(String nombreCurso, Integer anio, Pageable pageable);

    boolean existsByTituloIgnoreCaseAndMensajeIgnoreCase(String titulo, String mensaje);

    boolean existsByTituloIgnoreCaseAndMensajeIgnoreCaseAndIdNot(String titulo, String mensaje, Long id);
}

