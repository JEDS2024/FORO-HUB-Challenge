package com.example.forohub.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        String titulo,
        String mensaje,
        String status
) {
}
