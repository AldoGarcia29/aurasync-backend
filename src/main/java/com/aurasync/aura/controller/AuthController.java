package com.aurasync.aura.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurasync.aura.dto.LoginRequest;
import com.aurasync.aura.dto.LoginResponse;
import com.aurasync.aura.entity.Usuario;
import com.aurasync.aura.service.AuthService;

import jakarta.validation.Valid;

import java.util.Map;

import com.aurasync.aura.dto.RegistroRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> iniciarSesion(
            @Valid @RequestBody LoginRequest request
    ) {
        Optional<Usuario> resultado =
                authService.autenticar(
                        request.correo(),
                        request.password()
                );

        if (resultado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.rechazado());
        }

        Usuario usuario = resultado.get();

        LoginResponse respuesta = LoginResponse.exitoso(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo()
        );

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/registro")
public ResponseEntity<Map<String, Object>> registrar(
        @Valid @RequestBody RegistroRequest request
) {
    try {
        Usuario usuario = authService.registrar(
                request.nombre(),
                request.correo(),
                request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "usuarioId",
                                usuario.getId(),
                                "nombre",
                                usuario.getNombre(),
                                "correo",
                                usuario.getCorreo(),
                                "mensaje",
                                "Cuenta creada correctamente"
                        )
                );
    } catch (IllegalArgumentException error) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        Map.of(
                                "mensaje",
                                error.getMessage()
                        )
                );
    }
}
}