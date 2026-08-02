package com.aurasync.aura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConfirmarVinculacionRequest(

        @NotBlank(message = "El código es obligatorio")
        @Pattern(
                regexp = "\\d{6}",
                message = "El código temporal debe contener seis dígitos"
        )
        String codigo,

        @NotBlank(
                message = "El nombre del dispositivo es obligatorio"
        )
        @Size(
                max = 100,
                message = "El nombre es demasiado largo"
        )
        String nombreDispositivo,

        @NotBlank(message = "El PIN es obligatorio")
        @Pattern(
                regexp = "\\d{6}",
                message = "El PIN debe contener seis dígitos"
        )
        String pin
) {
}