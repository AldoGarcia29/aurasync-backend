package com.aurasync.aura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurasync.aura.dto.DispositivoResponse;
import com.aurasync.aura.repository.DispositivoRepository;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoController {

    private final DispositivoRepository
            dispositivoRepository;

    public DispositivoController(
            DispositivoRepository dispositivoRepository
    ) {
        this.dispositivoRepository =
                dispositivoRepository;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DispositivoResponse>>
    obtenerPorUsuario(
            @PathVariable Long usuarioId
    ) {
        List<DispositivoResponse> respuesta =
                dispositivoRepository
                        .findByUsuarioIdOrderByFechaConexionDesc(
                                usuarioId
                        )
                        .stream()
                        .map(DispositivoResponse::fromEntity)
                        .toList();

        return ResponseEntity.ok(respuesta);
    }
}