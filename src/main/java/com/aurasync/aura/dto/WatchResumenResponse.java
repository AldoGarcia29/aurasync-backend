package com.aurasync.aura.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WatchResumenResponse(
        Integer ritmoCardiaco,
        Integer pasos,
        Integer actividadMinutos,
        BigDecimal horasSueno,
        LocalDateTime fecha
) {
}