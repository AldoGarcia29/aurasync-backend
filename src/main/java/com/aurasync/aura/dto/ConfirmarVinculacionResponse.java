package com.aurasync.aura.dto;

public record ConfirmarVinculacionResponse(
        boolean vinculado,
        Long dispositivoId,
        Long usuarioId,
        String nombreDispositivo,
        boolean requierePin,
        String mensaje
) {
}