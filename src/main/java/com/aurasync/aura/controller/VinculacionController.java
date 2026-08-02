package com.aurasync.aura.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurasync.aura.dto.GenerarCodigoRequest;
import com.aurasync.aura.dto.GenerarCodigoResponse;
import com.aurasync.aura.service.VinculacionService;

import jakarta.validation.Valid;

import com.aurasync.aura.dto.ConfirmarVinculacionRequest;
import com.aurasync.aura.dto.ConfirmarVinculacionResponse;

@RestController
@RequestMapping("/api/vinculacion")
public class VinculacionController {

    private final VinculacionService vinculacionService;

    public VinculacionController(
            VinculacionService vinculacionService
    ) {
        this.vinculacionService =
                vinculacionService;
    }

    @PostMapping("/generar")
    public ResponseEntity<GenerarCodigoResponse>
    generarCodigo(
            @Valid
            @RequestBody
            GenerarCodigoRequest request
    ) {
        GenerarCodigoResponse respuesta =
                vinculacionService.generarCodigo(
                        request.usuarioId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    @PostMapping("/confirmar")
public ResponseEntity<ConfirmarVinculacionResponse>
confirmarVinculacion(
        @Valid
        @RequestBody
        ConfirmarVinculacionRequest request
) {
    ConfirmarVinculacionResponse respuesta =
            vinculacionService
                    .confirmarVinculacion(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(respuesta);
}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>>
    manejarArgumentoInvalido(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(
                        Map.of(
                                "mensaje",
                                exception.getMessage()
                        )
                );
    }
}