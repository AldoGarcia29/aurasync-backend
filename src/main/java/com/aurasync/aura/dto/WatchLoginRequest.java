package com.aurasync.aura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record WatchLoginRequest(

        @NotNull(
                message = "El dispositivo es obligatorio"
        )
        @Positive(
                message = "El dispositivo no es válido"
        )
        Long dispositivoId,

        @NotBlank(message = "El PIN es obligatorio")
        @Pattern(
                regexp = "\\d{6}",
                message = "El PIN debe contener seis dígitos"
        )
        String pin
) {
}