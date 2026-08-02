package com.aurasync.aura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aurasync.aura.entity.Biometria;
import com.aurasync.aura.service.BiometriaService;

@RestController
@RequestMapping("/api/biometria")
public class BiometriaController {

    private final BiometriaService service;

    public BiometriaController(
            BiometriaService service
    ) {
        this.service = service;
    }

    @PostMapping("/simular/{usuarioId}")
    public ResponseEntity<Biometria> simular(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                service.simularLectura(usuarioId)
        );
    }

    @GetMapping("/usuario/{usuarioId}/ultima")
    public ResponseEntity<Biometria> obtenerUltima(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                service.obtenerUltimaLectura(usuarioId)
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Biometria>> obtenerHistorial(
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                service.obtenerHistorial(usuarioId)
        );
    }
}