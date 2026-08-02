package com.aurasync.aura.dto;

import java.time.LocalDateTime;

import com.aurasync.aura.entity.Dispositivo;

public record DispositivoResponse(
        Long id,
        String tipo,
        String nombre,
        String estado,
        LocalDateTime fechaConexion,
        boolean pinHabilitado
) {

    public static DispositivoResponse fromEntity(
            Dispositivo dispositivo
    ) {
        return new DispositivoResponse(
                dispositivo.getId(),
                dispositivo.getTipo(),
                dispositivo.getNombre(),
                dispositivo.getEstado(),
                dispositivo.getFechaConexion(),
                dispositivo.isPinHabilitado()
        );
    }
}