package com.example.forohub.domain.dto;

import com.example.forohub.domain.Topico;
import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        String autorNombre,
        String cursoNombre
) {
    public DatosListadoTopico(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre()
        );
    }
}
