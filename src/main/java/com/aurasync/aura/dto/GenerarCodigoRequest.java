package com.aurasync.aura.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GenerarCodigoRequest(

        @NotNull(message = "El usuario es obligatorio")
        @Positive(message = "El usuario no es válido")
        Long usuarioId
) {
}