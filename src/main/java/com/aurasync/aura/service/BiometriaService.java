package com.aurasync.aura.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.aurasync.aura.entity.Biometria;
import com.aurasync.aura.repository.BiometriaRepository;

@Service
public class BiometriaService {

    private final BiometriaRepository repository;
    private final NotificacionService notificacionService;

    public BiometriaService(
            BiometriaRepository repository,
            NotificacionService notificacionService
    ) {
        this.repository = repository;
        this.notificacionService = notificacionService;
    }

    public Biometria simularLectura(Long usuarioId) {
        ThreadLocalRandom random =
                ThreadLocalRandom.current();

        int actividadMinutos = random.nextInt(0, 121);

        int ritmoCardiaco;

        if (actividadMinutos < 20) {
            ritmoCardiaco = random.nextInt(60, 81);
        } else if (actividadMinutos <= 60) {
            ritmoCardiaco = random.nextInt(70, 96);
        } else {
            ritmoCardiaco = random.nextInt(85, 116);
        }

        int pasosCalculados =
                actividadMinutos * random.nextInt(60, 121)
                + random.nextInt(0, 1001);

        int pasos = Math.min(pasosCalculados, 15000);

        double suenoGenerado =
                random.nextDouble(4.5, 9.1);

        BigDecimal horasSueno =
                BigDecimal.valueOf(suenoGenerado)
                        .setScale(
                                1,
                                RoundingMode.HALF_UP
                        );

        Biometria biometria = new Biometria();

        biometria.setUsuarioId(usuarioId);
        biometria.setRitmoCardiaco(ritmoCardiaco);
        biometria.setPasos(pasos);
        biometria.setActividadMinutos(
                actividadMinutos
        );
        biometria.setHorasSueno(horasSueno);
        biometria.setFecha(LocalDateTime.now());

        Biometria guardada = repository.save(biometria);

notificacionService.crearDesdeBiometria(guardada);

return guardada;
    }

    public Biometria obtenerUltimaLectura(
            Long usuarioId
    ) {
        return repository
                .findTopByUsuarioIdOrderByFechaDesc(
                        usuarioId
                )
                .orElseThrow(
                        () -> new IllegalStateException(
                                "El usuario todavía no tiene "
                                + "registros biométricos."
                        )
                );
    }

    public List<Biometria> obtenerHistorial(
            Long usuarioId
    ) {
        return repository
                .findByUsuarioIdOrderByFechaAsc(
                        usuarioId
                );
    }
}