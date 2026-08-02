package com.aurasync.aura.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aurasync.aura.dto.NotificacionDetalleResponse;
import com.aurasync.aura.dto.NotificacionPreviewDTO;
import com.aurasync.aura.service.NotificacionService;
import com.aurasync.aura.service.WatchNotificationService;

@RestController
@RequestMapping("/api/watch/notificaciones")
public class WatchNotificacionController {

    private final NotificacionService notificacionService;

    private final WatchNotificationService
            watchNotificationService;

    public WatchNotificacionController(
            NotificacionService notificacionService,
            WatchNotificationService watchNotificationService
    ) {
        this.notificacionService =
                notificacionService;

        this.watchNotificationService =
                watchNotificationService;
    }

    @GetMapping("/pendientes/{usuarioId}")
    public ResponseEntity<List<NotificacionPreviewDTO>>
    obtenerPendientes(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                notificacionService
                        .obtenerPendientes(usuarioId)
        );
    }

    @PostMapping("/{notificacionId}/desbloquear")
    public ResponseEntity<NotificacionDetalleResponse>
    desbloquear(
            @PathVariable Long notificacionId,
            @RequestHeader(
                    name = "Authorization",
                    required = false
            )
            String authorization
    ) {
        String token =
                extraerBearerToken(authorization);

        return ResponseEntity.ok(
                watchNotificationService.desbloquear(
                        notificacionId,
                        token
                )
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
    manejarError(
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