package com.aurasync.aura.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurasync.aura.dto.WatchResumenResponse;
import com.aurasync.aura.service.WatchDataService;

@RestController
@RequestMapping("/api/watch")
public class WatchDataController {

    private final WatchDataService watchDataService;

    public WatchDataController(
            WatchDataService watchDataService
    ) {
        this.watchDataService =
                watchDataService;
    }

    @GetMapping("/resumen")
    public ResponseEntity<WatchResumenResponse>
    obtenerResumen(
            @RequestHeader(
                    name = "Authorization",
                    required = false
            )
            String authorization
    ) {
        String token =
                extraerBearerToken(authorization);

        return ResponseEntity.ok(
                watchDataService
                        .obtenerResumen(token)
        );
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

        String token =
                authorization.substring(7).trim();

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
    manejarSesionInvalida(
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
}