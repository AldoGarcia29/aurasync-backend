package com.aurasync.aura.dto;

import java.time.OffsetDateTime;

public record WatchLoginResponse(
        boolean autenticado,
        Long dispositivoId,
        Long usuarioId,
        String nombreDispositivo,
        String token,
        OffsetDateTime fechaExpiracion,
        String mensaje
) {
}