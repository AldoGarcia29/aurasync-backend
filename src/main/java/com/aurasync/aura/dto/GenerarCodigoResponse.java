package com.aurasync.aura.dto;

import java.time.OffsetDateTime;

public record GenerarCodigoResponse(
        String codigo,
        OffsetDateTime fechaExpiracion,
        int duracionMinutos,
        String mensaje
) {
}