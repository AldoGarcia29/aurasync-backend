package com.aurasync.aura.dto;

public record LoginResponse(
        boolean autenticado,
        Long usuarioId,
        String nombre,
        String correo,
        String mensaje
) {

    public static LoginResponse exitoso(
            Long usuarioId,
            String nombre,
            String correo
    ) {
        return new LoginResponse(
                true,
                usuarioId,
                nombre,
                correo,
                "Inicio de sesión correcto"
        );
    }

    public static LoginResponse rechazado() {
        return new LoginResponse(
                false,
                null,
                null,
                null,
                "Correo o contraseña incorrectos"
        );
    }
}