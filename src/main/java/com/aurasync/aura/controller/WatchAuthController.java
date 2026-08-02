package com.aurasync.aura.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurasync.aura.dto.WatchLoginRequest;
import com.aurasync.aura.dto.WatchLoginResponse;
import com.aurasync.aura.service.WatchAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/watch")
public class WatchAuthController {

    private final WatchAuthService watchAuthService;

    public WatchAuthController(
            WatchAuthService watchAuthService
    ) {
        this.watchAuthService = watchAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<WatchLoginResponse>
    iniciarSesion(
            @Valid
            @RequestBody
            WatchLoginRequest request
    ) {
        WatchLoginResponse respuesta =
                watchAuthService
                        .iniciarSesion(request);

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> cerrarSesion(
            @RequestHeader(
                    name = "Authorization",
                    required = false
            )
            String authorization
    ) {
        String token =
                extraerBearerToken(authorization);

        watchAuthService.cerrarSesion(token);

        return ResponseEntity.noContent().build();
    }

    private String extraerBearerToken(
            String authorization
    ) {
        if (authorization == null ||
                !authorization.startsWith(
                        "Bearer "
                )) {
            throw new IllegalArgumentException(
                    "La sesión no es válida"
            );
        }

        String token = authorization
                .substring(7)
                .trim();

        if (token.isEmpty()) {
            throw new IllegalArgumentException(
                    "La sesión no es válida"
            );
        }

        return token;
    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<Map<String, String>>
    manejarCredencialesIncorrectas(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        Map.of(
                                "mensaje",
                                exception.getMessage()
                        )
                );
    }

    @ExceptionHandler(
            IllegalStateException.class
    )
    public ResponseEntity<Map<String, String>>
    manejarBloqueo(
            IllegalStateException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(
                        Map.of(
                                "mensaje",
                                exception.getMessage()
                        )
                );
    }
}