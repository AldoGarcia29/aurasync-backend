package com.aurasync.aura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurasync.aura.dto.WatchResumenResponse;
import com.aurasync.aura.entity.Biometria;
import com.aurasync.aura.entity.Dispositivo;
import com.aurasync.aura.entity.SesionDispositivo;
import com.aurasync.aura.repository.BiometriaRepository;
import com.aurasync.aura.repository.DispositivoRepository;

@Service
public class WatchDataService {

    private final WatchAuthService watchAuthService;

    private final DispositivoRepository
            dispositivoRepository;

    private final BiometriaRepository
            biometriaRepository;

    public WatchDataService(
            WatchAuthService watchAuthService,
            DispositivoRepository dispositivoRepository,
            BiometriaRepository biometriaRepository
    ) {
        this.watchAuthService =
                watchAuthService;

        this.dispositivoRepository =
                dispositivoRepository;

        this.biometriaRepository =
                biometriaRepository;
    }

    @Transactional(readOnly = true)
    public WatchResumenResponse obtenerResumen(
            String token
    ) {
        SesionDispositivo sesion =
                watchAuthService
                        .validarSesion(token);

        Dispositivo dispositivo =
                dispositivoRepository
                        .findById(
                                sesion.getDispositivoId()
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "El dispositivo no existe"
                                        )
                        );

        Biometria biometria =
                biometriaRepository
                        .findTopByUsuarioIdOrderByFechaDesc(
                                dispositivo.getUsuarioId()
                        )
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "No existen datos biométricos"
                                        )
                        );

        return new WatchResumenResponse(
                biometria.getRitmoCardiaco(),
                biometria.getPasos(),
                biometria.getActividadMinutos(),
                biometria.getHorasSueno(),
                biometria.getFecha()
        );
    }
}