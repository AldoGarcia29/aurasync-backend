package com.aurasync.aura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(
                min = 3,
                max = 100,
                message = "El nombre debe tener entre 3 y 100 caracteres"
        )
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(
                min = 8,
                max = 72,
                message = "La contraseña debe tener entre 8 y 72 caracteres"
        )
        String password
) {
}